//package org.example.kafka;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.listener.MessageListener;
//import org.springframework.kafka.listener.MessageListenerContainer;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//import java.util.concurrent.CompletableFuture;
//
//@Service
//public class KafkaRequestService {
//
//    @Autowired
//    private KafkaTemplate<String, Byte[]> kafkaTemplate;
//
//    @Autowired
//    private KafkaListenerEndpointRegistry registry;
//
//    private final String requestTopic = "request-topic";
//    private final String responseTopic = "response-topic";
//
//    public CompletableFuture<Byte[]> sendRequest(String data) {
//        String requestId = UUID.randomUUID().toString();
//
//        CompletableFuture<Byte[]> futureResponse = new CompletableFuture<>();
//
//        // Регистрируем слушатель для ожидания ответа
//        MessageListenerContainer container = registry.getListenerContainer("responseListener");
//        container.setupMessageListener((MessageListener<String, Byte[]>) message -> {
//             response = new String(message.value());
//            if (response.getRequestId().equals(requestId)) {
//                futureResponse.complete(response);
//                container.stop();
//            }
//        });
//
//        kafkaTemplate.send(requestTopic, requestMessage);
//        return futureResponse;
//    }
//}
