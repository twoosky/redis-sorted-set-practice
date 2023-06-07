package com.example.couponapi.controller;

import com.example.couponapi.dto.WaitingQueueRequest;
import com.example.couponapi.service.WaitingQueueApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WaitingQueueController {
    private final WaitingQueueApiService waitingQueueApiService;

    @PostMapping("/queue")
    public Boolean wait(@RequestBody WaitingQueueRequest request) {
        return waitingQueueApiService.add(request);
    }

    @GetMapping("/queue")
    public Long getOrder(@RequestBody WaitingQueueRequest request) {
        return waitingQueueApiService.getOrder(request);
    }
}
