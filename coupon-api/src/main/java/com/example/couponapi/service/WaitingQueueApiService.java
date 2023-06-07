package com.example.couponapi.service;

import com.example.couponapi.dto.WaitingQueueRequest;
import com.example.couponcore.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WaitingQueueApiService {
    private final WaitingQueueService waitingQueueService;

    // TODO: redis 트랜잭션 묶어야됨
    public Boolean add(WaitingQueueRequest request) {
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
