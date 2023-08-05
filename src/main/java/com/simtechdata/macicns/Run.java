package com.simtechdata.macicns;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Run implements Runnable {

    private final Path iconFolder;
    private final Path outPath;

    private final String command = "iconutil -c icns %s -o %s";

    public static String findBash() {
        File shell = new File("");
        String paths = System.getenv("PATH");
        List<String> pathList = new ArrayList<>(Arrays.stream(paths.split(":")).toList());
        for (String path : pathList) {
            shell = new File(path, "bash");
            if (shell.exists()) {
                break;
            }
            shell = new File(path, "zsh");
            if (shell.exists()) {
                break;
            }
        }
        if (!shell.exists()) {
            shell = new File("/usr/local/bin/bash");
        }
        if (shell.exists()) {
            return shell.getAbsolutePath();
        } else {
            System.out.println("Could not find bash or zsh shell. Please make sure the path is in your PATH environment variable.");
            System.exit(0);
            return "";
        }
    }

    public Run(Path iconFolder, Path outPath) {
        this.iconFolder = iconFolder;
        this.outPath = outPath;
    }

    @Override
    public void run() {
        String[] command = new String[3];
        command[0] = findBash();
        command[1] = "-c";
        command[2] = String.format(this.command, iconFolder.toString(), outPath.toString());
        ProcessBuilder pb = new ProcessBuilder(command);
        try {
            Process process = pb.start();
            Scanner scanner = new Scanner(process.getErrorStream());
            while (scanner.hasNext()) {
                doNothing();
            }
        } catch (IOException e) {
            System.out.println("There was an error. If the following information does not help you figure out the problem, copy and paste the text below the line and create an issue on https://github.com/EasyG0ing1/MacIcns\n---------------------------------------------------------------\nRun.run()\n\n");
            throw new RuntimeException(e);
        }
    }

    private void doNothing() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
