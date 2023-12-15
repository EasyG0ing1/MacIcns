package com.simtechdata;

import com.simtechdata.process.ProcBuilder;
import com.simtechdata.process.ProcResult;

import java.nio.file.Path;


public class Run {

    private final Path iconFolder;
    private final Path outPath;

    public Run(Path iconFolder, Path outPath) {
        this.iconFolder = iconFolder;
        this.outPath = outPath;
    }

    public int run() {
        String[] args = new String[]{"-c", "icns", iconFolder.toString(), "-o", outPath.toString()};
        ProcResult result = new ProcBuilder("iconutil")
                .withArgs(args)
                .withNoTimeout()
                .ignoreExitStatus()
                .run();
        return result.getExitValue();
    }
}
