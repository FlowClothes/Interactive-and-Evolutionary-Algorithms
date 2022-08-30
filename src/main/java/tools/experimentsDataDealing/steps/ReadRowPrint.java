package tools.experimentsDataDealing.steps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import tools.experimentsDataDealing.ExperimentSetup;

/**
 *
 * @author hgs 
 */
public class ReadRowPrint {
//功能：读出行，并输出
    //输出后把内容拷贝到CountCharater中，统计其中字符出现的次数，如","出现的次数
    public static void main(String[] args) {
        ReadRowPrint dadps = new ReadRowPrint();
        dadps.alignData4PS("E:\\tem\\myEAs\\adaptiveRank\\S00",1);
    }

    public void alignData4PS(String fileName,int row) {
        ExperimentSetup es = new ExperimentSetup();
        Workbook wb = null;
        try {
            for (int i = 0; i < es.getPs().length; i++) {
                System.out.print("{\"");
                FileInputStream fileInputStream = new FileInputStream(new File(fileName+"\\D_1_" + es.getPs()[i] + ".xlsx"));
                wb = WorkbookFactory.create(fileInputStream);
                Sheet sheet = wb.getSheet(String.valueOf(es.getPs()[i]));
                int colCount = sheet.getRow(row).getLastCellNum();
                for (int j = 0; j < colCount; j++) {
                    System.out.print(sheet.getRow(row).getCell(j));
                }
                fileInputStream.close();
                System.out.println("\"},");
            }
            wb.close();
            System.out.println("over");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(D5AlignData4PS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(D5AlignData4PS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
