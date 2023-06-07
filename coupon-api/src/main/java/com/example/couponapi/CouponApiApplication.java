package com.example.couponapi;

import com.example.couponcore.CouponCoreApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {CouponCoreApplication.class})
public class CouponApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponApiApplication.class, args);
    }

}
