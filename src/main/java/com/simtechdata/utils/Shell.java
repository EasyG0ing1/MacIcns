package com.simtechdata.utils;

import com.simtechdata.process.ProcBuilder;
import com.simtechdata.process.ProcResult;

public class Shell {

    public static int run(String command, String[] args) {
        ProcResult r = new ProcBuilder(command)
                .withArgs(args)
                .withNoTimeout()
                .ignoreExitStatus()
                .run();
        return r.getExitValue();
    }
}
