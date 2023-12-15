package com.simtechdata.fonts;

import javafx.scene.text.Font;

public class Fonts {
    private static Font getFont(com.simtechdata.fonts.FontNames name, double size) {
        return Font.loadFont(name.getPath(), size);
    }

    public static Font Fira_Code_Bold(double size) {
        return getFont(com.simtechdata.fonts.FontNames.FIRA_CODE_BOLD, size);
    }

    public static Font Fira_Code_Regular(double size) {
        return getFont(com.simtechdata.fonts.FontNames.FIRA_CODE_REGULAR, size);
    }

    public static Font Lato_Black(double size) {
        return getFont(com.simtechdata.fonts.FontNames.LATO_BLACK, size);
    }

    public static Font Lato_Heavy(double size) {
        return getFont(com.simtechdata.fonts.FontNames.LATO_HEAVY, size);
    }
}
