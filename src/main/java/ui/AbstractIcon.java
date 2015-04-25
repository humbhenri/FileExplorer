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
public abstract class AbstractIcon extends JPanel {

    protected final FileSystemEntity fileSystemEntity;
    protected final FileManager fileManager;
    private JLabel icon;

    public AbstractIcon(FileSystemEntity fileSystemEntity, FileManager fileManager) {
        this.fileSystemEntity = fileSystemEntity;
        this.fileManager = fileManager;
        addClickBehaviour();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setIcon();
        setFileName(fileSystemEntity);
    }

    private void setFileName(FileSystemEntity fileSystemEntity) {
        JTextArea fileName = new JTextArea(fileSystemEntity.toString());
        fileName.setLineWrap(true);
        fileName.setEditable(false);
        fileName.setAlignmentX(Component.CENTER_ALIGNMENT);
        fileName.setOpaque(false);
        add(fileName);
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

    protected void setIcon(ImageIcon imageIcon) {
        this.icon = new JLabel(imageIcon);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(this.icon);
    }

    protected abstract void setIcon();

    protected abstract void doubleClick();
}
