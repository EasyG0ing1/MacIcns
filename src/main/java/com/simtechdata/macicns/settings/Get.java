package com.simtechdata.macicns.settings;

import java.util.prefs.Preferences;

import static com.simtechdata.macicns.settings.LABEL.*;

public class Get {

    public static final Get INSTANCE = new Get();

    private Get() {
    }

    private final Preferences prefs = LABEL.prefs;

    public String folder() {
        return prefs.get(FOLDER.Name(), System.getProperty("user.home"));
    }
}
