package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static final String[] STATUS = {"\\", "|", "/"};
    public static int index = 0;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + STATUS[index++]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (index == STATUS.length) {
                index = 0;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
