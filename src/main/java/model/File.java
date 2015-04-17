package model;

import java.nio.file.Path;

/**
 * Created by humberto on 16/04/2015.
 */
public class File implements FileSystemEntity {
    private String filename;

    public File(Path path) {
        this.filename = path.getFileName().toString();
    }

    public String getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return getFilename();
    }

    @Override
    public void accept(FileVisitor f) {
        f.visit(this);
    }

}
