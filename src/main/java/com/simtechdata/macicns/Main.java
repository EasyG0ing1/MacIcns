package com.simtechdata.macicns;

import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

	private static Path mainPath;

	public static void main(String[] args) {
		for (String arg : args) {
			mainPath = Paths.get(arg);
		}
		if (mainPath == null) {
			launch(args);
		}
		else if (!mainPath.toFile().exists()) {
			System.out.println("No file exists at this path");
			System.exit(0);
		}
		else
			new ProcessFile(mainPath, false).run();
	}

	@Override public void start(Stage primaryStage) throws Exception {
		new Window();
	}
}
