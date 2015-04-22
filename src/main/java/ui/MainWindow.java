package ui;

import model.DefaultApplication;
import model.OpenFileObserver;
import model.OpenFileVisitor;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by humberto on 16/04/2015.
 */
public class MainWindow extends JFrame implements OpenFileObserver {

    private final IconView iconview;
    private Path currentDir;
    private OpenFileVisitor openFileVisitor = OpenFileVisitor.INSTANCE;
    private Logger logger = Logger.getLogger(MainWindow.class.getName());

    public MainWindow() {
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setCurrentDir(null);
        iconview = new IconView();
        iconview.setDir(getCurrentDir());
        add(iconview);
        openFileVisitor.addObserver(this);
    }

    private void setTitleFromCurrentDir() {
        setTitle(String.format("%s - [%s]",
                "File Explorer",
                getCurrentDir().toAbsolutePath().toString()));
    }

    public Path getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(Path currentDir) {
        this.currentDir = Optional.ofNullable(currentDir).
                orElse(Paths.get(System.getProperty("user.dir")));
        setTitleFromCurrentDir();
    }

    @Override
    public void directoryChanged(Path pwd) {
        setCurrentDir(pwd);
        iconview.setDir(getCurrentDir());
    }

    @Override
    public void fileOpened(Path path) {
        try {
            DefaultApplication.open(path);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    public static void main(String... args) {
        new MainWindow().setVisible(true);
    }
}
