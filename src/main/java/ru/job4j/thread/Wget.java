package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private long delay = 0L;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(
                new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            //Переводим миллисекунды в наносекунды:
            long nanoSpeed = speed * 1000000L;
            long startTime = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long estimatedTime = System.nanoTime() - startTime;
                System.out.printf(
                        "Время загрузки: %d нс. Загружено байт: %d%n", estimatedTime, bytesRead);
                //Определяем задержку в наносекундах:
                delay = estimatedTime < nanoSpeed ? nanoSpeed - estimatedTime : 0;
                System.out.printf("Установлено время задержки: %d нс.%n%n", delay);
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                Thread.sleep(delay / 1000000);
                startTime = System.nanoTime();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        //Скорость загрузки 1 байта в миллисекундах:
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}