package core.tools.Files;
import java.io.*;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public interface FileIO { 
    public void readFromFile(String filename) throws IOException;
    public void writeToFile(String filename) throws IOException;
    public void setValue(String key,String value);
    public String getValue(String key);
}