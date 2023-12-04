package com.simtechdata.utils;

import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class Colors {


    public static WritableImage overlayRed(Image in) {
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
                if(pColor.getOpacity() > 0) {
                    double oRed      = pColor.getRed();
                    double oGreen    = pColor.getGreen();
                    double oBlue     = pColor.getBlue();
                    double ratioRdGr = oRed / oGreen;
                    double ratioRdBl = oRed / oBlue;
                    double newRed;
                    double newGreen;
                    double newBlue;

                    if(ratioRdGr >= 1.0) {
                        newGreen = oGreen * .5;
                    }
                    else {
                        newGreen = oGreen * ratioRdGr;
                    }

                    if(ratioRdBl >= 1.0) {
                        newBlue = oBlue * .5;
                    }
                    else {
                        newBlue = oBlue * ratioRdBl;
                    }

                    newRed = oRed * 1.5;
                    newRed = Math.min(1.0, newRed);

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
