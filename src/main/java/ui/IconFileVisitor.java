package ui;

import model.Directory;
import model.File;
import model.FileSystemEntity;
import model.FileVisitor;

import javax.swing.*;

/**
 * Created by humberto on 16/04/2015.
 */

public class IconFileVisitor implements FileVisitor {

    private JComponent component;

    public void visit(File file) {
        component = new Icon(file);
    }

    @Override
    public void visit(Directory dir) {
        component = new Icon(dir);
    }

    public JComponent getIcon() {
        return component;
    }
}
