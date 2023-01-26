package com.simtechdata.macicns;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Run implements Runnable {

	private Path iconFolder;
	private Path outPath;

	private final String command = "iconutil -c icns %s -o %s";

	public static String findBash() {
		return "/usr/local/bin/bash";
	}

	public Run(Path iconFolder, Path outPath) {
		this.iconFolder = iconFolder;
		this.outPath    = outPath;
	}

	@Override public void run() {
		String[] command = new String[3];
		command[0] = findBash();
		command[1] = "-c";
		command[2] = String.format(this.command, iconFolder.toString(), outPath.toString());
		ProcessBuilder pb      = new ProcessBuilder(command);
		try {
			Process        process = pb.start();
			Scanner        scanner = new Scanner(process.getErrorStream());
			while (scanner.hasNext()) {
				doNothing();
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void doNothing() {

	}
}
