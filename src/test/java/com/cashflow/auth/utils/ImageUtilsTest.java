package com.cashflow.auth.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilsTest {

    @Test
    void givenEmptyBase64Image_whenConvertBase64ToByteArray_thenReturnsEmptyByteArray() {
        String base64Image = "";
        byte[] result = ImageUtils.convertBase64ToByteArray(base64Image);
        assertArrayEquals(new byte[0], result);
    }

    @Test
    void givenNullBase64Image_whenConvertBase64ToByteArray_thenReturnsEmptyByteArray() {
        String base64Image = null;
        byte[] result = ImageUtils.convertBase64ToByteArray(base64Image);
        assertArrayEquals(new byte[0], result);
    }

    @Test
    void givenValidBase64Image_whenConvertBase64ToByteArray_thenReturnsByteArray() {
        String base64Image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAUA" +
                "AAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO" +
                "9TXL0Y4OHwAAAABJRU5ErkJggg==";
        byte[] result = ImageUtils.convertBase64ToByteArray(base64Image);
        assertNotNull(result);
        assertNotEquals(0, result.length);
    }

}