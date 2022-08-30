package core.tools.office.Excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class J2Excel {
//行列值从0开始

    int fileNameIndex = 0;
//参考https://howtodoinjava.com/library/readingwriting-excel-files-in-java-poi-tutorial/
    //https://poi.apache.org/

    public synchronized void write2Excel(String excelFileName, String sheetName, Map<Integer, List<Object>> data) {
        accessFile(excelFileName);
        try {
            Workbook workbook;
            //HSSFFormulaEvaluator evaluator;
            try (FileInputStream inputStream = new FileInputStream(new File(excelFileName))) {
                workbook = WorkbookFactory.create(inputStream);
                //evaluator=new HSSFFormulaEvaluator((HSSFWorkbook)workbook);
                Sheet sheet = workbook.getSheet(sheetName);
                if (null == sheet) {//如果这个Sheet不存在
                    sheet = workbook.createSheet(sheetName);
                }   //处理要写入的内容，已经指定了行和列
                //找到已经存在的列的最大值，在这列后边写入
                int colCount = 0;
                if (null != sheet.getRow(0)) {
                    colCount = sheet.getRow(0).getLastCellNum();
                }
                Set<Integer> keyset = data.keySet();
                for (Integer key : keyset) {
                    Row row = sheet.getRow(key);
                    if (null == row) {//第一次写入这一行
                        row = sheet.createRow(key);
                    }
                    List objArr = data.get(key);
                    int cellnum = colCount;//向后追加
                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellnum++);
                        setCellValue(cell, obj, fileNameIndex++);
                    }
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(excelFileName)) {
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();
            }
        } catch (IOException | EncryptedDocumentException ex) {
        }
    }

    public void accessFile(String excelFileName) {
        File file = new File(excelFileName);
        if (!file.exists()) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook();
                //==============下面这段内容必须有
                XSSFSheet sheet = workbook.createSheet("welcome");
                Row row = sheet.createRow(1);
                Cell cell = row.createCell(1);
                cell.setCellValue("Created by myEAs");
                //=================================================
                FileOutputStream out = new FileOutputStream(file, true);
                workbook.write(out);
                workbook.close();
                out.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(J2Excel.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(J2Excel.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setCellValue(Cell cell, Object obj, int fileNameIndex) {
        if (obj instanceof String) {
            String objString = (String) obj;
            if (objString.length() > 30000) {//能存放的最大长度为32767
                String fileName = "data" + fileNameIndex + ".txt";
                FileWriter fw;
                try {
                    fw = new FileWriter(new File(fileName));
                    fw.write(objString);
                    fw.flush();
                    fw.close();
                    cell.setCellValue(fileName);
                } catch (IOException ex) {
                    Logger.getLogger(J2Excel.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                cell.setCellValue(objString);
            }
        } else if (obj instanceof Integer) {
            cell.setCellValue((Integer) obj);
        } else if (obj instanceof Float) {
            cell.setCellValue((Float) obj);
        } else if (obj instanceof Long) {
            cell.setCellValue((Long) obj);
        } else if (obj instanceof Double) {
            cell.setCellValue((Double) obj);
        } else {
            cell.setCellValue(obj.toString());
        }
    }

    public Row getRow(Sheet sheet1, int k) {
        Row row;
        if (null == sheet1.getRow(k)) {
            row = sheet1.createRow(k);
        } else {
            row = sheet1.getRow(k);
        }
        return row;
    }

    public void setCellValue(FormulaEvaluator evaluator, Cell src, Cell dst) {//是不是公式，由调用者决定
        if (null == evaluator) {
            if (null != src && null != src.getCellType()) {
                switch (src.getCellType()) {
                    case BOOLEAN:
                        dst.setCellValue(src.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        dst.setCellValue(src.getNumericCellValue());
                        break;
                    case STRING:
                        dst.setCellValue(src.getStringCellValue());
                        break;
                    case FORMULA://把公式的结果拿来
                        //evaluator.evaluate(src);
                        //dst.setCellValue(src.getNumericCellValue());
                        break;
                    default:
                        dst.setCellValue(0);
                }
            }
        } else {//拷贝公式的值
            if (src.getCellType() == CellType.FORMULA) {
                switch (evaluator.evaluateFormulaCell(src)) {
                    case BOOLEAN:
                        dst.setCellValue(src.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        dst.setCellValue(src.getNumericCellValue());
                        break;
                    case STRING:
                        dst.setCellValue(src.getRichStringCellValue());
                        break;
                }
            }
        }
    }

}
