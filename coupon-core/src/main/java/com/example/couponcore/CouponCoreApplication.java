package com.example.couponcore;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackageClasses = {CouponCoreApplication.class})
@Configuration
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"com.example.couponcore.domain"})
@EnableJpaRepositories(basePackages = {"com.example.couponcore.repository"})
public class CouponCoreApplication {

}
