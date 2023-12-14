package com.simtechdata.utils;

import com.simtechdata.enums.OverlayColor;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Colors {

    private static double getNewBlue(double oBlue, double ratio) {
        double newBlue;
        if (ratio >= 1.0) {
            newBlue = oBlue * .5;
        }
        else {
            newBlue = oBlue * ratio;
        }
        return newBlue;
    }

    private static double getNewRed(double oRed, double oGreen) {
        double ratio = oRed / oGreen;
        double newRed;
        if (ratio >= 1.0) {
            newRed = oRed * .5;
        }
        else {
            newRed = oRed * ratio;
        }
        return newRed;
    }

    private static double getNewGreen(double oRed, double oGreen) {
        double ratio = oGreen / oRed;
        double newGreen;
        if (ratio >= 1.0) {
            newGreen = oGreen * .5;
        }
        else {
            newGreen = oGreen * ratio;
        }
        return newGreen;
    }

    public static WritableImage overlay(Image in, OverlayColor overlayColor) {
        WritableImage out = new WritableImage(
                (int) in.getWidth(),
                (int) in.getHeight()
        );

        PixelReader reader = in.getPixelReader();
        PixelWriter writer = out.getPixelWriter();

        int width = (int) in.getWidth();
        int height = (int) in.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pColor = reader.getColor(x, y);
                if (pColor.getOpacity() > 0) {
                    double oRed = pColor.getRed();
                    double oGreen = pColor.getGreen();
                    double oBlue = pColor.getBlue();
                    double newRed = 0.0;
                    double newGreen = 0.0;
                    double newBlue = 0.0;

                    switch (overlayColor) {
                        case RED -> {
                            double blueRatio = oRed / oBlue;
                            newRed = oRed * 1.5;
                            newRed = Math.min(1.0, newRed);
                            newGreen = getNewGreen(oRed, oGreen);
                            newBlue = getNewBlue(oBlue, blueRatio);
                        }

                        case GREEN -> {
                            double blueRatio = oGreen / oBlue;
                            newRed = getNewRed(oRed, oGreen);
                            newGreen = oGreen * 1.5;
                            newGreen = Math.min(1.0, newGreen);
                            newBlue = getNewBlue(oBlue, blueRatio);
                        }

                        case BLUE -> {
                            newRed = oRed * .25;
                            newGreen = oGreen * .25;
                            newBlue = oBlue + (oBlue * .25);
                            newBlue = Math.min(newBlue, 1.0);
                        }

                        case ORANGE -> {
                            double halfRd = oRed * .5;
                            double halfGr = oGreen * .5;
                            double halfBl = oBlue * .5;

                            double orngRed = oRed + halfRd;
                            double orngGreen = oGreen + halfGr;
                            double orngBlue = oBlue - halfBl;

                            newRed = Math.min(orngRed, 1.0);
                            newGreen = Math.min(orngGreen, 1.0);
                            newBlue = orngBlue;
                            while ((newRed / newGreen) < 2.0) {
                                newGreen -= .01;
                                if (newGreen < .03) {
                                    newGreen = .03;
                                    break;
                                }
                            }
                        }
                    }

                    Color blended = Color.color(
                            newRed,
                            newGreen,
                            newBlue,
                            pColor.getOpacity());

                    writer.setColor(x, y, blended);
                }
            }
        }
        return out;
    }
}
