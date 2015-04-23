package model;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;
import java.util.stream.StreamSupport;

/**
 * Created by humberto on 22/04/2015.
 */
public class FileManager {

    private Deque<Path> back = new ArrayDeque<>();
    private Deque<Path> forward = new ArrayDeque<>();

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

    public void goBack() {
        if (canGoBack()) {
            forward.push(back.pop());
        }
    }

    public void go(String subfolder) {
        go(getCurrentDir().resolve(subfolder));
    }

    public void go(Path path) {
        back.push(path);
        forward.clear();
    }

    public boolean canGoBack() {
        return back.size() > 1;
    }

    public boolean canGoForward() {
        return !forward.isEmpty();
    }
}
