package com.MITProjectService.framework.redis;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.beans.factory.annotation.Value;import org.springframework.data.redis.core.RedisTemplate;import org.springframework.data.redis.core.StringRedisTemplate;import org.springframework.stereotype.Component;import org.springframework.stereotype.Service;@Servicepublic class RedisCashService {    @Autowired    private StringRedisTemplate stringRedisTemplate;    public void set(final String key, final String value)    {        stringRedisTemplate.opsForValue().set(key, value);    }}