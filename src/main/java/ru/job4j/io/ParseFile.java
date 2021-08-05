package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) {
        synchronized (file) {
            StringBuilder output = new StringBuilder();
            try (BufferedInputStream bi = new BufferedInputStream(
                    new FileInputStream(file))) {
                int data;
                while ((data = bi.read()) > 0) {
                    if (filter.test((char) data)) {
                        output.append((char) data);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }
}

