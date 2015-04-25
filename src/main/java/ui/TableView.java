package ui;

import model.*;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        model.addColumn("Object");
        model.addColumn("Name");
        model.addColumn("Date");
        model.addColumn("Type");
        model.addColumn("Size");
        JTable table = new JTable(model);
        table.setShowGrid(false);
        table.removeColumn(table.getColumnModel().getColumn(0));
        populateTable();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        addDoubleClickBehaviour(table);
    }

    private void addDoubleClickBehaviour(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table = (JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() == 2) {
                    FileSystemEntity fileSystemEntity = (FileSystemEntity) model.getValueAt(row, 0);
                    if (fileSystemEntity instanceof Directory)
                        DoubleClick.openFolder(fm, fileSystemEntity);
                    else
                        DoubleClick.openFile(fileSystemEntity);
                }
            }
        });
    }

    private void populateTable() {
        model.setNumRows(0);
        try {
            fm.list().forEach(path -> {
                FileSystemEntity fileSystemEntity = FileFactory.create(path);
                model.addRow(new Object[]{
                        fileSystemEntity
                        , fileSystemEntity.getFilename()
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
