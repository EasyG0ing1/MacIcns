package com.simtechdata;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class ProcessFile {

    public ProcessFile(Path selectedImageFile, boolean inWindow) {
        this.selectedImageFile = selectedImageFile;
        this.inWindow = inWindow;
    }

    private final Path selectedImageFile;
    private final boolean inWindow;

    public Response run() {
        return processFile();
    }

    private static String icnsFilePath;

    public static String getIcnsFilePath() {
        return icnsFilePath;
    }

    private Response processFile() {
        Response response = new Response();
        if (selectedImageFile != null) {
            String folder = selectedImageFile.toFile().getParentFile().getAbsolutePath();
            Path iconFolder = Paths.get(folder, "icon.iconset");
            String iconFilename = FilenameUtils.getBaseName(selectedImageFile.toString()) + ".icns";
            Path icnsFile = Paths.get(folder, iconFilename);
            icnsFilePath = icnsFile.toAbsolutePath().toString();
            Path Image1 = Paths.get(iconFolder.toString(),  "icon_512x512@2x.png");
            Path Image2 = Paths.get(iconFolder.toString(),  "icon_512x512.png");
            Path Image3 = Paths.get(iconFolder.toString(),  "icon_256x256@2x.png");
            Path Image4 = Paths.get(iconFolder.toString(),  "icon_256x256.png");
            Path Image5 = Paths.get(iconFolder.toString(),  "icon_128x128@2x.png");
            Path Image6 = Paths.get(iconFolder.toString(),  "icon_128x128.png");
            Path Image7 = Paths.get(iconFolder.toString(),  "icon_32x32@2x.png");
            Path Image8 = Paths.get(iconFolder.toString(),  "icon_32x32.png");
            Path Image9 = Paths.get(iconFolder.toString(),  "icon_16x16@2x.png");
            Path Image10 = Paths.get(iconFolder.toString(), "icon_16x16.png");

            Map<Integer, Job> jobMap = new HashMap<>();

            try {
                File file = selectedImageFile.toFile();
                BufferedImage original = ImageIO.read(file);
                double width = original.getWidth();
                double height = original.getHeight();
                if (!ImageChecker.isValid(file)) {
                    if(!inWindow) {
                        System.out.println("Specified file is not a valid image type.\nMust be: PNG, JPEG, GIF, TIFF, BMP or SVG");
                        System.exit(0);
                    }
                    response.set(false, "Specified file is not a valid image type.\nMust be: PNG, JPEG, GIF, TIFF, BMP or SVG");
                    return response;
                }
                if (width != 1024 || height != 1024) {

                    if(!inWindow) {
                        System.out.println("Specified image is not 1024 x 1024 in size");
                        System.exit(0);
                    }
                    response.set(false, "Specified image is not 1024 x 1024 in size");
                    return response;
                }
                if (!iconFolder.toFile().exists()) {
                    if(FileUtils.createParentDirectories(Image1.toFile()).exists()) {
                        jobMap.put(1, new Job(original, Image1, 1024));
                        jobMap.put(2, new Job(original, Image2, 512));
                        jobMap.put(3, new Job(original, Image3, 512));
                        jobMap.put(4, new Job(original, Image4, 256));
                        jobMap.put(5, new Job(original, Image5, 256));
                        jobMap.put(6, new Job(original, Image6, 128));
                        jobMap.put(7, new Job(original, Image7, 64));
                        jobMap.put(8, new Job(original, Image8, 32));
                        jobMap.put(9, new Job(original, Image9, 32));
                        jobMap.put(10, new Job(original, Image10, 16));

                        for (int index : jobMap.keySet()) {
                            jobMap.get(index).saveFile();
                        }

                        Thread thread = new Thread(new Run(iconFolder, icnsFile));
                        thread.start();
                        while (thread.getState().equals(Thread.State.RUNNABLE)) {
                            doNothing();
                        }
                        FileUtils.deleteDirectory(iconFolder.toFile());
                        if (!inWindow) {
                            System.out.println("Icon file created: " + icnsFile);
                            System.exit(0);
                        }
                        response.set(true, "");
                    }
                    else {
                        response.set(false, "Could not create the icon workspace folder, permission issue?");
                        if(!inWindow) {
                            System.out.println("Could not create the icon workspace folder, permission issue?");
                            System.exit(0);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("There was an error. If the following information does not help you figure out the problem, copy and paste the text below the line and create an issue on https://github.com/EasyG0ing1/MacIcns\n---------------------------------------------------------------\nProcessFile.processFile()\n\n");
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    private static class Job {
        public Job(BufferedImage original, Path path, int size) {
            this.original = original;
            this.path = path;
            this.size = size;
        }

        private final BufferedImage original;
        private final Path path;
        private final int size;

        public void saveFile() {
            try {
                BufferedImage finalImage = getImage();
                ImageIO.write(finalImage, "PNG", path.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private BufferedImage getImage() {
            try {
                return resizeImage(original, size, size);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
            return Scalr.resize(originalImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight);
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
