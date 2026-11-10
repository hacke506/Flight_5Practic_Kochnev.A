package service;

import java.io.FileWriter;
import java.io.PipedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Logger {
    private static final String[] logs = new String[500];
    private  static  int logSize = 0;
    private static final String FILE_PATCH = "log.txt";

    public static void  log(String operation) {
        String logEntry = String.format("[%s] %s", LocalDateTime.now(), operation);
        if(logSize < logs.length) {
            logs[logSize++] = logEntry;
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATCH, true))) {
            writer.println((logEntry));
        } catch (IOException e) {
            System.err.println("Log writing error:" + e.getMessage());
        }
    }
}
