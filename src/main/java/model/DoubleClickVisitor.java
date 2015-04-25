package model;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by humberto on 25/04/2015.
 */
public class DoubleClickVisitor implements FileVisitor {

    private static final Logger LOGGER = Logger.getLogger(DoubleClickVisitor.class.getName());
    private FileManager fm;

    public DoubleClickVisitor(FileManager fm) {
        this.fm = fm;
    }

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

    @Override
    public void visit(File file) {
        openFile(file);
    }

    @Override
    public void visit(Directory dir) {
        openFolder(fm, dir);
    }

    @Override
    public void visit(Image image) {
        openFile(image);
    }
}
