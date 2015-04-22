package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by humberto on 17/04/2015.
 */
public enum OpenFileVisitor implements FileVisitor {
    INSTANCE;

    private List<OpenFileObserver> observers = new ArrayList<>();

    @Override
    public void visit(File file) {
        observers.stream().forEach(observer -> observer.fileOpened(file.getPath()));
    }

    @Override
    public void visit(Directory dir) {
        observers.stream().forEach(observer -> observer.directoryChanged(dir.getPath()));
    }

    public void visit(Image image) {

    }

    public void addObserver(OpenFileObserver observer) {
        observers.add(observer);
    }
}
