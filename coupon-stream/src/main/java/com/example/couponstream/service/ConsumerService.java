package com.example.couponstream.service;

import com.example.couponcore.domain.UserCoupon;
import com.example.couponcore.domain.vo.Event;
import com.example.couponcore.service.UserCouponSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {
    private final UserCouponSaveService userCouponSaveService;

    @KafkaListener(topics = "coupon-CHICKEN")
    public void listenCouponIssued(Object message) {
        String userId = ((String) message).split(" ")[0];
        long issuedTime = Long.parseLong(((String) message).split(" ")[1]);

        UserCoupon userCoupon = userCouponSaveService.save(Event.CHICKEN, userId, issuedTime);
        log.info("[ConsumerService] userId={} coupon::{} 쿠폰 발급 성공", userCoupon.getUser().getId(), userCoupon.getCoupon().getEvent().toString());
    }
}
