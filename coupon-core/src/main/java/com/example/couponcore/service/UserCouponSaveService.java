package com.example.couponcore.service;

import com.example.couponcore.domain.Coupon;
import com.example.couponcore.domain.User;
import com.example.couponcore.domain.UserCoupon;
import com.example.couponcore.domain.vo.Event;
import com.example.couponcore.repository.CouponRepository;
import com.example.couponcore.repository.UserCouponRepository;
import com.example.couponcore.repository.UserRepository;
import com.example.couponcore.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCouponSaveService {
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    @Transactional
    public UserCoupon save(Event event, String userId, long issuedTime) {
        Coupon coupon = getCoupon(event);
        User user = getUser(Long.parseLong(userId));

        return userCouponRepository.save(
            new UserCoupon(
                user,
                coupon,
                TimeUtil.millsToOffsetDateTime(issuedTime)
            )
        );
    }

    private Coupon getCoupon(Event event) {
        return couponRepository.findByEvent(event)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이벤트입니다."));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));
    }
}
