package com.simtechdata.build;

import javafx.scene.image.Image;

import java.net.URL;

public class CheckBoxs {


    private static final URL u512U = CheckBoxs.class.getResource("/check/512U.png");
    private static final URL u512C = CheckBoxs.class.getResource("/check/512C.png");
    private static final URL u256U = CheckBoxs.class.getResource("/check/256U.png");
    private static final URL u256C = CheckBoxs.class.getResource("/check/256C.png");
    private static final URL u128U = CheckBoxs.class.getResource("/check/128U.png");
    private static final URL u128C = CheckBoxs.class.getResource("/check/128C.png");
    private static final URL u64U = CheckBoxs.class.getResource("/check/64U.png");
    private static final URL u64C = CheckBoxs.class.getResource("/check/64C.png");
    private static final URL u32U = CheckBoxs.class.getResource("/check/32U.png");
    private static final URL u32C = CheckBoxs.class.getResource("/check/32C.png");
    private static final URL u16U = CheckBoxs.class.getResource("/check/16U.png");
    private static final URL u16C = CheckBoxs.class.getResource("/check/16C.png");
    private static final Image img512U = new Image(u512U.toExternalForm());
    private static final Image img512C = new Image(u512C.toExternalForm());
    private static final Image img256U = new Image(u256U.toExternalForm());
    private static final Image img256C = new Image(u256C.toExternalForm());
    private static final Image img128U = new Image(u128U.toExternalForm());
    private static final Image img128C = new Image(u128C.toExternalForm());
    private static final Image img64U = new Image(u64U.toExternalForm());
    private static final Image img64C = new Image(u64C.toExternalForm());
    private static final Image img32U = new Image(u32U.toExternalForm());
    private static final Image img32C = new Image(u32C.toExternalForm());
    private static final Image img16U = new Image(u16U.toExternalForm());
    private static final Image img16C = new Image(u16C.toExternalForm());

    private static CheckBox c512;
    private static CheckBox c256;
    private static CheckBox c128;
    private static CheckBox c64;
    private static CheckBox c32;
    private static CheckBox c16;

    public static void reset() {
        if (c512 != null) {
            c512.reset();
            c256.reset();
            c128.reset();
            c64.reset();
            c32.reset();
            c16.reset();
        }
    }

    public static CheckBox getCheckbox(int size) {
        switch (size) {
            case 512 -> {
                c512 = new CheckBox(img512C, img512U, 90);
                return c512;
            }
            case 256 -> {
                c256 = new CheckBox(img256C, img256U, 90);
                return c256;
            }
            case 128 -> {
                c128 = new CheckBox(img128C, img128U, 90);
                return c128;
            }
            case 64 -> {
                c64 = new CheckBox(img64C, img64U, 90);
                return c64;
            }
            case 32 -> {
                c32 = new CheckBox(img32C, img32U, 90);
                return c32;
            }
            case 16 -> {
                c16 = new CheckBox(img16C, img16U, 90);
                return c16;
            }
            default -> {
                return null;
            }
        }
    }


}
