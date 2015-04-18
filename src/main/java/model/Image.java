package model;

import java.nio.file.Path;

/**
 * Created by humberto on 17/04/2015.
 */
public class Image implements FileSystemEntity {
    private Path path;

    public Image(Path path) {
        this.path = path;
    }

    @Override
    public void accept(FileVisitor f) {
        f.visit(this);
    }

    @Override
    public String toString() {
        return path.getFileName().toString();
    }

    public String getPath() {
        return path.toAbsolutePath().toString();
    }
}
