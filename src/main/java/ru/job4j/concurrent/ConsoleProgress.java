package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static final String FIRST = "\\";
    public static final String SECOND = "|";
    public static final String THIRD = "/";
    private String current = FIRST;

    private boolean flag = true;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + next());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public String next() {
        if (current.equals(FIRST) || current.equals(THIRD)) {
            current = SECOND;
        } else if (flag) {
            current = THIRD;
            flag = false;
        } else {
            current = FIRST;
            flag = true;
        }
        return current;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
