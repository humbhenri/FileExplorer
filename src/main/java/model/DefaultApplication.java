package model;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by humberto on 21/04/2015.
 */
public class DefaultApplication {

    public static void open(Path path) throws IOException {
        Desktop.getDesktop().open(path.toFile());
    }
}
