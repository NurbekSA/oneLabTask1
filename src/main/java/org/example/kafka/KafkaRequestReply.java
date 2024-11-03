package org.example.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.kafka.proto.tutorial.KafkaMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;



import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class KafkaRequestReply {
    private final Logger logger = LoggerFactory.getLogger(KafkaRequestReply.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final KafkaListenerEndpointRegistry registry;
    private final ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory;

    public CompletableFuture<KafkaMessage> sendRequest(String data, String requestTopic, String responseTopic) throws InterruptedException {
        String requestId = UUID.randomUUID().toString();
        CompletableFuture<KafkaMessage> futureResponse = new CompletableFuture<>();
        CountDownLatch consumerReadyLatch = new CountDownLatch(1);

        // Create container properties and set the message listener
        ContainerProperties containerProperties = new ContainerProperties(responseTopic);
        containerProperties.setGroupId("dynamic-group-" + requestId); // Unique Group ID
        containerProperties.setMessageListener((MessageListener<String, byte[]>) message -> {
            try {
                logger.info("KafkaRequestReply received a message");
                KafkaMessage kafkaMessage = KafkaMessage.parseFrom(message.value());
                if (kafkaMessage.getId().equals(requestId)) {
                    futureResponse.complete(kafkaMessage);
                    logger.info("KafkaRequestReply stops listening");
                }
            } catch (InvalidProtocolBufferException e) {
                futureResponse.completeExceptionally(e);
            }
        });

        // Add a listener to know when the consumer is ready
        containerProperties.setConsumerRebalanceListener(new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                // Signal that the consumer is ready
                consumerReadyLatch.countDown();
            }

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                // Do nothing
            }

            @Override
            public void onPartitionsLost(Collection<TopicPartition> partitions) {
                // Do nothing
            }
        });

        // Create and start the container
        ConcurrentMessageListenerContainer<String, byte[]> container =
                new ConcurrentMessageListenerContainer<>(kafkaListenerContainerFactory.getConsumerFactory(), containerProperties);
        container.start();

        // Wait for the consumer to be ready
        consumerReadyLatch.await();

        // Send the request message
        KafkaMessage requestMessage = KafkaMessage.newBuilder()
                .setId(requestId)
                .setRequestType(KafkaMessage.RequestType.REQUEST)
                .setMethosType(KafkaMessage.MethodType.CREAT)
                .setBody(data)
                .setReplyTo(responseTopic)
                .build();

        byte[] messageBytes = requestMessage.toByteArray();
        kafkaTemplate.send(requestTopic, messageBytes);

        // Stop the container when the future is completed
        futureResponse.whenComplete((response, throwable) -> container.stop());

        return futureResponse;
    }
}
