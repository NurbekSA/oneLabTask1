package org.example.kafka.config;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component
public class TopicSetting {
    private final Logger logger = LoggerFactory.getLogger(TopicSetting.class);

    @PostConstruct
    public void topicCreater() {
        // Настройка подключения к Kafka
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Создание AdminClient для управления топиками
        try (AdminClient adminClient = AdminClient.create(props)) {
            // Создаем новый топик с 3 разделами и фактором репликации 1
            NewTopic newPaymentTopic = new NewTopic("payment-topic", 2, (short) 1);
            NewTopic newInvestmentTopic = new NewTopic("investment-topic", 1, (short) 1);

            // Создание топика в Kafka
            adminClient.createTopics(Collections.singletonList(newPaymentTopic));
            adminClient.createTopics(Collections.singletonList(newInvestmentTopic));
            logger.info("Topic created succesful");
        } catch (Exception e) {
            logger.info("Mistake of creating a topic {}", e.getMessage());
        }
    }


    public void info() {
        // Настройка подключения к Kafka
        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Создание AdminClient для управления топиками
        try (AdminClient adminClient = AdminClient.create(props)) {
            // Получение информации о всех топиках
            Set<String> topicNames = adminClient.listTopics().names().get();
            logger.info("Topics list: {}", topicNames);

            // Получение подробной информации о конкретном топике
            TopicDescription description = adminClient.describeTopics(
                    Collections.singletonList("order-topic")).all().get().get("order-topic");// todo
            logger.info("Topic description: {}", description);
        } catch (ExecutionException | InterruptedException e) {
            logger.warn("Mistake of getting info about topic: {}", e.getMessage());
            Thread.currentThread().interrupt(); // Восстановление флага прерывания
        }
    }
}
