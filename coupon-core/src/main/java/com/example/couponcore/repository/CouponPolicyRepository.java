package com.example.couponcore.repository;

import com.example.couponcore.domain.CouponPolicy;
import com.example.couponcore.domain.vo.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {
    Optional<CouponPolicy> findByEvent(Event event);
}
