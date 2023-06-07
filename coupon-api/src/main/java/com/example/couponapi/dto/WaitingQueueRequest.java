package com.example.couponapi.dto;

import com.example.couponcore.domain.vo.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WaitingQueueRequest {
    private String userId;
    private Event event;
}
