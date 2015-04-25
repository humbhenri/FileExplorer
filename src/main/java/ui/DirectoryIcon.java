package ui;

import model.DoubleClickVisitor;
import model.FileManager;
import model.FileSystemEntity;

import javax.swing.*;

/**
 * Created by humberto on 24/04/2015.
 */
public class DirectoryIcon extends AbstractIcon {

    private static final ImageIcon folderIcon = new ImageIcon(DirectoryIcon.class.getClassLoader().getResource("images/folder.png"));

    public DirectoryIcon(FileSystemEntity fileSystemEntity, FileManager fileManager) {
        super(fileSystemEntity, fileManager);
    }

    @Override
    protected void setIcon() {
        setIcon(folderIcon);
    }

    @Override
    protected void doubleClick() {
        DoubleClickVisitor.openFolder(fileManager, fileSystemEntity);
    }

}
