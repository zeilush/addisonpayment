package de.wkss.addisonpayment.config;

import de.wkss.addisonpayment.domain.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Protocol;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfiguration {

    @Value("${config.redis.host}")
    private String host;

    @Value("${config.redis.port}")
    private int port;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JedisConnectionFactory jedisConnFactory() {

        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();

        jedisConnFactory.setUsePool(true);
        jedisConnFactory.setHostName(host);
        jedisConnFactory.setPort(port);
        jedisConnFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
//            jedisConnFactory.setPassword(redistogoUri.getUserInfo().split(":", 2)[1]);

        return jedisConnFactory;
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        return stringRedisSerializer;
    }

    @Bean
    public JacksonJsonRedisSerializer<Payment> jacksonJsonRedisJsonSerializer() {
        JacksonJsonRedisSerializer<Payment> jacksonJsonRedisJsonSerializer = new JacksonJsonRedisSerializer<>(Payment.class);
        return jacksonJsonRedisJsonSerializer;
    }

    @Bean
    public RedisTemplate<String, Payment> redisTemplate() {
        RedisTemplate<String, Payment> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnFactory());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jacksonJsonRedisJsonSerializer());
        return redisTemplate;
    }
}
