package org.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class PartitionListener {

    // Слушатель для партиции 0
    @KafkaListener(topics = "payment-topic", groupId = "group-1")
    public void listenPartition0(ConsumerRecord<String, byte[]> record) {
        System.out.println("Партиция 0 получила сообщение: " + new String(record.value()));
    }

    // Слушатель для партиции 1
    @KafkaListener(topics = "payment-topic", groupId = "group-1")
    public void listenPartition1(ConsumerRecord<String, byte[]> record) {
        System.out.println("Партиция 1 получила сообщение: " + new String(record.value()));
    }
}

