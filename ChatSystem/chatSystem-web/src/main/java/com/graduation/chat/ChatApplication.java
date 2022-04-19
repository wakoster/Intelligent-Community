package com.graduation.chat;

import com.graduation.chat.controller.WebSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ChatApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ChatApplication.class, args);
        WebSocketServer.setApplicationContext(configurableApplicationContext);
    }

}
