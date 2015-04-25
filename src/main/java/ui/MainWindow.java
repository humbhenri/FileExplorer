package ui;

import model.FileManager;
import model.FileManagerObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by humberto on 16/04/2015.
 */
public class MainWindow extends JFrame implements FileManagerObserver {

    private Logger logger = Logger.getLogger(MainWindow.class.getName());
    private final FileManager fm = new FileManager();

    public MainWindow() {
        fm.addObserver(this);

        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitleFromCurrentDir();

        IconView iconView = new IconView(fm);
        JScrollPane scrollPane = new JScrollPane(iconView,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        Toolbar toolBar = new Toolbar(fm);
        add(toolBar, BorderLayout.PAGE_START);
    }

    private void setTitleFromCurrentDir() {
        setTitle(String.format("%s - [%s]",
                "File Explorer",
                fm.getFullPath()));
    }

    @Override
    public void directoryChanged() {
        setTitleFromCurrentDir();
    }

    public static void main(String... args) {
        new MainWindow().setVisible(true);
    }
}
