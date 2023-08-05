package com.simtechdata.macicns.settings;

import java.util.prefs.Preferences;

import static com.simtechdata.macicns.settings.LABEL.FOLDER;

public class Clear {

    public static final Clear INSTANCE = new Clear();

    private Clear() {
    }

    private final Preferences prefs = LABEL.prefs;

    public void folder() {
        prefs.remove(FOLDER.Name());
    }
}
