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
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import core.tools.office.Excel.J2Excel;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class D4SummaryData {

    //把不同种群实验的数据拷贝到同一个文件中
    //步骤：
    //0.创建一个新文件summary.xlsx
    //1.打开第i个种群文件
    //2.把最后一列拷贝到summary.xlsx
    String summaryFile = "summary.xlsx";
    J2Excel j2Excel = new J2Excel();

    public static void main(String[] args) {
        D4SummaryData sd = new D4SummaryData();
        //sd.summaryData(args[0], args[1], args[2]);
        sd.summaryData("E:\\tem\\myEAs\\data\\F0\\binaryCode", "\\D",".xlsx");
    }

    public void summaryData(String commonPath, String commonFileName,String commExtension) {
        //0.创建一个新文件summary.xlsx
        String summaryFileName = commonPath + "\\" + summaryFile;
        j2Excel.accessFile(summaryFileName);
        Workbook wbdst;
        ExperimentSetup es=new ExperimentSetup();
        try {
            try (FileInputStream dstInputStream = new FileInputStream(new File(summaryFileName))) {
                wbdst = WorkbookFactory.create(dstInputStream);
                Sheet sheetDst;
                if (null == wbdst.getSheet("fitness")) {
                    sheetDst = wbdst.createSheet("fitness");
                } else {
                    sheetDst = wbdst.getSheet("fitness");
                }
                //初始化目标sheet的各行
                initDstRow(commonPath +  commonFileName + 10 +commExtension , sheetDst, 10);//这里采用2是因为PS=2时，行数最长，因此以此为目标文件确定最大行数
                //开始拷贝数据
                for (int i = 0; i < es.getPs().length; i++) {
                    //确定目标sheet中的列
                    int colValue = 0;
                    if (es.getPs()[i] < 10) {
                        colValue = es.getPs()[i] - 2;//从第0列开始写
                    } else if (es.getPs()[i] >= 10) {
                        //colValue = 9+es.getPs()[i] / 10 - 2;//从第10列开始写
                        colValue = es.getPs()[i] / 10 - 1;//种群规模从10开始的
                    }
                    ///
                    String fileName = commonPath +  commonFileName + es.getPs()[i] + commExtension;
                    //System.out.println("处理文件："+fileName);
                    try (FileInputStream srcfileInputStream = new FileInputStream(new File(fileName));) {
                        XSSFWorkbook wbsrc = new XSSFWorkbook(srcfileInputStream);
                       // FormulaEvaluator evaluator = wbsrc.getCreationHelper().createFormulaEvaluator();
                        //找到以数字命名的sheet,对每一个sheet都处理
                        Sheet sheetsrc = wbsrc.getSheet(String.valueOf(es.getPs()[i]));
                        //确定其最长的行数rowCount
                        int rowCount = sheetsrc.getLastRowNum();
                        //System.out.println("Max  rowCount" + rowCount);
                        //获取其列数
                        int colCount = sheetsrc.getRow(0).getLastCellNum() - 1;
                        for (int j = 0; j < 9; j++) {//前9行中，只需要处理第0，5，6行即可
                            if (j == 0 || j==4||j == 5 || j == 6) {
                                sheetDst.getRow(j).createCell(colValue);
                                j2Excel.setCellValue(null, sheetsrc.getRow(j).getCell(colCount), sheetDst.getRow(j).getCell(colValue));
                            }
                            if (j == 7) {//记录种群规模
                                sheetDst.getRow(j).createCell(colValue).setCellValue(es.getPs()[i]);
                            }
                        }
                        int start = 8;//从第9行开始继续拷贝
                        int k = start;
                        for (; k <= rowCount; k++) {
                            sheetDst.getRow(k).createCell(colValue);
                            j2Excel.setCellValue(null, sheetsrc.getRow(k).getCell(colCount), sheetDst.getRow(k).getCell(colValue));
                        }
                        srcfileInputStream.close();
                    }
                }
            }
            try (OutputStream outputStream = new FileOutputStream(new File(summaryFileName))) {
                wbdst.write(outputStream);
                wbdst.close();
                outputStream.close();
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(D1FillUnbalancedata.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(D1FillUnbalancedata.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initDstRow(String fileName, Sheet sheetDst, int minPS) {   //初始化各行
        //根据种群规模为2时的行数确定最大行
        FileInputStream srcfileInputStream;
        try {
            srcfileInputStream = new FileInputStream(new File(fileName));
            Workbook wbsrc = WorkbookFactory.create(srcfileInputStream);
            //找到以数字命名的sheet,对每一个sheet都处理
            Sheet sheetsrc = wbsrc.getSheet(String.valueOf(minPS));
            //确定其最长的行数rowCount
            int rowCount = sheetsrc.getLastRowNum();
            for (int i = 0; i <= rowCount; i++) {
                sheetDst.createRow(i);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(D4SummaryData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(D4SummaryData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
