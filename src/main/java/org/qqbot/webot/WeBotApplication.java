package org.qqbot.webot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class WeBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeBotApplication.class, args);
    }
}
