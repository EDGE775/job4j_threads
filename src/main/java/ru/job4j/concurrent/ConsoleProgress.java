package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] loadChars = {'\\', '|', '/'};
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                index = index > loadChars.length - 1 ? 0 : index;
                Thread.sleep(500);
                System.out.printf("\r Loading ... %s", loadChars[index]);
                index++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(15000);
        progress.interrupt();
    }
}
