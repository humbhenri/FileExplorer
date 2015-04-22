package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by humberto on 16/04/2015.
 */
public class Directory extends FileSystemEntity {

    public Directory(Path path) {
        super(path);
    }

    public Stream<FileSystemEntity> list() {
        try {
            return StreamSupport.stream(Files.newDirectoryStream(getPath()).spliterator(), false)
                    .sorted(sortByDirectoriesFirst())
                    .map(FileFactory::create);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    private Comparator<Path> sortByDirectoriesFirst() {
        return (path1, path2) -> {
            java.io.File file1 = path1.toFile();
            java.io.File file2 = path2.toFile();
            if (file1.isDirectory() && !file2.isDirectory())
                return -1;
            if (!file1.isDirectory() && file2.isDirectory())
                return 1;
            return path1.compareTo(path2);
        };
    }

    @Override
    public void accept(FileVisitor f) {
        f.visit(this);
    }

    public Directory goUp() {
        Path parent = getPath().getParent();
        if (parent == null) {
            return this;
        }
        return new Directory(parent);
    }
}
