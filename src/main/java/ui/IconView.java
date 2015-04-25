package ui;

import model.*;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by humberto on 16/04/2015.
 */
public class IconView extends JPanel implements FileManagerObserver {

    private final FileManager fm;
    private IconFileVisitor iconFileVisitor;
    private static final Logger LOGGER = Logger.getLogger(IconView.class.getName());

    public IconView(FileManager fm) {
        iconFileVisitor = new IconFileVisitor();
        this.fm = fm;
        fm.addObserver(this);
        showFiles();
    }

    private void showFiles() {
        SwingUtilities.invokeLater(() -> {
            Arrays.stream(getComponents()).forEach(this::remove);
            try {
                fm.list().forEach(this::addIcon);
            } catch (IOException e) {
                LOGGER.severe(e.getMessage());
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
