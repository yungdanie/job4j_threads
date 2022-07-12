package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    public static final long ONE_SEC = 1000;
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("loaded.dat")) {
            byte[] dataBuffer = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead;
            long bytesWrited = 0;
            long endTime;
            long startTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                bytesWrited = bytesWrited + bytesRead;
                if (bytesWrited >= speed) {
                    endTime = System.currentTimeMillis() - startTime;
                    if (endTime < ONE_SEC) {
                        bytesWrited = 0;
                        Thread.sleep(ONE_SEC - endTime);
                    }
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Illegal number of arguments."
                    .concat(System.lineSeparator())
                    .concat("Put URL of the file which you want to download as first argument")
                    .concat(System.lineSeparator())
                    .concat("Put download speed (byte/sec) as second argument"));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
