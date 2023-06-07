package com.example.couponcore.service;

import com.example.couponcore.domain.vo.Event;
import com.example.couponcore.repository.UserCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssueService {
    private final UserCouponRepository userCouponRepository;

    public void issue(Event event, Object value) {

    }
}
