package ui;

import model.FileManager;
import model.FileManagerObserver;

import javax.swing.*;
import java.awt.*;

/**
 * Created by humberto on 16/04/2015.
 */
public class MainWindow extends JFrame implements FileManagerObserver, DisplayViewObserver {

    public static final String ICON_VIEW = "icon view";
    public static final String DETAILS_VIEW = "details view";
    private final FileManager fm = new FileManager();
    private JPanel directoryView;

    public MainWindow() {
        fm.addObserver(this);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitleFromCurrentDir();
        addDirectoryView();
        Toolbar toolBar = new Toolbar(fm, this);
        add(toolBar, BorderLayout.PAGE_START);
    }

    public static void main(String... args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        new MainWindow().setVisible(true);
    }

    private void addDirectoryView() {
        JComponent icons = addIconView();
        JComponent details = new TableView(fm);
        directoryView = new JPanel(new CardLayout());
        directoryView.add(ICON_VIEW, icons);
        directoryView.add(DETAILS_VIEW, details);
        add(directoryView);
    }

    private JComponent addIconView() {
        IconView iconView = new IconView(fm);
        return new JScrollPane(iconView,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

    @Override
    public void showIconView() {
        CardLayout cardLayout = (CardLayout) directoryView.getLayout();
        cardLayout.show(directoryView, ICON_VIEW);
    }

    @Override
    public void showDetailsView() {
        CardLayout cardLayout = (CardLayout) directoryView.getLayout();
        cardLayout.show(directoryView, DETAILS_VIEW);
    }
}
