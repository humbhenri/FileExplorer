package model;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by humberto on 25/04/2015.
 */
public class DoubleClick {

    private static final Logger LOGGER = Logger.getLogger(DoubleClick.class.getName());

    public static void openFolder(FileManager fm, FileSystemEntity fileSystemEntity) {
        fm.go(fileSystemEntity.getPath());
    }

    public static void openFile(FileSystemEntity fileSystemEntity) {
        try {
            DefaultApplication.open(fileSystemEntity.getPath());
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
