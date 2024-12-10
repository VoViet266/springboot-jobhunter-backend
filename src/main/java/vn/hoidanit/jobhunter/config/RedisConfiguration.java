// package vn.hoidanit.jobhunter.config;

// import java.time.Duration;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.cache.annotation.EnableCaching;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.redis.cache.RedisCacheConfiguration;
// import org.springframework.data.redis.cache.RedisCacheManager;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
// import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
// // import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
// import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
// import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
// import org.springframework.data.redis.serializer.StringRedisSerializer;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.module.SimpleModule;
// import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

// @Configuration
// @EnableCaching
// public class RedisConfiguration {

//     @Value("${spring.data.redis.host}")
//     private String host;
//     @Value("${spring.data.redis.port}")
//     private String port;

//     @Bean
//     JedisConnectionFactory JedisConnectionFactory() {
//         RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//         redisStandaloneConfiguration.setHostName(host);
//         redisStandaloneConfiguration.setPort(Integer.parseInt(port));
//         return new JedisConnectionFactory(redisStandaloneConfiguration);
//     }

//     @Bean
//     RedisCacheManager cacheManager(RedisConnectionFactory factory) {
//         RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                 .entryTtl(Duration.ofMinutes(10)); // Cấu hình TTL cho cache (tuỳ chỉnh)

//         return RedisCacheManager.builder(factory)
//                 .cacheDefaults(config)
//                 .build();
//     }

//     @Bean
//     RedisTemplate<String, Object> redisTemplate() {
//         RedisTemplate<String, Object> template = new RedisTemplate<>();
//         template.setConnectionFactory(JedisConnectionFactory());

//         template.setKeySerializer(new StringRedisSerializer());
//         template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

//         template.setHashKeySerializer(new StringRedisSerializer());
//         template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());


//         return template;
//     }

//     @Bean
//     ObjectMapper redisObjectMapper() {
//         ObjectMapper objectMapper = new ObjectMapper();
//         SimpleModule module = new SimpleModule();
//         module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
//         module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
//         objectMapper.registerModule(module);
//         return objectMapper;
//     }

// }
