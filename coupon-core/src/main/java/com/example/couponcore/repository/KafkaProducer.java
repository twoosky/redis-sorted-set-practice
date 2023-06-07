package com.example.couponcore.repository;

import com.example.couponcore.domain.vo.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private static final String BASE_TOPIC = "coupon-";

    public void sendMessage(Event event, String message, KafkaCallback callback) {
        String topic = BASE_TOPIC + event.toString();
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                callback.call();
                log.info("[Unable to send] topic={}, value={}", BASE_TOPIC + event, message);
            } else {
                log.info("[Success to send] topic={}, value={}", BASE_TOPIC + event, message);
            }
        });
    }
}
