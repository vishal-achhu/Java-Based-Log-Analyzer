package com.example.loganalyzer;

import java.io.*;
import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java -jar loganalyzer.jar analyze --file <path> --rules <path> [--tail] [--webhook <url>]");
            return;
        }
        CommandLine cli = CommandLine.parse(args);
        Analyzer analyzer = new Analyzer(cli);
        analyzer.run();
    }
}
