package com.simtechdata.macicns.settings;

import java.util.prefs.Preferences;

import static com.simtechdata.macicns.settings.LABEL.FOLDER;

public class Set {

    public static final Set INSTANCE = new Set();

    private Set() {
    }

    private final Preferences prefs = LABEL.prefs;

    public void folder(String value) {
        AppSettings.clear.folder();
        prefs.put(FOLDER.Name(), value);
    }
}
