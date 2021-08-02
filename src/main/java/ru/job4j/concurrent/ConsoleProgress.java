package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    private char[] loadChars = new char[] {'\\', '|', '/'};

    private int charNumber = 0;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
                System.out.print("\r load: " + getLoadBar());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getLoadBar() {
        if (charNumber > 2) {
            charNumber = 0;
        }
        return String.format("Loading ... %s", loadChars[charNumber++]);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(15000);
        progress.interrupt();
    }
}
