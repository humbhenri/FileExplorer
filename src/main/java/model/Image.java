package model;

import java.nio.file.Path;

/**
 * Created by humberto on 17/04/2015.
 */
public class Image extends FileSystemEntity {
    public Image(Path path) {
        super(path);
    }

    @Override
    public void accept(FileVisitor f) {
        f.visit(this);
    }

}
