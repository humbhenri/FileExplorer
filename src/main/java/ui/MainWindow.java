package ui;

import model.DefaultApplication;
import model.OpenFileObserver;
import model.OpenFileVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Created by humberto on 16/04/2015.
 */
public class MainWindow extends JFrame implements OpenFileObserver, ActionListener {

    private final IconView iconview;
    private Path currentDir;
    private Logger logger = Logger.getLogger(MainWindow.class.getName());
    static final private String UP = "up";

    public MainWindow() {
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setCurrentDir(null);
        iconview = new IconView();
        iconview.setDir(getCurrentDir());
        add(iconview);
        OpenFileVisitor.INSTANCE.addObserver(this);

        JToolBar toolBar = new JToolBar();
        add(toolBar, BorderLayout.PAGE_START);
        toolBar.add(makeNavigationButton("go-up.png", UP, "up dir", "up"));
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
    public void changeDirectory(Path pwd) {
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

    protected JButton makeNavigationButton(String imageName,
                                           String actionCommand,
                                           String toolTipText,
                                           String altText) {
        String imgLocation = "images/" + imageName;
        URL imageURL = MainWindow.class.getClassLoader().getResource(imgLocation);

        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) {
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {
            button.setText(altText);
            logger.severe("Resource not found: " + imgLocation);
        }

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case UP:
                changeDirectory(getCurrentDir().getParent());
                break;
        }
    }

    public static void main(String... args) {
        new MainWindow().setVisible(true);
    }
}
