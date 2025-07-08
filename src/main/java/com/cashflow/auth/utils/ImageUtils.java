package com.cashflow.auth.utils;

public class ImageUtils {

    private ImageUtils() {}

    public static byte[] convertBase64ToByteArray(String base64Image) {
        if (base64Image == null || base64Image.isEmpty()) {
            return new byte[0];
        }
        String[] parts = base64Image.split(",");
        String imageData = parts.length > 1 ? parts[1] : parts[0];
        return java.util.Base64.getDecoder().decode(imageData);
    }
}
