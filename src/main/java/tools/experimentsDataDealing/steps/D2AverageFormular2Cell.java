package tools.experimentsDataDealing.steps;

import tools.experimentsDataDealing.ExperimentSetup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class D2AverageFormular2Cell {

    public static void main(String[] args) {
        ExperimentSetup es = new ExperimentSetup();
        D2AverageFormular2Cell afc = new D2AverageFormular2Cell();
        String filename = "E:\\tem\\myEAs\\data\\F0\\binaryCode\\D";
        for (int i = 0; i < es.getPs().length; i++) {
            afc.putFormular2Cell(filename + es.getPs()[i] + ".xlsx", es.getPs()[i]);
        }
    }

    public void putFormular2Cell(String fileName, int populationsize) {
        System.out.println("处理文件" + fileName);
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(fileName);
            FileOutputStream outputStream;
            try (Workbook wb = WorkbookFactory.create(inputStream)) {
                Sheet sheet = wb.getSheet(String.valueOf(populationsize));
                //确定其最长的行数rowCount
                int rowCount = sheet.getLastRowNum();
                //获取其列数,一定在第201列，即GS列
                // int colCount = sheet.getRow(0).getLastCellNum();
                for (int i = 0; i < 7; i++) {
                    if (null == sheet.getRow(i)) {
                        continue;
                    }
//                    sheet.getRow(i).createCell(colCount);// 创建最后一列
//                    String formular = "";
                    switch (i) {
                        case 0:// 第1行是运行时间，单位ms
                        case 4:
                        case 6:
                            calculateAverage(sheet.getRow(i), true);
                            //formular = "AVERAGE(A1:GR1)";
                            //sheet.getRow(i).getCell(colCount).setCellFormula(formular);
                            break;
                        case 1:
                            break;
                        case 2://第3行禁忌域的大小
                            break;
                        case 3://第4行是最优解所对应的X值
                            break;
                        //case 4://第5行是最优适应值maxf(X)
                        //formular = "AVERAGE(A5:GR5)";
                        // sheet.getRow(i).getCell(colCount).setCellFormula(formular);
                        // break;
                        case 5://第6行是统计hit最优解的次数
                            calculateAverage(sheet.getRow(i), false);
//                            formular = "SUM(A6:GR6)";
//                            sheet.getRow(i).getCell(colCount).setCellFormula(formular);
                            break;
//                        case 6://第7行是找到最优解时或进化结束时的进化代数
//                            formular = "AVERAGE(A7:GR7)";
//                            sheet.getRow(i).getCell(colCount).setCellFormula(formular);
//                            break;
                        default:
                    }
                }
                // String formular = "AVERAGE(A";
                for (int i = 7; i <= rowCount; i++) {
//                    if (sheet.getRow(i) == null) {
//                        sheet.createRow(i);
//                    }
//                    sheet.getRow(i)
//                            .createCell(colCount)
//                            .setCellFormula(formular + (i + 1) + ":GR" + (i + 1) + ")");
                    calculateAverage(sheet.getRow(i), true);
                }
                inputStream.close();
                outputStream = new FileOutputStream(fileName);
                wb.write(outputStream);
            }
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(D2AverageFormular2Cell.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(D2AverageFormular2Cell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void calculateAverage(Row row, boolean average) {
        int colCount = row.getLastCellNum();
        if (colCount > 0) {//会出现-1的情况
            row.createCell(colCount);// 创建最后一列
        }
        double sum = 0;
        for (int i = 0; i < colCount; i++) {
            sum += row.getCell(i).getNumericCellValue();
        }
        if (colCount > 0) {
            if (average) {//求平均值
                row.getCell(colCount).setCellValue(sum / colCount);
            } else {//求和
                row.getCell(colCount).setCellValue(sum);
            }
        }else{
            System.out.println("");
        }
    }
}
