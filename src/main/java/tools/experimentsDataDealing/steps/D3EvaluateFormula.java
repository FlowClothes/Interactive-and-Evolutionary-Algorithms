package tools.experimentsDataDealing.steps;

import tools.experimentsDataDealing.ExperimentSetup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import core.tools.office.Excel.J2Excel;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class D3EvaluateFormula {
    //淘汰了，公式在第2步中从插入改为直接计算了
    //把文件中的最后一列公式求值
    //然后拷贝到下一列
    String summaryFile = "summary.xlsx";
    J2Excel j2Excel = new J2Excel();

    public static void main(String[] args) {
        D4SummaryData sd = new D4SummaryData();
        //sd.evaluateFormula(args[0], args[1], args[2]);
        sd.summaryData("E:\\backupFile\\Fangcloud of JSNUV2\\个人文件\\study\\papers\\experimentsData\\EAs\\ExperimentsDataNew\\F1\\NoRepeatOnSmaePopulationKnowledge", "\\PS_", "_1D.xlsx");
    }

    public void evaluateFormula(String commonPath, String commonFileName, String commExtension) {
        ExperimentSetup es=new ExperimentSetup();
        for (int i = 0; i < es.getPs().length; i++) {
            String fileName = commonPath + commonFileName + es.getPs()[i] + commExtension;
            System.out.println("处理文件：" + fileName);
            firstStep(fileName, String.valueOf(es.getPs()[i]));
        }
    }

    public void firstStep(String fileName, String sheetName) {//只求公式的值
        try (FileInputStream srcfileInputStream = new FileInputStream(new File(fileName));) {
            XSSFWorkbook wbx = new XSSFWorkbook(srcfileInputStream);
            FormulaEvaluator evaluator = wbx.getCreationHelper().createFormulaEvaluator();
            //找到以数字命名的sheet,对每一个sheet都处理
            Sheet sheet = wbx.getSheet(sheetName);
            //确定其最长的行数rowCount
            int rowCount = sheet.getLastRowNum();
            // System.out.println(rowCount);
            //System.out.println("Max  rowCount" + rowCount);
            //获取其列数
            int colCount = sheet.getRow(0).getLastCellNum() - 1;
            for (int j = 0; j <= rowCount; j++) {//前9行中，只需要处理第0，5，6行即可
                if (j == 0 || j >= 4) {
                    //if (sheetsrc.getRow(j).getCell(colCount).getCellType() == CellType.FORMULA) {
                    evaluator.evaluateFormulaCell(sheet.getRow(j).getCell(colCount));//求公式的值
                    sheet.getRow(j).createCell(colCount + 1).setCellValue(
                            sheet.getRow(j).getCell(colCount).getNumericCellValue());
                }
            }

            srcfileInputStream.close();
            //写入
            try (OutputStream outputStream = new FileOutputStream(new File(fileName))) {
                wbx.write(outputStream);
                wbx.close();
                outputStream.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(D3EvaluateFormula.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(D3EvaluateFormula.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
