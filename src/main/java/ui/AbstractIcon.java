package ui;

import model.FileManager;
import model.FileSystemEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by humberto on 24/04/2015.
 */
public abstract class AbstractIcon extends JLabel {

    protected final FileSystemEntity fileSystemEntity;
    protected final FileManager fileManager;

    public AbstractIcon(FileSystemEntity fileSystemEntity, FileManager fileManager) {
        super(fileSystemEntity.toString());
        this.fileSystemEntity = fileSystemEntity;
        this.fileManager = fileManager;
        alignText();
        addClickBehaviour();
        setIcon();
    }

    protected void alignText() {
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
    }

    protected void addClickBehaviour() {
        setOpaque(true);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    doubleClick();
                } else {
                    singleClick();
                }
            }
        });
    }

    protected void singleClick() {
        SwingUtilities.invokeLater(() -> {
            for (Component component : getParent().getComponents()) {
                if (component instanceof AbstractIcon) {
                    component.setBackground(UIManager.getColor("InternalFrame.background"));
                }
            }
            setBackground(UIManager.getColor("TextField.selectionBackground"));
        });
    }

    protected abstract void setIcon();

    protected abstract void doubleClick();
}
