package de.wkss.addisonpayment.config;

import de.wkss.addisonpayment.domain.Invoice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Configuration
public class AppConfiguration {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JedisConnectionFactory jedisConnFactory() {

        try {
            URI redistogoUri = new URI(System.getenv("REDIS_URL"));

            JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();

            jedisConnFactory.setUsePool(true);
            jedisConnFactory.setHostName(redistogoUri.getHost());
            jedisConnFactory.setPort(redistogoUri.getPort());
            jedisConnFactory.setPassword(redistogoUri.getUserInfo().split(":", 2)[1]);

            jedisConnFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);

            return jedisConnFactory;

        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        return stringRedisSerializer;
    }

    @Bean
    public JacksonJsonRedisSerializer<Invoice> jacksonJsonRedisJsonSerializer() {
        JacksonJsonRedisSerializer<Invoice> jacksonJsonRedisJsonSerializer = new JacksonJsonRedisSerializer<>(Invoice.class);
        return jacksonJsonRedisJsonSerializer;
    }

    @Bean
    public RedisTemplate<String, Invoice> redisTemplate() {
        RedisTemplate<String, Invoice> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnFactory());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jacksonJsonRedisJsonSerializer());
        return redisTemplate;
    }
}
