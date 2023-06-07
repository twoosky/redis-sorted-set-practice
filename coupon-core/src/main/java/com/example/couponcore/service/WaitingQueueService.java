package com.example.couponcore.service;

import com.example.couponcore.domain.Coupon;
import com.example.couponcore.domain.vo.Event;
import com.example.couponcore.repository.CouponRepository;
import com.example.couponcore.repository.RedisRepository;
import com.example.couponcore.utils.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class WaitingQueueService {
    private final CouponRepository couponRepository;
    private final RedisRepository redisRepository;

    public Boolean add(Event event, Object value) {
        double now = (double) System.currentTimeMillis();
        Boolean result = redisRepository.zAdd(event.toString(), value, now);
        log.info("[WaitingQueueService :: add] key = {}, value = {}, score={}", event, value, now);
        return result;
    }

    public Long getOrder(Event event, Object value) {
        Long rank = redisRepository.zRank(event.toString(), value);
        log.info("[WaitingQueueService :: getOrder] {} 님의 현재 대기열은 {}명 남았습니다.", value, rank);
        return rank;
    }

    public Boolean canWaiting(Event event) {
        Coupon coupon = getCoupon(event);
        return checkCouponQuantity(event, coupon) && checkDate(coupon);
    }

    private Boolean checkCouponQuantity(Event event, Coupon coupon) {
        Long quantity = coupon.getQuantity();
        Long issuedCount = redisRepository.sCard(event.toString());

        return quantity > issuedCount;
    }

    private Boolean checkDate(Coupon coupon) {
        return checkPublishedDate(coupon) && checkExpiredDate(coupon);
    }

    private Boolean checkExpiredDate(Coupon coupon) {
        return OffsetDateTime.now().isBefore(coupon.getDateExpire());
    }

    private Boolean checkPublishedDate(Coupon coupon) {
        return OffsetDateTime.now().isAfter(coupon.getDatePublished());
    }

    private Coupon getCoupon(Event event) {
        return couponRepository.findByEvent(event)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이벤트입니다."));
    }
}
