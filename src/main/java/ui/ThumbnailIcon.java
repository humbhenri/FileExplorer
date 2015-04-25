package ui;

import model.DefaultApplication;
import model.FileManager;
import model.FileSystemEntity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by humberto on 24/04/2015.
 */
public class ThumbnailIcon extends AbstractIcon {

    private static final Logger LOGGER = Logger.getLogger(ThumbnailIcon.class.getName());

    public ThumbnailIcon(FileSystemEntity fileSystemEntity, FileManager fileManager) {
        super(fileSystemEntity, fileManager);
    }

    @Override
    protected void setIcon() {
        try {
            java.awt.Image thumb = ImageIO.read(fileSystemEntity.getPath().toFile())
                    .getScaledInstance(100, 100, BufferedImage.SCALE_SMOOTH);
            setIcon(new ImageIcon(thumb));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    @Override
    protected void doubleClick() {
        try {
            DefaultApplication.open(fileSystemEntity.getPath());
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

}
