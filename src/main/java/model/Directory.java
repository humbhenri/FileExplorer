package model;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by humberto on 16/04/2015.
 */
public class Directory implements FileSystemEntity {

    private Path pwd;

    public Directory(Path path) {
        this.pwd = path;
    }

    public Stream<FileSystemEntity> list() {
        try {
            return StreamSupport.stream(Files.newDirectoryStream(pwd).spliterator(), false)
                    .map(FileFactory::create);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    @Override
    public void accept(FileVisitor f) {
        f.visit(this);
    }

    @Override
    public String toString() {
        return pwd.getFileName().toString();
    }

}
