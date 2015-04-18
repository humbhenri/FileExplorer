package ui;

import model.*;
import model.File;
import model.Image;
import sun.rmi.runtime.Log;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by humberto on 16/04/2015.
 */
public class Icon extends JLabel {

    private static final ImageIcon folderIcon = new ImageIcon(Icon.class.getClassLoader().getResource("images/folder.png"));
    private static final ImageIcon fileIcon = new ImageIcon(Icon.class.getClassLoader().getResource("images/empty.png"));
    private FileSystemEntity fileSystemEntity;
    private static final Logger logger = Logger.getLogger(Icon.class.getName());

    public Icon(Directory directory) {
        super(directory.toString());
        fileSystemEntity = directory;
        setIcon(folderIcon);
        putTextBelowIcon();
        addSelectBehaviour();
    }

    public Icon(File file) {
        super(file.getFilename(), JLabel.CENTER);
        fileSystemEntity = file;
        setIcon(fileIcon);
        putTextBelowIcon();
        addSelectBehaviour();
    }

    public Icon(Image image) {
        super(image.toString(), JLabel.CENTER);
        fileSystemEntity = image;
        setIcon(loadThumbnail(image));
        putTextBelowIcon();
        addSelectBehaviour();
    }

    private ImageIcon loadThumbnail(Image image) {
        try {
            java.awt.Image thumb = ImageIO.read(new java.io.File(image.getPath()))
                    .getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
            return new ImageIcon(thumb);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return fileIcon;
        }
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
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    e.consume();
                    fileSystemEntity.accept(OpenFileVisitor.INSTANCE);
                } else {
                    selectOnlyThisIcon();
                }
            }
        });
    }

    private void selectOnlyThisIcon() {
        SwingUtilities.invokeLater(() -> {
            for (Component component : getParent().getComponents()) {
                if (component instanceof Icon) {
                    component.setBackground(UIManager.getColor("InternalFrame.background"));
                }
            }
            setBackground(UIManager.getColor("TextField.selectionBackground"));
        });
    }

}
