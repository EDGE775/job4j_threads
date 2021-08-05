package ru.job4j.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        synchronized (file) {
            try (BufferedOutputStream bs = new BufferedOutputStream(
                    new FileOutputStream(file))) {
                for (int i = 0; i < content.length(); i += 1) {
                    bs.write(content.charAt(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
