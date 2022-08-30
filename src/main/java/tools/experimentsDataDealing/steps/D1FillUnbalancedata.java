/*
 * 这个类中，处理数据。在程序记录进化过程数据时，有的实验结束早，有的则结束完。例如有的2代就找到了最优解，而有的则9938393代才找到最优解。在求取200次实验的最优解的平均值时，首先需要把那些空缺的数据补充或填满。如把2代就结束的后续代用最后的解的值补充完整。这个程序就负责补充完整这些数据
 */
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

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class D1FillUnbalancedata {

    public static void main(String[] args) {
        D1FillUnbalancedata fud = new D1FillUnbalancedata();
        ExperimentSetup es = new ExperimentSetup();
        for (int i = 0; i < es.getPs().length; i++) {
            fud.completeData("E:\\tem\\myEAs\\data\\F0\\binaryCode\\D"+es.getPs()[i]+".xlsx", es.getPs()[i]);
        }
    }

    //面向实验数据处理的，其中的实验数据是每个实验有的列数据长，而有的列数据短。
    //本方法要把短的列数据补全，使列数据一样长
    //参数为：指定excel文件名
    public void completeData(String fileName, int sheetName) {
        Workbook wb;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
            wb = WorkbookFactory.create(fileInputStream);
            //找到以数字命名的sheet,对每一个sheet都处理
            Sheet sheet = wb.getSheet(String.valueOf(sheetName));
            //确定其最长的行数rowCount
            int rowCount = sheet.getLastRowNum();
            //System.out.println("Max  rowCount" + rowCount);
            //获取其列数
            int colCount = sheet.getRow(0).getLastCellNum();
            //System.out.println("columnCount" + colCount);
            //对每一列进行遍历，当数据短时，补齐

            for (int j = 0; j < colCount; j++) {//每一列
                // 怎么把原来的sheet拿来过，赋值给这个Sheet,并把原来的sheet释放掉
                //遍历cell，把所有的cell都拷贝一下,从第0行第1个元素开始到最后一行最后一个元素
                //得到这一列的最后一行，如果小于最大行数，则得到其值，进行补齐
                //因为Excel是按行存储的，所以想得到一列的最大长度，只能查找，下面先采用折半一下
                //lastRow保存从哪一行开始检查
                int start = 9;//最少从第9行开始记录实验数据，前面几行，记录其他内容
                //首先拷贝第0到start行
                int k = start;
                for (; k <= rowCount; k++) {//跳过不是空行的
                    if (null == sheet.getRow(k).getCell(j)) {
                        break;
                    }
                }
                //拿到了k的值
                int lastRow = k - 1;
                //开始添数据
                for (; k <= rowCount + 1; k++) {
                    if (sheet.getRow(k) == null) {
                        sheet.createRow(k);
                        //System.out.println(fileName+":"+k+":column:"+j);
                    }
                    sheet.getRow(k).createCell(j).setCellValue(
                            sheet.getRow(lastRow).getCell(j).getNumericCellValue());
                }
                //每处理完一列，就写入一列的作法失败，必须全部完成后，再写入
                fileInputStream.close();
            }
            try (OutputStream outputStream = new FileOutputStream(fileName)) {
                wb.write(outputStream);//每处理完一列，就写入一列的作法失败，必须全部完成后，再写入
                wb.close();
                outputStream.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(D1FillUnbalancedata.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(D1FillUnbalancedata.class.getName()).log(Level.SEVERE, null, ex);
        }
        // System.gc();
        // System.gc();
    }

}
