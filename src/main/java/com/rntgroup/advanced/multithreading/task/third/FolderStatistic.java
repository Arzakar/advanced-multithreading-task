package com.rntgroup.advanced.multithreading.task.third;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderStatistic {

    long folderCount;
    long fileCount;
    long size;

    public FolderStatistic add(FolderStatistic folderStatistic) {
        this.folderCount += folderStatistic.getFolderCount();
        this.fileCount += folderStatistic.getFileCount();
        this.size += folderStatistic.getSize();
        return this;
    }

    public static FolderStatistic empty() {
        return new FolderStatistic()
                .setFolderCount(0)
                .setFileCount(0)
                .setSize(0);
    }
}
