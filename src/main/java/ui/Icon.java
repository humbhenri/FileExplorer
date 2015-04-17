package ui;

import model.Directory;
import model.File;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by humberto on 16/04/2015.
 */
public class Icon extends JLabel {

    private static final ImageIcon folderIcon = new ImageIcon(Icon.class.getClassLoader().getResource("images/folder.png"));
    private static final ImageIcon fileIcon = new ImageIcon(Icon.class.getClassLoader().getResource("images/empty.png"));

    public Icon(Directory directory) {
        super(directory.toString());
        setIcon(folderIcon);
        putTextBelowIcon();
        addSelectBehaviour();
    }

    public Icon(File file) {
        super(file.getFilename(), JLabel.CENTER);
        setIcon(fileIcon);
        putTextBelowIcon();
        addSelectBehaviour();
    }

    private void putTextBelowIcon() {
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
    }

    private void addSelectBehaviour() {
        setOpaque(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    for (Component component : getParent().getComponents()) {
                        if (component instanceof Icon) {
                            component.setBackground(UIManager.getColor("InternalFrame.background"));
                        }
                    }
                    setBackground(UIManager.getColor("TextField.selectionBackground"));
                });
            }
        });
    }

}
