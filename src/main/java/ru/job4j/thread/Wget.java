package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String destFileName;
    private int delay = 0;

    public Wget(String url, int speed, String destFileName) {
        this.url = url;
        this.speed = speed;
        this.destFileName = destFileName;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(
                new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destFileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int nanoSpeed = speed;
            int startTime = (int) (System.nanoTime() / 1000000);
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                int estimatedTime = (int) (System.nanoTime() / 1000000) - startTime;
                System.out.printf(
                        "Время загрузки: %d мс. Загружено байт: %d%n", estimatedTime, bytesRead);
                delay = estimatedTime < nanoSpeed ? nanoSpeed - estimatedTime : 0;
                System.out.printf("Установлено время задержки: %d мс.%n%n", delay);
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(delay);
                startTime = (int) (System.nanoTime() / 1000000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Invalid input args. Usage 3 args: url, speed, destfilename.");
        }
        String url = args[0];
        int speed;
        try {
            speed = Integer.parseInt(args[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid speed argument. Speed must be a number.");
        }
        String destFileName = args[2];
        Thread wget = new Thread(new Wget(url, speed, destFileName));
        wget.start();
        wget.join();
    }
}