package model;

/**
 * Created by humberto on 16/04/2015.
 */
public interface FileVisitor {
    void visit(File file);
    void visit(Directory dir);
}
