package org.example;

import org.example.kafka.KafkaSender;
import org.example.model.InvestmentModel;
import org.example.model.InvestorModel;
import org.example.model.OrderModel;
import org.example.service.InvestmentService;
import org.example.service.InvestorService;
import org.example.service.OrderService;
import org.example.tutorial.KafkaMessage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

@SpringBootApplication
public class Main implements CommandLineRunner{

    private InvestorService investorService; // Репозиторий для инвесторов
    private OrderService orderService; // Репозиторий для заказов
    private InvestmentService investmentService;
    private DataLoader dataLoader;
    private KafkaSender kafkaSender;

    public Main(InvestorService investorService, OrderService orderService, InvestmentService investmentService, DataLoader dataLoader, KafkaSender kafkaSender) {
        this.investorService = investorService;
        this.orderService = orderService;
        this.investmentService = investmentService;
        this.dataLoader = dataLoader;
        this.kafkaSender = kafkaSender;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        dataLoader.initDatabase();
        CreatInvestment(new InvestmentModel(1L, (InvestorModel)investorService.findById(1l).getBody(),(OrderModel) orderService.findById(1l).getBody(), true, false, true, new BigDecimal("1000.00"), System.currentTimeMillis()), "credeltial");

    }

    public void CreatInvestment(InvestmentModel investment, String credential){
        System.out.println("CreatInvestment");
        ResponseEntity<?> response = investmentService.create(investment);

        if(response.getStatusCode() == HttpStatus.OK){
            KafkaMessage message = KafkaMessage.newBuilder()
                    .setId("12345")
                    .setRequestType(KafkaMessage.RequestType.REQUEST)
                    .setBody(credential)
                    .build();

            String topicName = "payment-topic"; // Укажите название вашего топика
            String key = message.getId(); // Используем id как ключ

            kafkaSender.sendMessage(topicName, key, message);
        }
    }

}



