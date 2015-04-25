package ui;

import model.*;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Created by humberto on 16/04/2015.
 */
public class IconView extends JPanel implements FileManagerObserver {

    private final FileManager fm;
    private IconFileVisitor iconFileVisitor;

    public IconView(FileManager fm) {
        iconFileVisitor = new IconFileVisitor();
        this.fm = fm;
        fm.addObserver(this);
        showFiles();
    }

    private void showFiles() {
        SwingUtilities.invokeLater(() -> {
            Arrays.stream(getComponents())
                    .filter(component -> component instanceof Icon)
                    .forEach(this::remove);
            try {
                fm.list().forEach(this::addIcon);
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateUI();
        });
    }

    private void addIcon(Path path) {
        FileSystemEntity fileSystemEntity = FileFactory.create(path);
        fileSystemEntity.accept(iconFileVisitor);
        add(iconFileVisitor.getIcon());
    }

    @Override
    public void directoryChanged() {
        showFiles();
    }
}
