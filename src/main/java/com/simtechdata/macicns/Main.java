package com.simtechdata.macicns;

import com.simtechdata.sceneonefx.SceneOne;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

	private static       Path       mainPath;
	private              Path       resourcesFolder;
	private              Path       monacoFont = null;
	private              Path       logoIcon   = null;
	private final        ScanResult scanResult = new ClassGraph().enableAllInfo().scan();
	private static final String     fontMonaco = "fonts/Monaco.ttf";
	private static final String     iconLogo   = "icons/Logo.png";

	public static void main(String[] args) {
		if (args.length > 0) {
			if (!args[0].equalsIgnoreCase("overwrite")) {mainPath = Paths.get(args[0]);}
		}
		if (mainPath != null) {
			if (mainPath.toFile().exists()) {new ProcessFile(mainPath, false).run();}
			else {
				System.out.println("No file exists at this path: " + args[0]);
				System.exit(0);
			}
		}
		else {
			launch(args);
		}
	}

	private void copyResources() {
		resourcesFolder = Paths.get(System.getProperty("user.dir"), "Contents", "Resources");
		monacoFont      = Paths.get(resourcesFolder.toString(), "Monaco.ttf");
		logoIcon        = Paths.get(resourcesFolder.toString(), "Logo.png");
		try {
			ResourceList resources = scanResult.getAllResources();
			for (URL u : resources.getURLs()) {
				if (u.toString().contains(fontMonaco)) {
					if (!monacoFont.toFile().exists()) {
						FileUtils.copyURLToFile(u, monacoFont.toFile());
					}
				}
				if (u.toString().contains(iconLogo)) {
					if (!logoIcon.toFile().exists()) {
						FileUtils.copyURLToFile(u, logoIcon.toFile());
					}
				}
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override public void start(Stage primaryStage) {
		SceneOne.setDefaultStage(primaryStage);
		copyResources();
		new Window();
	}
}
