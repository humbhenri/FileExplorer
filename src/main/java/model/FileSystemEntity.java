package model;

import java.nio.file.Path;

/**
 * Created by humberto on 16/04/2015.
 */
public abstract class FileSystemEntity {

    private final Path path;

    protected FileSystemEntity(Path path) {
        this.path = path;
    }

    public String getFilename() {
        return path.getFileName().toString();
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return getFilename();
    }


    public abstract void accept(FileVisitor f);

}
