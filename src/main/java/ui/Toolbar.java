package ui;

import model.FileManager;
import model.FileManagerObserver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by humberto on 25/04/2015.
 */
public class Toolbar extends JToolBar implements ActionListener, FileManagerObserver {

    private static final Logger LOGGER = Logger.getLogger(Toolbar.class.getName());
    private static final String UP = "up";
    private static final String BACK = "back";
    private static final String FORWARD = "forward";
    private final JButton up;
    private final JButton forward;
    private final JButton back;
    private FileManager fm;

    public Toolbar(FileManager fm) {
        this.fm = fm;
        fm.addObserver(this);
        up = makeNavigationButton("go-up.png", UP, "up dir", "up");
        add(up);
        back = makeNavigationButton("go-back.png", BACK, "go back", "back");
        add(back);
        forward = makeNavigationButton("go-next.png", FORWARD, "go forward", "forward");
        add(forward);
        enableNavButtons();
        setFloatable(false);
    }

    private JButton makeNavigationButton(String imageName,
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
            LOGGER.severe("Resource not found: " + imgLocation);
        }

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case UP:
                fm.goUp();
                break;
            case BACK:
                fm.goBack();
                break;
            case FORWARD:
                fm.goForward();
                break;
        }
    }


    @Override
    public void directoryChanged() {
        enableNavButtons();
    }

    private void enableNavButtons() {
        back.setEnabled(fm.canGoBack());
        forward.setEnabled(fm.canGoForward());
        up.setEnabled(fm.canGoUp());
    }
}
