package com.rntgroup.advanced.multithreading.task.third;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;
import java.util.stream.Stream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FolderPropertiesTask extends RecursiveTask<FolderStatistic> {

    static transient Logger log = Logger.getLogger(FolderPropertiesTask.class.getName());

    transient Path path;

    public FolderPropertiesTask(Path path) {
        this.path = path;

    }

    @Override
    protected FolderStatistic compute() {
        FolderStatistic result = FolderStatistic.empty();

        /**
         * Имитация долгой работы ПК
         */
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {

        }

        if (!Files.isDirectory(path)) {
            try {
                return result.add(new FolderStatistic()
                        .setFileCount(1)
                        .setSize(Files.size(path)));
            } catch (IOException e) {
                log.warning("Error while get file size by " + path);
                log.throwing(FolderStatistic.class.getSimpleName(), "compute()", e);
            }
        }

        result.add(new FolderStatistic().setFolderCount(1));

        try(Stream<Path> internalPathsStream = Files.list(path)) {
            List<Path> internalPaths = internalPathsStream.toList();

            if (internalPaths.isEmpty()) {
                return result;
            }

            List<ForkJoinTask<FolderStatistic>> taskList = new ArrayList<>();
            internalPaths.forEach(internalPath -> {
                ForkJoinTask<FolderStatistic> task = new FolderPropertiesTask(internalPath).fork();
                taskList.add(task);
            });

            taskList.forEach(task -> result.add(task.join()));

        } catch (AccessDeniedException e) {
            log.warning("Error while get internal paths in " + path + ". Because program have not permissions.");
        } catch (IOException e) {
            log.warning("Error while get internal paths in " + path);
        }

        return result;
    }
}
