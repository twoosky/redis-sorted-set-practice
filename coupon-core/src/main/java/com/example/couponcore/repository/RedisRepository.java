package com.example.couponcore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String SET_BASE_KEY = "issued-";

    public Boolean zAdd(String key, Object value, Double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> zPop(String key, Long count) {
        return redisTemplate.opsForZSet().popMin(key, count).stream()
                .map(tuple -> tuple.getValue() + " " + tuple.getScore())
                .collect(Collectors.toSet());
    }

    public Long zRank(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public Set<Object> zRange(String key, Long startRank, Long endRank) {
        return new HashSet<>(redisTemplate.opsForZSet().range(key, startRank, endRank));
    }

    public Long zSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(SET_BASE_KEY + key, value);
    }

    public Long sAdd(String key, Object value) {
        return redisTemplate.opsForSet().add(SET_BASE_KEY + key, value);
    }

    public Long sRem(String key, Object value) {
        return redisTemplate.opsForSet().remove(SET_BASE_KEY + key, value);
    }

    public Long sCard(String key) {
        return redisTemplate.opsForSet().size(SET_BASE_KEY + key);
    }
}
