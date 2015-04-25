package model;

import java.nio.file.Path;

/**
 * Created by humberto on 16/04/2015.
 */
public class Directory extends FileSystemEntity {

    public Directory(Path path) {
        super(path);
    }

    @Override
    public void accept(FileVisitor f) {
        f.visit(this);
    }

}
