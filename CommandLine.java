package com.example.loganalyzer;

import java.util.*;

public class CommandLine {
    private Map<String, String> options = new HashMap<>();
    private Set<String> flags = new HashSet<>();

    public static CommandLine parse(String[] args) {
        CommandLine cli = new CommandLine();
        for (int i = 0; i < args.length; i++) {
            String a = args[i];
            if (a.startsWith("--")) {
                if (i + 1 < args.length && !args[i+1].startsWith("--")) {
                    cli.options.put(a, args[++i]);
                } else {
                    cli.flags.add(a);
                }
            }
        }
        return cli;
    }

    public String getOption(String k) { return options.get(k); }
    public boolean hasFlag(String f) { return flags.contains(f); }
}
