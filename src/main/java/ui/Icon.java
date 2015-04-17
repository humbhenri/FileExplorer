package ui;

import model.Directory;
import model.File;

import javax.swing.*;

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
    }

    public Icon(File file) {
        super(file.getFilename(), JLabel.CENTER);
        setIcon(fileIcon);
        putTextBelowIcon();
    }

    private void putTextBelowIcon() {
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
    }

}
