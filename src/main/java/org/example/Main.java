package org.example;

import org.example.model.InvestorModel;
import org.example.model.OrderModel;
import org.example.repository.AdminRepo;
import org.example.repository.InvestorRepo; // Замените на ваш путь к репозиторию
import org.example.repository.OrderRepo; // Замените на ваш путь к репозиторию
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main implements CommandLineRunner{

    @Autowired
    private InvestorRepo investorRepo; // Репозиторий для инвесторов

    @Autowired
    private OrderRepo orderRepo; // Репозиторий для заказов

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    @Override
    public void run(String... args){
        while (true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Введите ИИН (в бд есть: 123456789, 987654321, 12345678910): ");
            String iin = scanner.nextLine();
            InvestorModel investor = investorRepo.findByIin(iin);

            if (investor != null) {
                System.out.println("Инвестор найден: " + investor.getMail());
            } else {
                System.out.println("Инвестор с таким ИИН не найден.");
            }






        }
    }

}



