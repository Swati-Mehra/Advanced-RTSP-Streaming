package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

@Service
public class RTSPServerSetupService {

	public String startLocalFileStreaming() {
		return runBatchFile("stream_local_file.bat", null);
	}

	public String startRTSPUrlStreaming(String rtspUrl) {
		return runBatchFile("listen_rtsp_server.bat", rtspUrl);
	}

	private String runBatchFile(String batFileName, String argument) {
		try {
			File batFile = new File(batFileName);
			if (!batFile.exists()) {
				return "Batch file not found: " + batFileName;
			}

			ProcessBuilder processBuilder;
			if (argument != null) {
				processBuilder = new ProcessBuilder(batFileName, argument);
			} else {
				processBuilder = new ProcessBuilder(batFileName);
			}

			processBuilder.redirectErrorStream(true);
			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			process.waitFor();
			return "Streaming process completed successfully.";

		} catch (IOException | InterruptedException e) {
			Thread.currentThread().interrupt();
			return "An error occurred: " + e.getMessage();
		}
	}
}
