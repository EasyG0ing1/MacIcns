package com.simtechdata.utils;

import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;

public class Shell {

    public static int run(String command, String[] args) {
        ProcResult r = new ProcBuilder(command)
                .withArgs(args)
                .withNoTimeout()
                .ignoreExitStatus()
                .run();
        System.out.println(r.getCommandLine());
        return r.getExitValue();
    }


}
