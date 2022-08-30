package core.tools.Files;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class FileProperties extends Properties implements FileIO {

    @Override
    public void readFromFile(String filename) {
        filename=filename.trim();
        try {
            File file = new File(filename);
            file.getAbsolutePath();
            if (file.exists()) {
                load(new FileInputStream(filename));
            } else {
                System.out.println(filename + "不存在");
                System.exit(0);
            }
        } catch (IOException e) {
        }
    }

    @Override
    public void writeToFile(String filename) {
        try {
            store(new FileOutputStream(filename), "Writen by myEAs");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setValue(String key, String value) {
        setProperty(key, value);
    }

    @Override
    public String getValue(String key) {
        return getProperty(key, "").trim();
    }
}
