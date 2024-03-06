package com.simtechdata.build;

import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Selections {

    public Selections(Path originalFile) {
        this.iconFolder = originalFile.getParent().resolve("icon.iconset");
        System.out.println("iconFolder = " + iconFolder);
        this.originalFile = originalFile;
    }

    private boolean i512 = true;
    private boolean i256 = true;
    private boolean i128 = true;
    private boolean i64  = true;
    private boolean i32  = true;
    private boolean i16  = true;

    private final Path iconFolder;
    private final Path originalFile;
    private final Map<Integer, Job> jobMap = new HashMap<>();

    private final LinkedList<Path> pathList = new LinkedList<>();

    public Path getIconFolder() {
        return iconFolder;
    }

    public Path getIcnsFilePath() {
        String iconFilename = FilenameUtils.getBaseName(originalFile.toFile().getName()) + ".icns";
        return originalFile.getParent().resolve(iconFilename);
    }

    public boolean makeParentFolder() throws IOException {
        File random = iconFolder.resolve("someFile.txt").toFile();
        return FileUtils.createParentDirectories(random).exists();
    }

    public Map<Integer, Job> getJobMap() {
        if(i512) {
            pathList.addLast(iconFolder.resolve("icon_512x512@2x.png"));
            pathList.addLast(iconFolder.resolve("icon_512x512.png"));
        }

        if(i256) {
            pathList.addLast(iconFolder.resolve("icon_256x256@2x.png"));
            pathList.addLast(iconFolder.resolve("icon_256x256.png"));
        }

        if(i128) {
            pathList.addLast(iconFolder.resolve("icon_128x128@2x.png"));
            pathList.addLast(iconFolder.resolve("icon_128x128.png"));
        }

        if(i64) {
            pathList.addLast(iconFolder.resolve("icon_64x64@2x.png"));
            pathList.addLast(iconFolder.resolve("icon_64x64.png"));
        }

        if(i32) {
            pathList.addLast(iconFolder.resolve("icon_32x32@2x.png"));
            pathList.addLast(iconFolder.resolve("icon_32x32.png"));
        }

        if(i16) {
            pathList.addLast(iconFolder.resolve("icon_16x16@2x.png"));
            pathList.addLast(iconFolder.resolve("icon_16x16.png"));
        }
        Image imgOriginal = new Image(originalFile.toUri().toString());
        int count = 1;
        for(Path path : pathList) {
            String name = path.toFile().getName();
            if(name.contains("512x512@2x")) {
                jobMap.put(count, new Job(imgOriginal, path, 1024));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("512")) {
                jobMap.put(count, new Job(imgOriginal, path, 512));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("256x256@2")) {
                jobMap.put(count, new Job(imgOriginal, path, 512));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("256")) {
                jobMap.put(count, new Job(imgOriginal, path, 256));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("128x128@2")) {
                jobMap.put(count, new Job(imgOriginal, path, 256));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("128")) {
                jobMap.put(count, new Job(imgOriginal, path, 128));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("64x64@2")) {
                jobMap.put(count, new Job(imgOriginal, path, 128));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("64")) {
                jobMap.put(count, new Job(imgOriginal, path, 64));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("32x32@2")) {
                jobMap.put(count, new Job(imgOriginal, path, 64));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("32")) {
                jobMap.put(count, new Job(imgOriginal, path, 32));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("16x16@2")) {
                jobMap.put(count, new Job(imgOriginal, path, 32));
                System.out.println("Adding Job: " + name);
            }
            else if(name.contains("16")) {
                jobMap.put(count, new Job(imgOriginal, path, 16));
                System.out.println("Adding Job: " + name);
            }
            count++;
        }

        return jobMap;
    }

    public void setI512(boolean i512) {
        this.i512 = i512;
    }

    public void setI256(boolean i256) {
        this.i256 = i256;
    }

    public void setI128(boolean i128) {
        this.i128 = i128;
    }

    public void setI64(boolean i64) {
        this.i64 = i64;
    }

    public void setI32(boolean i32) {
        this.i32 = i32;
    }

    public void setI16(boolean i16) {
        this.i16 = i16;
    }
}
