package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.demo.service.RTSPServerSetupService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class RTSPWebSocketHandler extends TextWebSocketHandler {
    
    private final RTSPServerSetupService rtspServerSetupService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2); // Adjust pool size as needed

    public RTSPWebSocketHandler(RTSPServerSetupService rtspServerSetupService) {
        this.rtspServerSetupService = rtspServerSetupService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        String responseMessage;
        if (payload.equalsIgnoreCase("start:local")) {
            responseMessage = "Starting local file streaming...";
            session.sendMessage(new TextMessage("{\"status\": \"" + responseMessage + "\"}"));
            executorService.submit(() -> {
                String result = rtspServerSetupService.startLocalFileStreaming();
                sendMessage(session, result);
            });
        } else if (payload.startsWith("start:rtsp:")) {
            String rtspUrl = payload.substring(11).trim();
            responseMessage = "Starting RTSP streaming...";
            session.sendMessage(new TextMessage("{\"status\": \"" + responseMessage + "\"}"));
            executorService.submit(() -> {
                String result = rtspServerSetupService.startRTSPUrlStreaming(rtspUrl);
                sendMessage(session, result);
            });
        } else {
            responseMessage = "Unknown command";
            session.sendMessage(new TextMessage("{\"status\": \"" + responseMessage + "\"}"));
        }
    }

    private void sendMessage(WebSocketSession session, String result) {
        try {
            String jsonResponse = "{\"status\": \"" + result + "\"}";
            session.sendMessage(new TextMessage(jsonResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
