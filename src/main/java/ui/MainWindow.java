package ui;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Created by humberto on 16/04/2015.
 */
public class MainWindow extends JFrame {

    private String currentDir;

    public MainWindow() {
        super("File Explorer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCurrentDir(null);
        setTitleFromCurrentDir();
        IconView iconview = new IconView();
        iconview.setDir(getCurrentDir());
        add(iconview);
    }

    private void setTitleFromCurrentDir() {
        setTitle(String.format("%s - [%s]", getTitle(), getCurrentDir()));
    }

    public static void main(String ... args) {
        new MainWindow().setVisible(true);
    }

    public String getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(String currentDir) {
        this.currentDir = Optional.ofNullable(currentDir).
                orElse(Paths.get(".").toAbsolutePath().normalize().toString());
    }
}
