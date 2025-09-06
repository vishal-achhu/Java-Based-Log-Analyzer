package com.example.loganalyzer;

import java.io.*;
import java.util.*;

public class Analyzer {
    private final CommandLine cli;
    private final RuleEngine engine;

    public Analyzer(CommandLine cli) throws Exception {
        this.cli = cli;
        String rulesFile = cli.getOption("--rules");
        this.engine = new RuleEngine(rulesFile);
    }

    public void run() throws Exception {
        String file = cli.getOption("--file");
        boolean tail = cli.hasFlag("--tail");
        String webhook = cli.getOption("--webhook");

        if (tail) {
            LogTailer tailer = new LogTailer(new File(file), line -> handle(line, webhook));
            tailer.start();
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    handle(line, webhook);
                }
            }
        }
    }

    private void handle(String line, String webhook) {
        List<String> detections = engine.apply(line);
        for (String d : detections) {
            System.out.println("ALERT: " + d);
            if (webhook != null) {
                WebhookNotifier.send(webhook, d);
            }
        }
    }
}
