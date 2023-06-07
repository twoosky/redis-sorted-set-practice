package com.example.couponcore.domain;

import com.example.couponcore.domain.vo.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name ="coupon")
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Event event;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private OffsetDateTime datePublished;

    @Column(nullable = false)
    private OffsetDateTime dateExpire;

    @OneToMany(mappedBy = "coupon")
    private List<UserCoupon> users = new ArrayList<>();
}
