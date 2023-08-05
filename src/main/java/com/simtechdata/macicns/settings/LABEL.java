package com.simtechdata.macicns.settings;

import java.util.prefs.Preferences;

public enum LABEL {

    FOLDER;

    public String Name(LABEL this) {
        return switch (this) {
            case FOLDER -> "FOLDER";
        };

    }

    public static final Preferences prefs = Preferences.userNodeForPackage(LABEL.class);
}
