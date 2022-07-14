package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;

import java.io.*;
import java.util.function.Predicate;

public class ContentLoad {

    @GuardedBy("this")
    private final File file;

    public ContentLoad(File file) {
        this.file = file;
    }

    private String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        synchronized (this) {
            try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
                int data;
                char dataF;
                while ((data = i.read()) != -1) {
                    dataF = (char) data;
                    if (filter.test(dataF)) {
                        output.append(dataF);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output.toString();
        }
    }

    public String getContent() {
        return getContent(x -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(x -> x < 0x80);
    }
}
