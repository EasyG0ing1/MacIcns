package com.simtechdata;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


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
            Path Image01 = Paths.get(iconFolder.toString(), "icon_512x512@2x.png");
            Path Image02 = Paths.get(iconFolder.toString(), "icon_512x512.png");
            Path Image03 = Paths.get(iconFolder.toString(), "icon_256x256@2x.png");
            Path Image04 = Paths.get(iconFolder.toString(), "icon_256x256.png");
            Path Image05 = Paths.get(iconFolder.toString(), "icon_128x128@2x.png");
            Path Image06 = Paths.get(iconFolder.toString(), "icon_128x128.png");
            Path Image07 = Paths.get(iconFolder.toString(), "icon_32x32@2x.png");
            Path Image08 = Paths.get(iconFolder.toString(), "icon_32x32.png");
            Path Image09 = Paths.get(iconFolder.toString(), "icon_16x16@2x.png");
            Path Image10 = Paths.get(iconFolder.toString(), "icon_16x16.png");

            Map<Integer, Job> jobMap = new HashMap<>();

            try {
                File file = selectedImageFile.toFile();
                Image original = new Image(file.toURI().toString());
                double width = original.getWidth();
                double height = original.getHeight();
                String msg;
                if (!ImageChecker.isValid(file)) {
                    msg = "Specified file is not a valid image type.\nMust be: PNG, JPEG, GIF, TIFF, BMP or SVG";
                    if (!inWindow) {
                        System.out.println(msg);
                        System.exit(0);
                    }
                    response.set(false, msg);
                    return response;
                }
                if (width != 1024 || height != 1024) {
                    msg = "Specified image is not 1024 x 1024 in size";
                    if (!inWindow) {
                        System.out.println(msg);
                        System.exit(0);
                    }
                    response.set(false, msg);
                    return response;
                }
                if (FileUtils.createParentDirectories(Image01.toFile()).exists()) {
                    jobMap.put(1, new Job(original, Image01, 1024));
                    jobMap.put(2, new Job(original, Image02, 512));
                    jobMap.put(3, new Job(original, Image03, 512));
                    jobMap.put(4, new Job(original, Image04, 256));
                    jobMap.put(5, new Job(original, Image05, 256));
                    jobMap.put(6, new Job(original, Image06, 128));
                    jobMap.put(7, new Job(original, Image07, 64));
                    jobMap.put(8, new Job(original, Image08, 32));
                    jobMap.put(9, new Job(original, Image09, 32));
                    jobMap.put(10, new Job(original, Image10, 16));

                    for (int index : jobMap.keySet()) {
                        jobMap.get(index).saveFile();
                    }

                    int exitCode = new Run(iconFolder, icnsFile).run();
                    if (exitCode != 0) {
                        response.set(false, "Error occurred while running the icon creation process");
                    }
                    else {
                        FileUtils.forceDeleteOnExit(iconFolder.toFile());
                        if (!inWindow) {
                            System.out.println("Icon file created: " + icnsFile);
                            System.exit(0);
                        }
                        response.set(true, "");
                    }
                }
                else {
                    msg = "Could not create the icon workspace folder, permission issue?";
                    response.set(false, msg);
                    if (!inWindow) {
                        System.out.println(msg);
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                System.err.println("There was an error. If the following information does not help you figure out the problem, copy and paste the text below the line and create an issue on https://github.com/EasyG0ing1/MacIcns\n---------------------------------------------------------------\nProcessFile.processFile()\n\n");
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    private static class Job {
        public Job(Image original, Path path, int size) {
            this.original = original;
            this.path = path;
            this.size = size;
        }

        private final Image original;
        private final Path path;
        private final int size;

        public void saveFile() {
            try {
                WritableImage finalImage = getImage();
                ImageIO.write(SwingFXUtils.fromFXImage(finalImage, null), "PNG", path.toFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private WritableImage getImage() {
            try {
                return resizeImage(original, size, size);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private WritableImage resizeImage(Image originalImage, int targetWidth, int targetHeight) {
            int width = (int) originalImage.getWidth();
            int height = (int) originalImage.getHeight();

            WritableImage resizedImage = new WritableImage(targetWidth, targetHeight);
            for (int x = 0; x < targetWidth; x++) {
                for (int y = 0; y < targetHeight; y++) {
                    double sourceX = x * width / (double) targetWidth;
                    double sourceY = y * height / (double) targetHeight;
                    Color color = originalImage.getPixelReader().getColor((int) sourceX, (int) sourceY);
                    resizedImage.getPixelWriter().setColor(x, y, color);
                }
            }
            return resizedImage;
        }
    }
}
