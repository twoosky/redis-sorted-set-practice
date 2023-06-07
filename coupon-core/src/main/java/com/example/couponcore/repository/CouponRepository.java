package com.example.couponcore.repository;

import com.example.couponcore.domain.Coupon;
import com.example.couponcore.domain.vo.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByEvent(Event event);
}
