package com.simtechdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ImageChecker {

    public static boolean isValid(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] header = new byte[8];
            int bytesRead = fis.read(header);
            if (bytesRead >= 8) {
                if (isPNG(header) || isJPEG(header) || isGIF(header) || isTIFF(header) || isBMP(header) || isSVG(file)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean isPNG(byte[] header) {
        // PNG file signature: 89 50 4E 47 0D 0A 1A 0A
        return (header[0] == (byte) 0x89 && header[1] == (byte) 0x50 && header[2] == (byte) 0x4E
                && header[3] == (byte) 0x47 && header[4] == (byte) 0x0D && header[5] == (byte) 0x0A
                && header[6] == (byte) 0x1A && header[7] == (byte) 0x0A);
    }

    private static boolean isJPEG(byte[] header) {
        // JPEG file signature: FF D8 FF E0 or FF D8 FF E1
        return (header[0] == (byte) 0xFF && header[1] == (byte) 0xD8 && header[2] == (byte) 0xFF
                && (header[3] == (byte) 0xE0 || header[3] == (byte) 0xE1));
    }

    private static boolean isGIF(byte[] header) {
        // GIF file signature: 47 49 46 38
        return (header[0] == (byte) 0x47 && header[1] == (byte) 0x49 && header[2] == (byte) 0x46
                && header[3] == (byte) 0x38);
    }

    private static boolean isTIFF(byte[] header) {
        // TIFF file signatures: 4D 4D 00 2A (big-endian) or 49 49 2A 00 (little-endian)
        return ((header[0] == (byte) 0x4D && header[1] == (byte) 0x4D && header[2] == (byte) 0x00 && header[3] == (byte) 0x2A)
                || (header[0] == (byte) 0x49 && header[1] == (byte) 0x49 && header[2] == (byte) 0x2A && header[3] == (byte) 0x00));
    }

    private static boolean isBMP(byte[] header) {
        // BMP file signature: 42 4D
        return (header[0] == (byte) 0x42 && header[1] == (byte) 0x4D);
    }

    private static boolean isSVG(File file) {
        // SVG file signature: The first few bytes contain "<svg" (ASCII representation)
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] svgHeader = new byte[4];
            int bytesRead = fis.read(svgHeader);
            String svgHeaderString = new String(svgHeader, StandardCharsets.US_ASCII);
            return (bytesRead >= 4 && svgHeaderString.startsWith("<svg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
