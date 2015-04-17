package ui;

import model.Directory;

import javax.swing.*;
import java.nio.file.Paths;

/**
 * Created by humberto on 16/04/2015.
 */
public class IconView extends JPanel {

    private String dir;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        IconFileVisitor iconFileVisitor = new IconFileVisitor();
        SwingUtilities.invokeLater(() -> {
            removeAll();
            new Directory(Paths.get(dir)).list().forEach(file -> {
                file.accept(iconFileVisitor);
                add(iconFileVisitor.getIcon());
            });
        });
    }
}
