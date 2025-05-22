package com.tellin;

import com.tellin.support.logging.Log;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = "com.tellin.support.config")
public class TellInBackendApplication {

    @PreDestroy
    public void destroy() {
        Log.info("[\uD83D\uDED1  기존 커넥션 정리 시작]");
    }
    public static void main(String[] args) {
        SpringApplication.run(TellInBackendApplication.class, args);
    }

}
