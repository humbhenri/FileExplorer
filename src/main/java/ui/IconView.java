package ui;

import model.Directory;

import javax.swing.*;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by humberto on 16/04/2015.
 */
public class IconView extends JPanel {

    public void setDir(final Path dir) {
        IconFileVisitor iconFileVisitor = new IconFileVisitor();
        SwingUtilities.invokeLater(() -> {
            Arrays.stream(getComponents())
                    .filter(component -> component instanceof Icon)
                    .forEach(this::remove);
            new Directory(dir).list().forEach(file -> {
                file.accept(iconFileVisitor);
                add(iconFileVisitor.getIcon());
            });
            updateUI();
        });
    }
}
