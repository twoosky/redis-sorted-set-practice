package com.example.couponcore.service;

import com.example.couponcore.domain.vo.Event;
import com.example.couponcore.repository.KafkaProducer;
import com.example.couponcore.repository.RedisRepository;
import com.example.couponcore.utils.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponIssueService {
    private final KafkaProducer kafkaProducer;
    private final RedisRepository redisRepository;
    private static final long PUBLISH_SIZE = 10;

    public void publish(Event event) {
        long start = 0;
        long end = PUBLISH_SIZE - 1;
        log.info("[CouponIssueService - publish] event={}", event);
        redisRepository.zRange(event.toString(), start, end)
                .forEach(value -> issue(event, value));
    }

    private void issue(Event event, Object value) {
        log.info("[CouponIssueService - issue] event={}, value={}", event, value);
        redisRepository.sAdd(event.toString(), value);
        log.info("[CouponIssueService - issue2] event={}, value={}", event, value);
        String valueScore = (String) redisRepository.zPop(event.toString(), 1L).iterator().next();
        log.info("[CouponIssueService - issue3] event={}, valueScore={}", event, valueScore);
        send(event, valueScore);
        log.info("[CouponIssueService - kafkaSend] event={}, value={}", event, value);
    }

    private void send(Event event, String valueScore) {
        String value = valueScore.split(" ")[0];
        double score = Double.parseDouble(valueScore.split(" ")[1]);

        log.info("[CouponIssueService - kafkaSend start] event={}, value={}", event, value);
        kafkaProducer.sendMessage(event, valueScore, () -> {
            redisRepository.sRem(event.toString(), value);
            redisRepository.zAdd(event.toString(), value, score);
        });
        log.info("[CouponIssueService - kafkaSend end] event={}, value={}", event, value);
    }
}
