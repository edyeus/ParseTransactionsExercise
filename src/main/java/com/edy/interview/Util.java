package com.edy.interview;

import org.apache.commons.cli.Options;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /**
     * Remove unknown options from args to prevent exception
     * @param options, options from commandline
     * @param args, commandline arguments
     * @return args without unknown exceptions
     */
    public static String[] ignoreUnknownOptions(Options options, String[] args){
        List<String> list = new ArrayList<>();

        for (String str: args){
            String arg = str.contains("=")? str.substring(0,str.indexOf('=')) :str;
            if (options.hasOption(arg)) list.add(str);
        }

        String[] newArgs = new String[list.size()];
        return list.toArray(newArgs);
    }

}
