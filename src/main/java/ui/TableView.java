package ui;

import model.FileFactory;
import model.FileManager;
import model.FileManagerObserver;
import model.FileSystemEntity;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Locale;

/**
 * Created by humberto on 25/04/2015.
 */
public class TableView extends JPanel implements FileManagerObserver {

    private FileManager fm;
    private DefaultTableModel model;

    public TableView(FileManager fm) {
        super(new BorderLayout());
        this.fm = fm;
        fm.addObserver(this);
        model = new DefaultTableModel();
        JTable table = new JTable(model);
        table.setShowGrid(false);
        model.addColumn("Name");
        model.addColumn("Date");
        model.addColumn("Type");
        model.addColumn("Size");
        populateTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private void populateTable() {
        for (int row = 0; row < model.getRowCount(); row++) {
            model.removeRow(row);
        }
        try {
            fm.list().forEach(path -> {
                FileSystemEntity fileSystemEntity = FileFactory.create(path);
                model.addRow(new Object[]{
                        fileSystemEntity.getFilename()
                        , getReadableDate(fileSystemEntity)
                        , fileSystemEntity.getType()
                        , FileUtils.byteCountToDisplaySize(fileSystemEntity.getSize())
                });
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getReadableDate(FileSystemEntity fileSystemEntity) {
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault());
        return format.format(fileSystemEntity.getLastModified());
    }

    @Override
    public void directoryChanged() {
        populateTable();
    }
}
