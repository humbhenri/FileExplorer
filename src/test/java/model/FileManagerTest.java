package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by humberto on 22/04/2015.
 */
@RunWith(JUnit4.class)
public class FileManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    public static final String USER_HOME = "user.home";
    private FileManager fileManager;
    private String userHome;

    @Before
    public void setUp() {
        userHome = System.getProperty(USER_HOME);
        System.setProperty(USER_HOME, testFolder.getRoot().toString());
        fileManager = new FileManager();
    }

    @After
    public void tearDown() {
        System.setProperty(USER_HOME, userHome);
    }

    @Test
    public void startAtUserDir() {
        assertEquals(getTestFolderPath(), fileManager.getCurrentDir());
    }

    @Test
    public void goUp() {
        fileManager.goUp();
        assertEquals(getTestFolderPath().getParent(), fileManager.getCurrentDir());
    }

    @Test
    public void goBack() {
        fileManager.goUp();
        fileManager.goBack();
        assertEquals(getTestFolderPath(), fileManager.getCurrentDir());
    }

    @Test
    public void goBack2() {
        fileManager.goUp();
        fileManager.goUp();
        fileManager.goBack();
        assertEquals(getTestFolderPath().getParent(), fileManager.getCurrentDir());
    }

    @Test
    public void goBack3() {
        fileManager.goUp();
        fileManager.goUp();
        fileManager.goUp();
        fileManager.goBack();
        assertEquals(getTestFolderPath().getParent().getParent(), fileManager.getCurrentDir());
    }

    @Test
    public void goTo() throws IOException {
        testFolder.newFolder("foo");
        fileManager.go("foo");
        assertEquals(getTestFolderPath().resolve("foo"), fileManager.getCurrentDir());
    }

    @Test
    public void goAndBack() throws IOException {
        testFolder.newFolder("baz");
        fileManager.go("baz");
        fileManager.goBack();
        assertEquals(getTestFolderPath(), fileManager.getCurrentDir());
    }

    @Test
    public void goAndBack2() throws IOException {
        testFolder.newFolder("guz", "foo");
        fileManager.go("guz");
        fileManager.go("foo");
        fileManager.goBack();
        fileManager.goBack();
        assertEquals(getTestFolderPath(), fileManager.getCurrentDir());
    }

    @Test
    public void cantGoUpWhenIsRoot() {
        for (int i = 0; i < 100; i++) fileManager.goUp();
        assertTrue(currentDirIsRoot());
    }

    @Test
    public void goBackAtStartShouldDoNothing() {
        fileManager.goBack();
        assertEquals(getTestFolderPath(), fileManager.getCurrentDir());
    }

    @Test
    public void canGoBack() {
        fileManager.goUp();
        assertTrue(fileManager.canGoBack());
    }

    @Test
    public void cantGoBack() {
        assertFalse(fileManager.canGoBack());
    }

    @Test
    public void whenGoToSubFolderCannotGoForward() {
        fileManager.goUp();
        assertFalse(fileManager.canGoForward());
    }

    @Test
    public void whenGoBackCanGoForward() {
        fileManager.goUp();
        fileManager.goBack();
        assertTrue(fileManager.canGoForward());
    }

    @Test
    public void whenGoNewFolderCannotGoForward() throws IOException {
        testFolder.newFolder("foo");
        fileManager.go("foo");
        fileManager.goBack();
        fileManager.go("foo");
        assertFalse(fileManager.canGoForward());
    }

    private boolean currentDirIsRoot() {
        return StreamSupport.stream(FileSystems.getDefault().getRootDirectories().spliterator()
                , false).anyMatch(path -> path.equals(fileManager.getCurrentDir()));
    }

    private Path getTestFolderPath() {
        return Paths.get(testFolder.getRoot().toString());
    }
}