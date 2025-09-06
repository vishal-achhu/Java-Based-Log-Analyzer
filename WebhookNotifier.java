package com.example.loganalyzer;

import java.io.*;
import java.net.*;

public class WebhookNotifier {
    public static void send(String url, String message) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            String payload = "{\"text\":\""+message.replace("\","\\").replace(""","\"")+"\"}";
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes());
            }
            int resp = conn.getResponseCode();
            if (resp >= 400) {
                System.err.println("Webhook failed: " + resp);
            }
        } catch (Exception e) {
            System.err.println("Webhook error: " + e.getMessage());
        }
    }
}
