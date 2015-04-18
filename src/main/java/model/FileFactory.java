package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by humberto on 16/04/2015.
 */
public class FileFactory {

    private final static Logger logger = Logger.getLogger(FileFactory.class.getName());

    public static FileSystemEntity create(Path path) {
        if (path.toFile().isDirectory()) {
            return new Directory(path);
        } else {
            File defaultFile = new File(path);
            try {
                String type = Files.probeContentType(path);
                if (isImage(type)) {
                    return new Image(path);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
                return defaultFile;
            }
            return defaultFile;
        }
    }

    private static boolean isImage(String type) {
        return type.contains("image");
    }
}
