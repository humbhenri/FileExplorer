package model;

import java.nio.file.Path;

/**
 * Created by humberto on 16/04/2015.
 */
public class File extends FileSystemEntity {

    public File(Path path) {
        super(path);
    }

    @Override
    public void accept(FileVisitor f) {
        f.visit(this);
    }

}
