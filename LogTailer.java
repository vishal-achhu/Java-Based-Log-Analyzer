package com.example.loganalyzer;

import java.io.*;
import java.util.function.Consumer;

public class LogTailer {
    private final File file;
    private final Consumer<String> handler;

    public LogTailer(File file, Consumer<String> handler) {
        this.file = file;
        this.handler = handler;
    }

    public void start() throws Exception {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long pointer = raf.length();
            while (true) {
                long len = raf.length();
                if (len < pointer) {
                    pointer = len;
                }
                if (len > pointer) {
                    raf.seek(pointer);
                    String line;
                    while ((line = raf.readLine()) != null) {
                        handler.accept(line);
                    }
                    pointer = raf.getFilePointer();
                }
                Thread.sleep(1000);
            }
        }
    }
}
