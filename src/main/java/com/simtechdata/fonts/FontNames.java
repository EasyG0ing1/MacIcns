package com.simtechdata.fonts;

public enum FontNames {

    FIRA_CODE_BOLD,
    FIRA_CODE_REGULAR,
    LATO_BLACK,
    LATO_HEAVY;


    public String getPath() {
        return switch (this) {
            case FIRA_CODE_BOLD -> FontNames.class.getResource("/fonts/FiraCode_Bold.ttf").toExternalForm();
            case FIRA_CODE_REGULAR -> FontNames.class.getResource("/fonts/FiraCode_Regular.ttf").toExternalForm();
            case LATO_BLACK -> FontNames.class.getResource("/fonts/Lato_Black.ttf").toExternalForm();
            case LATO_HEAVY -> FontNames.class.getResource("/fonts/Lato_Heavy.ttf").toExternalForm();
        };
    }
}
