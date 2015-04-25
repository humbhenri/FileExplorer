package ui;

import model.*;

import javax.swing.*;

/**
 * Created by humberto on 16/04/2015.
 */

public class IconFileVisitor implements FileVisitor {

    private JComponent component;

    @Override
    public void visit(File file) {
        component = new Icon(file);
    }

    @Override
    public void visit(Directory dir) {
        component = new Icon(dir);
    }

    @Override
    public void visit(Image image) {
        component = new Icon(image);
    }

    public JComponent getIcon() {
        return component;
    }
}
