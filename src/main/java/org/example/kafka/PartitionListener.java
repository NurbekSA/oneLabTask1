package org.example.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.kafka.proto.tutorial.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PartitionListener {
    private static final Logger logger = LoggerFactory.getLogger(PartitionListener.class);
    private KafkaSender kafkaSender;

    public PartitionListener(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    // Слушатель для партиции 0
    @KafkaListener(topics = "payment-topic", groupId = "group-1")
    public void listenPartition(ConsumerRecord<String, byte[]> messageInByte) {
        logger.info("Партиция 'group-1' получила сообщение");
        try {
            KafkaMessage message = KafkaMessage.parseFrom(messageInByte.value());

            logger.info("Принято сообщение: {}, Тип запроса: {}", message, message.getRequestType());

            if(message.getRequestType() == KafkaMessage.RequestType.REQUEST && message.getMethosType() == KafkaMessage.MethodType.CREAT){

                KafkaMessage responseMessage = KafkaMessage.newBuilder()
                        .setId(message.getId())
                        .setRequestType(KafkaMessage.RequestType.RESPONSE)
                        .setBody(payment(message.getBody()))
                        .build();
                String topicName = message.getReplyTo(); // Укажите название вашего топика

                kafkaSender.sendMessage(topicName,responseMessage);
                logger.info("Отправлен ответ на {}", topicName);
            }

        } catch (InvalidProtocolBufferException e) {
            logger.info("Ошибка десериализации: {}", e.getMessage());
        }
    }

    String payment(String credential){
        logger.info(credential);
        try {
            Thread.sleep(1000);
        }
        catch (Exception e){
            logger.info(e.getMessage());
        }
        return "Длинный чек об оплате";
    }
}

