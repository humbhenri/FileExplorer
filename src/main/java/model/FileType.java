package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by humberto on 25/04/2015.
 */
public class FileType {

    public static String findExtension(String fileName) {
        Pattern fileExtensionPattern = Pattern.compile(".*\\.(\\w+)");
        Matcher matcher = fileExtensionPattern.matcher(fileName);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return "";
    }
}
