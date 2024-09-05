package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	private final RTSPWebSocketHandler rtspWebSocketHandler;

    public WebSocketConfig(RTSPWebSocketHandler rtspWebSocketHandler) {
        this.rtspWebSocketHandler = rtspWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(rtspWebSocketHandler, "/live-stream").setAllowedOrigins("*");
    }
}