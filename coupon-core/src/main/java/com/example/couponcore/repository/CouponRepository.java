package com.example.couponcore.repository;

import com.example.couponcore.domain.CouponPolicy;
import com.example.couponcore.domain.vo.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponPolicyRepository extends JpaRepository<CouponPolicy, Long> {
    Optional<CouponPolicy> findByEvent(Event event);
}
