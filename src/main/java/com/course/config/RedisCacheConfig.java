package com.course.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class RedisCacheConfig {
	 @Bean
	     RedisCacheConfiguration cacheConfiguration() {
	        return RedisCacheConfiguration.defaultCacheConfig()
	                .entryTtl(Duration.ofMinutes(10)) // Set TTL to 10 minutes
	                .disableCachingNullValues();
	    }
	 @Bean
	     CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
	        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
	                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
	        return RedisCacheManager.builder(connectionFactory)
	                .cacheDefaults(cacheConfiguration)
	                .build();
	    }
}
