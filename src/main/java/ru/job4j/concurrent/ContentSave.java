package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@ThreadSafe
public final class ContentSave {

    @GuardedBy("file")
    private final File file;

    public ContentSave(File file) {
        this.file = new File(file.toString());
    }

    public void saveContent(String content) {
        synchronized (file) {
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
