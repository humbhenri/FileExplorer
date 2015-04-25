package model;

import java.nio.file.Path;
import java.util.Date;

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

    public String getType() {
        return FileType.findExtension(getFilename());
    }

    public Date getLastModified() {
        return new Date(path.toFile().lastModified());
    }

    public long getSize() {
        return path.toFile().length();
    }

}
