package model;

import java.nio.file.Path;

/**
 * Created by humberto on 17/04/2015.
 */
public interface OpenFileObserver {

    void changeDirectory(Directory dir);

    void fileOpened(File file);
}
