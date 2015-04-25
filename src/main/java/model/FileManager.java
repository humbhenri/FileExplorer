package model;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by humberto on 22/04/2015.
 */
public class FileManager {

    private Deque<Path> back = new ArrayDeque<>();
    private Deque<Path> forward = new ArrayDeque<>();
    private List<FileManagerObserver> observers = new ArrayList<>();

    public FileManager() {
        back.push(Paths.get(System.getProperty("user.home")));
    }

    public Path getCurrentDir() {
        return back.peek();
    }

    public void goUp() {
        if (!currentDirIsRoot()) {
            go(getCurrentDir().getParent());
        }
    }

    private boolean currentDirIsRoot() {
        return StreamSupport.stream(FileSystems.getDefault().getRootDirectories().spliterator()
                , false).anyMatch(path -> path.equals(getCurrentDir()));
    }

    public void goForward() {
        if (canGoForward()) {
            back.push(forward.pop());
        }
        directoryChanged();
    }

    public void goBack() {
        if (canGoBack()) {
            forward.push(back.pop());
        }
        directoryChanged();
    }

    public void go(String subfolder) {
        go(getCurrentDir().resolve(subfolder));
    }

    public void go(Path path) {
        back.push(path);
        forward.clear();
        directoryChanged();
    }

    public boolean canGoBack() {
        return back.size() > 1;
    }

    public boolean canGoForward() {
        return !forward.isEmpty();
    }

    public Stream<Path> list() throws IOException {
        return StreamSupport.stream(Files.newDirectoryStream(getCurrentDir()).spliterator(), false);
    }

    public String getFullPath() {
        return getCurrentDir().toAbsolutePath().toString();
    }

    public void addObserver(FileManagerObserver fileManagerObserver) {
        observers.add(fileManagerObserver);
    }

    private void directoryChanged() {
        observers.forEach(FileManagerObserver::directoryChanged);
    }

    public boolean canGoUp() {
        return !currentDirIsRoot();
    }
}
