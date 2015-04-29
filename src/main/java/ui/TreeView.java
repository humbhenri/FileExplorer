package ui;

import model.FileManager;
import model.FileManagerObserver;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by humberto on 29/04/2015.
 */

class FileSystemModel implements TreeModel {
    private File root;

    private Vector listeners = new Vector();

    public FileSystemModel(File rootDirectory) {
        root = rootDirectory;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        File directory = (File) parent;
        String[] children = directory.list();
        return new TreeFile(directory, children[index]);
    }

    public int getChildCount(Object parent) {
        File file = (File) parent;
        if (file.isDirectory()) {
            String[] fileList = file.list();
            if (fileList != null)
                return file.list().length;
        }
        return 0;
    }

    public boolean isLeaf(Object node) {
        File file = (File) node;
        return file.isFile();
    }

    public int getIndexOfChild(Object parent, Object child) {
        File directory = (File) parent;
        File file = (File) child;
        String[] children = directory.list();
        for (int i = 0; i < children.length; i++) {
            if (file.getName().equals(children[i])) {
                return i;
            }
        }
        return -1;

    }

    public void valueForPathChanged(TreePath path, Object value) {
        File oldFile = (File) path.getLastPathComponent();
        String fileParentPath = oldFile.getParent();
        String newFileName = (String) value;
        File targetFile = new File(fileParentPath, newFileName);
        oldFile.renameTo(targetFile);
        File parent = new File(fileParentPath);
        int[] changedChildrenIndices = {getIndexOfChild(parent, targetFile)};
        Object[] changedChildren = {targetFile};
        fireTreeNodesChanged(path.getParentPath(), changedChildrenIndices, changedChildren);

    }

    private void fireTreeNodesChanged(TreePath parentPath, int[] indices, Object[] children) {
        TreeModelEvent event = new TreeModelEvent(this, parentPath, indices, children);
        Iterator iterator = listeners.iterator();
        TreeModelListener listener = null;
        while (iterator.hasNext()) {
            listener = (TreeModelListener) iterator.next();
            listener.treeNodesChanged(event);
        }
    }

    public void addTreeModelListener(TreeModelListener listener) {
        listeners.add(listener);
    }

    public void removeTreeModelListener(TreeModelListener listener) {
        listeners.remove(listener);
    }


}

class TreeFile extends File {
    public TreeFile(File parent, String child) {
        super(parent, child);
    }

    public String toString() {
        return getName();
    }
}

public class TreeView extends JTree implements TreeSelectionListener, FileManagerObserver {

    private FileManager fm;

    public TreeView(FileManager fm, File root) {
        super(new FileSystemModel(root));
        this.fm = fm;
        fm.addObserver(this);
        setEditable(false);
        addTreeSelectionListener(this);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreeFile node = (TreeFile)
                getLastSelectedPathComponent();

        if (node == null)
            //Nothing is selected.
            return;

        if (node.isDirectory()) {
            fm.go(node.toPath());
        }
    }

    @Override
    public void directoryChanged() {

    }
}
