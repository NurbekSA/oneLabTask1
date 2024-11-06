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
    private final KafkaSender kafkaSender;

    public PartitionListener(KafkaSender kafkaSender) {
        this.kafkaSender = kafkaSender;
    }

    // Listener for partition 0
    @KafkaListener(topics = "onelab.payment-api.payment-by-card", groupId = "group-1")
    public void listenPartition(ConsumerRecord<String, byte[]> messageInByte) {
        logger.info("LISTEN_PARTITION: Partition 'group-1' received a message");
        try {
            KafkaMessage message = KafkaMessage.parseFrom(messageInByte.value());

            logger.info("onelab.payment-api.payment-by-card: Received message: {}, Request type: {}", message);

            if (message.getRequestType() == KafkaMessage.RequestType.REQUEST) {
                String paymentResult = payment(message.getBody());

                KafkaMessage responseMessage;
                String replyTo = message.getReplyTo();

                if (paymentResult != null) {
                    responseMessage = KafkaMessage.newBuilder()
                            .setCorrelationId(message.getCorrelationId())
                            .setRequestResult(KafkaMessage.RequestResult.SUCCESS)
                            .setRequestType(KafkaMessage.RequestType.RESPONSE)
                            .setBody(paymentResult)
                            .build();

                    logger.info("onelab.payment-api.payment-by-card: Sent success response to {}", replyTo);
                } else {
                    responseMessage = KafkaMessage.newBuilder()
                            .setCorrelationId(message.getCorrelationId())
                            .setRequestResult(KafkaMessage.RequestResult.FAILED)
                            .setRequestType(KafkaMessage.RequestType.RESPONSE)
                            .setBody("Insufficient funds")
                            .build();

                    logger.info("onelab.payment-api.payment-by-card: Sent failure response to {}", replyTo);
                }

                kafkaSender.sendMessage(replyTo, responseMessage);
            }

        } catch (InvalidProtocolBufferException e) {
            logger.info("onelab.payment-api.payment-by-card: Deserialization error: {}", e.getMessage());
        }
    }

    String payment(String credential) {
        logger.info("PAYMENT: Processing payment for credential {}", credential);
        try {
            // Simulating a payment delay
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.info("PAYMENT: Error during payment process: {}", e.getMessage());
        }
        return "Detailed payment receipt";
    }
}
