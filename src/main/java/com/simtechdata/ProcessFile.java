package com.simtechdata;

import com.simtechdata.build.Job;
import com.simtechdata.build.Selections;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class ProcessFile {

    public ProcessFile(Path selectedImageFile, Selections selections) {
        this.selectedImageFile = selectedImageFile;
        this.selections = selections;
    }

    private final Path selectedImageFile;
    private final Selections selections;

    public Response run() {
        if(selections == null)
            return processFileShell();
        return processFileGUI();
    }

    private static String icnsFilePath;

    public static String getIcnsFilePath() {
        return icnsFilePath;
    }

    private Response processFileGUI() {
        Response response = new Response();
        if (selectedImageFile != null) {
            Map<Integer, Job> jobMap = selections.getJobMap();
            if(jobMap == null) {
                System.out.println("NULL");
                System.exit(0);
            }
            try {
                File file = selectedImageFile.toFile();
                Image original = new Image(file.toURI().toString());
                double width = original.getWidth();
                double height = original.getHeight();
                String msg;
                if (!ImageChecker.isValid(file)) {
                    msg = "Specified file is not a valid image type.\nMust be: PNG, JPEG, GIF, TIFF, BMP or SVG";
                    response.set(false, msg);
                    return response;
                }
                if (width != 1024 || height != 1024) {
                    msg = "Specified image is not 1024 x 1024 in size";
                    response.set(false, msg);
                    return response;
                }
                if(selections.makeParentFolder()) {
                    for (int index : jobMap.keySet()) {
                        jobMap.get(index).saveFile();
                    }
                    int exitCode = new Run(selections.getIconFolder(), selections.getIcnsFilePath()).run();
                    if (exitCode != 0) {
                        response.set(false, "Error occurred while running the icon creation process");
                    }
                    else {
                        FileUtils.deleteDirectory(selections.getIconFolder().toFile());
                        response.set(true, "");
                    }
                }
                else {
                    msg = "Could not create the icon workspace folder, permission issue?";
                    response.set(false, msg);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    private Response processFileShell() {
        Response response = new Response();
        if (selectedImageFile != null) {
            String folder = selectedImageFile.toFile().getParentFile().getAbsolutePath();
            Path iconFolder = Paths.get(folder, "icon.iconset");
            String iconFilename = FilenameUtils.getBaseName(selectedImageFile.toString()) + ".icns";
            Path icnsFile = Paths.get(folder, iconFilename);
            icnsFilePath = icnsFile.toAbsolutePath().toString();
            Path Image01 = Paths.get(iconFolder.toString(), "icon_512x512@2x.png"); //01
            Path Image02 = Paths.get(iconFolder.toString(), "icon_512x512.png"); //02
            Path Image03 = Paths.get(iconFolder.toString(), "icon_256x256@2x.png"); //03
            Path Image04 = Paths.get(iconFolder.toString(), "icon_256x256.png"); //04
            Path Image05 = Paths.get(iconFolder.toString(), "icon_128x128@2x.png"); //05
            Path Image06 = Paths.get(iconFolder.toString(), "icon_128x128.png"); //06
            Path Image07 = Paths.get(iconFolder.toString(), "icon_64x64@2x.png"); //07
            Path Image08 = Paths.get(iconFolder.toString(), "icon_64x64.png"); //08
            Path Image09 = Paths.get(iconFolder.toString(), "icon_32x32@2x.png"); //09
            Path Image10 = Paths.get(iconFolder.toString(), "icon_32x32.png"); //10
            Path Image11 = Paths.get(iconFolder.toString(), "icon_16x16@2x.png"); //11
            Path Image12 = Paths.get(iconFolder.toString(), "icon_16x16.png"); //12

            Map<Integer, Job> jobMap = new HashMap<>();

            try {
                File file = selectedImageFile.toFile();
                Image original = new Image(file.toURI().toString());
                double width = original.getWidth();
                double height = original.getHeight();
                String msg;
                if (!ImageChecker.isValid(file)) {
                    msg = "Specified file is not a valid image type.\nMust be: PNG, JPEG, GIF, TIFF, BMP or SVG";
                    System.out.println(msg);
                    System.exit(0);
                }
                if (width != 1024 || height != 1024) {
                    msg = "Specified image is not 1024 x 1024 in size";
                    System.out.println(msg);
                    System.exit(0);
                }
                if (FileUtils.createParentDirectories(Image01.toFile()).exists()) {
                    jobMap.put(1, new Job(original, Image01, 1024));
                    jobMap.put(2, new Job(original, Image02, 512));
                    jobMap.put(3, new Job(original, Image03, 512));
                    jobMap.put(4, new Job(original, Image04, 256));
                    jobMap.put(5, new Job(original, Image05, 256));
                    jobMap.put(6, new Job(original, Image06, 128));
                    jobMap.put(7, new Job(original, Image07, 128));
                    jobMap.put(8, new Job(original, Image08, 64));
                    jobMap.put(9, new Job(original, Image09, 64));
                    jobMap.put(10, new Job(original, Image10, 32));
                    jobMap.put(11, new Job(original, Image11, 32));
                    jobMap.put(12, new Job(original, Image12, 16));

                    for (int index : jobMap.keySet()) {
                        jobMap.get(index).saveFile();
                    }

                    int exitCode = new Run(iconFolder, icnsFile).run();
                    if (exitCode != 0) {
                        response.set(false, "Error occurred while running the icon creation process");
                    }
                    else {
                        FileUtils.forceDeleteOnExit(iconFolder.toFile());
                        System.out.println("Icon file created: " + icnsFile);
                        System.exit(0);
                        response.set(true, "");
                    }
                }
                else {
                    msg = "Could not create the icon workspace folder, permission issue?";
                    System.out.println(msg);
                    System.exit(0);
                }
            }
            catch (IOException e) {
                System.err.println("There was an error. If the following information does not help you figure out the problem, copy and paste the text below the line and create an issue on https://github.com/EasyG0ing1/MacIcns\n---------------------------------------------------------------\nProcessFile.processFileShell()\n\n");
                throw new RuntimeException(e);
            }
        }
        return response;
    }

}
