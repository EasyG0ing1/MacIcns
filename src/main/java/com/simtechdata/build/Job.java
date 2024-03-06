package com.simtechdata.build;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.glavo.png.javafx.PNGJavaFXUtils;

import java.io.IOException;
import java.nio.file.Path;

public class Job {
    public Job(Image original, Path path, int size) {
        this.original = original;
        this.path = path;
        this.size = size;
    }

    public static WritableImage lastImage;
    private final Image original;
    private final Path path;
    private final int size;
    private int lastSize = 0;

    public void saveFile() {
        try {
            PNGJavaFXUtils.writeImage(getImage(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private WritableImage getImage() {
        try {
            WritableImage writableImage;
            if(lastImage == null) {
                writableImage = resizeImage(original, size, size);
                lastImage = writableImage;
                lastSize = size;
            }
            else {
                if(size == lastSize) {
                    writableImage = lastImage;
                }
                else {
                    writableImage = resizeImage(lastImage, size, size);
                    lastSize = size;
                }
            }
            return writableImage;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private WritableImage resizeImage(Image originalImage, int targetWidth, int targetHeight) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();

        WritableImage resizedImage = new WritableImage(targetWidth, targetHeight);
        for (int x = 0; x < targetWidth; x++) {
            for (int y = 0; y < targetHeight; y++) {
                double sourceX = x * width / (double) targetWidth;
                double sourceY = y * height / (double) targetHeight;
                Color color = originalImage.getPixelReader().getColor((int) sourceX, (int) sourceY);
                resizedImage.getPixelWriter().setColor(x, y, color);
            }
        }
        return resizedImage;
    }
}
