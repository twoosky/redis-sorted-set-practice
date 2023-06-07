package com.example.couponcore.domain;

import com.example.couponcore.domain.vo.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Getter
@Entity
@Table(name ="coupon_policy")
@NoArgsConstructor
public class CouponPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Event event;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private OffsetDateTime dateIssued;

    @Column(nullable = false)
    private OffsetDateTime dataExpire;
}
