package com.simtechdata;

import javafx.application.Application;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;


public class MainGUI extends Application {

    private static Path mainPath;

    public static void main(String[] args) {
        if (args.length > 0) {
            if (!args[0].equalsIgnoreCase("overwrite")) {
                mainPath = Paths.get(args[0]);
            }
        }
        if (mainPath != null) {
            if (mainPath.toFile().exists()) {
                new ProcessFile(mainPath,null).run();
            }
            else {
                System.out.println("No file exists at this path: " + args[0]);
                System.exit(0);
            }
        }
        else {
            launch(args);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        new Window(primaryStage);
    }
}
