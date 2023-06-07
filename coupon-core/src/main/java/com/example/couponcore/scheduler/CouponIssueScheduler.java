package com.example.couponcore.scheduler;

import com.example.couponcore.domain.vo.Event;
import com.example.couponcore.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableScheduling
@Component
@RequiredArgsConstructor
public class CouponIssueScheduler {

    private final CouponIssueService couponIssueService;

    @Scheduled(fixedDelay = 1000)
    public void run() {
        couponIssueService.publish(Event.CHICKEN);
    }
}
