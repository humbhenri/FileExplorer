package ui;

import model.*;

import javax.swing.*;

/**
 * Created by humberto on 16/04/2015.
 */

public class IconFileVisitor implements FileVisitor {

    private JComponent component;
    protected final FileSystemEntity fileSystemEntity;
    protected final FileManager fileManager;

    public IconFileVisitor(FileSystemEntity fileSystemEntity, FileManager fileManager) {
        this.fileSystemEntity = fileSystemEntity;
        this.fileManager = fileManager;
    }

    @Override
    public void visit(File file) {
        component = new FileIcon(fileSystemEntity, fileManager);
    }

    @Override
    public void visit(Directory dir) {
        component = new DirectoryIcon(fileSystemEntity, fileManager);
    }

    @Override
    public void visit(Image image) {
        component = new ThumbnailIcon(fileSystemEntity, fileManager);
    }

    public JComponent getIcon() {
        return component;
    }
}
