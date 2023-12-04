package com.simtechdata.settings;

import java.util.prefs.Preferences;

import static com.simtechdata.settings.LABEL.FOLDER;


public class Get {

    public static final Get INSTANCE = new Get();

    private Get() {
    }

    private final Preferences prefs = LABEL.prefs;

    public String folder() {
        return prefs.get(FOLDER.Name(), System.getProperty("user.home"));
    }
}
