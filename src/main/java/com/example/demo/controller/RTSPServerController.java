package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.RTSPServerSetupService;

@RestController
public class RTSPServerController {

	@Autowired
    private RTSPServerSetupService rtspService;

    @PostMapping("/start-local-stream")
    public ResponseEntity<Map<String, String>> startLocalStream() {
        String responseMessage = rtspService.startLocalFileStreaming();
        Map<String, String> response = new HashMap<>();
        response.put("status", responseMessage);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/start-rtsp-stream")
    public ResponseEntity<Map<String, String>> startRTSPStream(@RequestParam String rtspUrl) {
        String responseMessage = rtspService.startRTSPUrlStreaming(rtspUrl);
        Map<String, String> response = new HashMap<>();
        response.put("status", responseMessage);
        return ResponseEntity.ok(response);
    }
}
