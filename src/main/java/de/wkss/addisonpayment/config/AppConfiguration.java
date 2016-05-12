package de.wkss.addisonpayment.config;

import static springfox.documentation.builders.PathSelectors.regex;
import de.wkss.addisonpayment.dal.BillInvoice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Protocol;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@Configuration
@EnableSwagger2
public class AppConfiguration {

//    @Bean
//    public Docket newsApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("greetings")
//                .apiInfo(apiInfo())
//                .pathMapping("/")
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(regex("/api/.*"))
//                .build()
//                .paths(regex("/*"))
//                .build();
//    }

    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/.*"))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Addison Lunchbox REST API")
                .description("Addison Lunchbox REST API")
                .version("1.0")
                .build();
    }

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
    public JacksonJsonRedisSerializer<BillInvoice> jacksonJsonRedisJsonSerializer() {
        JacksonJsonRedisSerializer<BillInvoice> jacksonJsonRedisJsonSerializer = new JacksonJsonRedisSerializer<>(BillInvoice.class);
        return jacksonJsonRedisJsonSerializer;
    }

    @Bean
    public RedisTemplate<String, BillInvoice> redisTemplateBillInvoice() {
        RedisTemplate<String, BillInvoice> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnFactory());
        redisTemplate.setKeySerializer(stringRedisSerializer());
        redisTemplate.setValueSerializer(jacksonJsonRedisJsonSerializer());
        return redisTemplate;
    }
}
