package model;

import model.Directory;
import model.File;
import model.FileVisitor;
import model.OpenFileObserver;

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

    }

    @Override
    public void visit(Directory dir) {
        observers.stream().forEach(observer -> observer.directoryChanged(dir.getPwd()));
    }

    public void addObserver(OpenFileObserver observer) {
        observers.add(observer);
    }
}
