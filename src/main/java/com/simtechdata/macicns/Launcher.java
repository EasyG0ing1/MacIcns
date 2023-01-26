package com.simtechdata.macicns;

import java.awt.*;

public class Launcher {
	public static void main(String[] args) {
		if (System.getenv("SLIC3R_FILAMENT_TYPE") != null) {System.setProperty("apple.awt.UIElement", "true");}
		else {System.setProperty("apple.awt.UIElement", "false");}
		Toolkit.getDefaultToolkit();
		Main.main(args);
	}
}
