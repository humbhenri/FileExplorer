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
import java.util.logging.Logger;

/**
 * Created by humberto on 25/04/2015.
 */
public class TableView extends JPanel implements FileManagerObserver {

    private static Logger LOGGER = Logger.getLogger(TableView.class.getName());
    private FileManager fm;
    private DefaultTableModel model;

    public TableView(FileManager fm) {
        super(new BorderLayout());
        this.fm = fm;
        fm.addObserver(this);
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("Object");
        model.addColumn("Name");
        model.addColumn("Date");
        model.addColumn("Type");
        model.addColumn("Size");
        JTable table = new JTable(model);
        table.setShowGrid(false);
        table.getColumnModel().removeColumn(table.getColumn("Object"));
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
                    DoubleClickVisitor doubleClickVisitor = new DoubleClickVisitor(fm);
                    fileSystemEntity.accept(doubleClickVisitor);
                }
            }
        });
    }

    private void populateTable() {
        model.setRowCount(0);
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
            LOGGER.severe(e.toString());
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
