package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        int charNumber = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (charNumber > 2) {
                    charNumber = 0;
                }
                Thread.sleep(500);
                String loadBar = charNumber == 0 ? "\\"
                        : charNumber == 1 ? "|" : "/";
                System.out.printf("\r Loading ... %s", loadBar);
                charNumber++;
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
