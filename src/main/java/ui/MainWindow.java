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
public class MainWindow extends JFrame implements ActionListener, FileManagerObserver {

    private Logger logger = Logger.getLogger(MainWindow.class.getName());
    private static final String UP = "up";
    private final FileManager fm = new FileManager();

    public MainWindow() {
        fm.addObserver(this);

        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitleFromCurrentDir();

        add(new IconView(fm));

        JToolBar toolBar = new JToolBar();
        add(toolBar, BorderLayout.PAGE_START);
        toolBar.add(makeNavigationButton("go-up.png", UP, "up dir", "up"));
    }

    private void setTitleFromCurrentDir() {
        setTitle(String.format("%s - [%s]",
                "File Explorer",
                fm.getFullPath()));
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
                fm.goUp();
                break;
        }
    }

    @Override
    public void directoryChanged() {
        setTitleFromCurrentDir();
    }

    public static void main(String... args) {
        new MainWindow().setVisible(true);
    }
}
