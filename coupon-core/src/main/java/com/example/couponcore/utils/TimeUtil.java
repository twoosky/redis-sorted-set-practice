package com.example.couponcore.utils;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class TimeUtil {

    public static OffsetDateTime millsToOffsetDateTime(long millis) {
        Instant instant = Instant.ofEpochMilli(millis);
        return instant.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }
}
