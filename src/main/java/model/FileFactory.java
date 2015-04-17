package model;

import java.nio.file.Path;

/**
 * Created by humberto on 16/04/2015.
 */
public class FileFactory {

    public static FileSystemEntity create(Path path) {
        if (path.toFile().isDirectory()) {
            return new Directory(path);
        } else {
            return new File(path);
        }
    }
}
