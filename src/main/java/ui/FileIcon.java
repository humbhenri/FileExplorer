package ui;

import model.DoubleClickVisitor;
import model.FileManager;
import model.FileSystemEntity;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Created by humberto on 24/04/2015.
 */
public class FileIcon extends AbstractIcon {

    private static final ImageIcon fileIcon = new ImageIcon(FileIcon.class.getClassLoader().getResource("images/empty.png"));
    private static final Logger LOGGER = Logger.getLogger(FileIcon.class.getName());

    public FileIcon(FileSystemEntity fileSystemEntity, FileManager fileManager) {
        super(fileSystemEntity, fileManager);
    }

    @Override
    protected void setIcon() {
        setIcon(fileIcon);
    }

    @Override
    protected void doubleClick() {
        DoubleClickVisitor.openFile(fileSystemEntity);
    }

}
