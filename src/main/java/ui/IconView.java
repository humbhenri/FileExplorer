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
    private static final Logger LOGGER = Logger.getLogger(IconView.class.getName());

    public IconView(FileManager fm) {
        this.fm = fm;
        fm.addObserver(this);
        setLayout(new ModifiedFlowLayout());
        showIcons();
    }

    private void showIcons() {
        SwingUtilities.invokeLater(() -> {
            Arrays.stream(getComponents()).forEach(this::remove);
            try {
                fm.list().forEach(this::showIcon);
            } catch (IOException e) {
                LOGGER.severe(e.toString());
            }
            updateUI();
        });
    }

    private void showIcon(Path path) {
        FileSystemEntity fileSystemEntity = FileFactory.create(path);
        IconFileVisitor iconFileVisitor = new IconFileVisitor(fileSystemEntity, fm);
        fileSystemEntity.accept(iconFileVisitor);
        add(iconFileVisitor.getIcon());
    }

    @Override
    public void directoryChanged() {
        showIcons();
    }
}
