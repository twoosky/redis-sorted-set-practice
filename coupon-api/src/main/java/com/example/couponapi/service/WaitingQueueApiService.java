package com.example.couponapi.service;

import com.example.couponapi.dto.WaitingQueueRequest;
import com.example.couponcore.service.WaitingQueueService;
import com.example.couponcore.utils.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WaitingQueueApiService {
    private final WaitingQueueService waitingQueueService;

//    @DistributedLock(lockName = "issue_lock", waitTime = 3000, leaseTime = 3000)
    public Boolean add(WaitingQueueRequest request) {
        log.info("[WaitingQueueApiService - add] event={}, userId={}", request.getEvent(), request.getUserId());
        if (!waitingQueueService.canWaiting(request.getEvent())) {
            return false;
        }

        return waitingQueueService.add(
                request.getEvent(),
                request.getUserId()
        );
    }

    public Long getOrder(WaitingQueueRequest request) {
        return waitingQueueService.getOrder(request.getEvent(), request.getUserId());
    }
}
