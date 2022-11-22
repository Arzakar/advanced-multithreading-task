package com.rntgroup.advanced.multithreading.task.folder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class FolderProperties {

    private static final Logger log = Logger.getLogger(FolderProperties.class.getName());

    public static void main(String[] args) {
        FolderProperties folderProperties = new FolderProperties();
        Path path;

        do {
            System.out.print("Введите путь к директории: ");
            Scanner scanner = new Scanner(System.in);
            path = Paths.get(scanner.nextLine());
        } while (!folderProperties.isDirectory(path));

        final Path finalPath = path;
        ForkJoinPool fjp = new ForkJoinPool();

        FutureTask<FolderStatistic> folderStatisticFutureTask = new FutureTask<>(() -> {
            FolderPropertiesTask folderPropertiesTask = new FolderPropertiesTask(finalPath);
            FolderStatistic result = fjp.invoke(folderPropertiesTask);
            return result.setFolderCount(result.getFolderCount() - 1);
        });

        Thread fjpThread = new Thread(folderStatisticFutureTask);

        AtomicBoolean fjpThreadWasCancelled = new AtomicBoolean(false);
        Thread cancelThread = new Thread(() -> {
            while (true) {
                try {
                    int code = System.in.read();
                    char symbol = (char) code;

                    if (symbol == 'C' || symbol == 'c') {
                        fjp.shutdownNow();
                        fjpThreadWasCancelled.set(true);
                        System.out.println("Сканирование прервано");
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        fjpThread.start();
        cancelThread.start();

        try {
            FolderStatistic result = folderStatisticFutureTask.get();

            System.out.println("Свойства папки:");
            System.out.println("Количество внутренних папок: " + result.getFolderCount());
            System.out.println("Количество внутренних файлов: " + result.getFileCount());
            System.out.println("Размер папки: " + result.getSize() + " bytes");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            if (!fjpThreadWasCancelled.get()) {
                e.printStackTrace();
            }
        }
    }

    private boolean isDirectory(Path path) {
        if (Files.isDirectory(path)) {
            return true;
        } else {
            log.warning("Путь " + path + " указывает не на каталог!");
            return false;
        }
    }
}
