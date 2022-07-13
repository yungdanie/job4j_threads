package ru.job4j.concurrent;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ContentSave {
    private final File file;

    public ContentSave(File file) {
        this.file = file;
    }

    public void saveContent(String content) {
        synchronized (this) {
            try (BufferedOutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
                for (int i = 0; i < content.length(); i += 1) {
                    o.write(content.charAt(i));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
