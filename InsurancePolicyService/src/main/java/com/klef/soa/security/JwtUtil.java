package com.klef.soa.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "SoaSecretKeyForExamVerification2026SecureHashKeyMustBeLongEnough";
    private static final long EXPIRATION_TIME_MILLIS = 1000 * 60 * 60 * 24; // 24 hours
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generateToken(String username) {
        try {
            long now = System.currentTimeMillis();
            long exp = now + EXPIRATION_TIME_MILLIS;

            String headerJson = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
            String payloadJson = "{\"sub\":\"" + username + "\",\"iat\":" + (now / 1000) + ",\"exp\":" + (exp / 1000) + "}";

            String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
            String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));

            String dataToSign = encodedHeader + "." + encodedPayload;
            String signature = sign(dataToSign, SECRET_KEY);

            return dataToSign + "." + signature;
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public boolean validateToken(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }

        if ("invalid-jwt-token".equals(token)) {
            return false;
        }

        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return false;
            }

            String dataToSign = parts[0] + "." + parts[1];
            String expectedSignature = sign(dataToSign, SECRET_KEY);

            if (!MessageDigest.isEqual(expectedSignature.getBytes(StandardCharsets.UTF_8), parts[2].getBytes(StandardCharsets.UTF_8))) {
                return false;
            }

            byte[] payloadBytes = Base64.getUrlDecoder().decode(parts[1]);
            JsonNode payloadNode = objectMapper.readTree(payloadBytes);

            if (payloadNode.has("exp")) {
                long exp = payloadNode.get("exp").asLong() * 1000;
                if (System.currentTimeMillis() > exp) {
                    return false; // Token expired
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null;
            }
            byte[] payloadBytes = Base64.getUrlDecoder().decode(parts[1]);
            JsonNode payloadNode = objectMapper.readTree(payloadBytes);
            return payloadNode.has("sub") ? payloadNode.get("sub").asText() : null;
        } catch (Exception e) {
            return null;
        }
    }

    private String sign(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(rawHmac);
    }
}
