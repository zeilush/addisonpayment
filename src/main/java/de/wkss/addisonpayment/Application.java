package de.wkss.addisonpayment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * Created by Artur.Zeiler on 10.05.2016.
 */
@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String...args) throws Exception {
        Environment env = SpringApplication.run(Application.class, args).getEnvironment();
        log.info("Access URLs:\n----------------------------------------------------------\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n----------------------------------------------------------",
                8080,
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));
    }
}
