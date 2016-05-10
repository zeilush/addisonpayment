package de.wkss.addisonpayment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@SpringBootApplication
public class Application {
    public static void main(String...args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
