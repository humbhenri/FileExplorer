package model;

/**
 * Created by humberto on 16/04/2015.
 */
public interface FileSystemEntity {

    void accept(FileVisitor f);
}
