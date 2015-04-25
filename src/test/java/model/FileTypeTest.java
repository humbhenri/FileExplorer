package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by humberto on 25/04/2015.
 */
public class FileTypeTest {

    @Test
    public void findExtension() {
        String fileName = "abc.xyz";
        assertEquals("xyz", FileType.findExtension(fileName));
    }
}