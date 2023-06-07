package com.example.couponcore.service;

import com.example.couponcore.domain.CouponPolicy;
import com.example.couponcore.domain.vo.Event;
import com.example.couponcore.repository.CouponPolicyRepository;
import com.example.couponcore.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class WaitingQueueService {
    private final CouponPolicyRepository couponPolicyRepository;
    private final RedisRepository redisRepository;
    private static final long FIRST_ELEMENT = 0;
    private static final long LAST_ELEMENT = 0;

    public Boolean add(Event event, Object value) {
        double now = (double) System.currentTimeMillis();
        Boolean result = redisRepository.zAdd(event.toString(), value, now);
        log.info("[WaitingQueueService :: offer] key = {}, value = {}, score={}", event.toString(), value, now);
        return result;
    }

    public Long getOrder(Event event, Object value) {
        Long rank = redisRepository.zRank(event.toString(), value);
        log.info("[WaitingQueueService :: getOrder] {} 님의 현재 대기열은 {}명 남았습니다.", value, rank);
        return rank;
    }

    public Queue<Object> pop(Event event, long count) {
        return new LinkedList<>(redisRepository.zPop(event.toString(), count));
    }

    public Boolean canWaiting(Event event) {
        CouponPolicy couponPolicy = getCouponPolicy(event);
        return checkCouponQuantity(event, couponPolicy) && checkExpiredDate(couponPolicy);
    }

    private Boolean checkCouponQuantity(Event event, CouponPolicy couponPolicy) {
        Long quantity = couponPolicy.getQuantity();
        Long issuedCount = redisRepository.sCard(event.toString());

        return quantity > issuedCount;
    }

    private Boolean checkExpiredDate(CouponPolicy couponPolicy) {
        return OffsetDateTime.now().isBefore(couponPolicy.getDataExpire());
    }

    private CouponPolicy getCouponPolicy(Event event) {
        return couponPolicyRepository.findByEvent(event)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이벤트입니다."));
    }
}
