package com.example.couponcore.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DistributedLockAspect {
    private final RedissonClient redissonClient;

    @Around("@annotation(DistributedLock)")
    public Object lock(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DistributedLock distributedLock = signature.getMethod().getAnnotation(DistributedLock.class);
        log.info("[DistributedLock] {} 에서 Lock::{} 획득 시도", signature.getMethod().getName(), distributedLock.lockName());
        RLock lock = redissonClient.getLock(distributedLock.lockName());

        try {
            boolean available = lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!available) {
                log.info("[DistributedLock] {} 에서 Lock::{} 획득 실패", signature.getMethod().getName(), distributedLock.lockName());
                throw new IllegalStateException(distributedLock.lockName() + " lock 획득 실패");
            }
            log.info("[DistributedLock] {} 에서 Lock::{} 획득", signature.getMethod().getName(), distributedLock.lockName());
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new IllegalStateException(distributedLock.lockName() + " lock 획득 실패");
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("[DistributedLock] {} 에서 Lock::{} 반납", signature.getMethod().getName(), distributedLock.lockName());
            }
        }
    }
}
