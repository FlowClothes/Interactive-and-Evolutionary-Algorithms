/*
 * 在绘制实验图像前，为了具有可比性，针对不同的种群规模，要把评价次数作为自变量，比较相同评价次数所获得的适应值大小。但是不同种群规模，进化结束代数不同，因此需要在评价次数上，把不同种群规模的评价对齐。如种群规模为10，进化代数为1000；而种群规模为100时，进化代数为100，这时就需要把种群规模为100的100代数据对齐到1000代上，从而与种群规模为10的进行对比。为此，把种群规模为100的数据每代都复制10份，成阶梯状上长升
思路是：
对同一个文件，从一个sheet中读出来，再写到另一个sheet中进去
  for(m=0;m<k;m++){
    cell原(i)(k)-->cell新(i*k+m)(k)
  }
 */
package tools.experimentsDataDealing.steps;

import tools.experimentsDataDealing.ExperimentSetup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
public class D5AlignData4PS {

    public static void main(String[] args) {
        D5AlignData4PS dadps = new D5AlignData4PS();
        dadps.alignData4PS("E:\\tem\\myEAs\\data\\F0\\binaryCode");
    }

    public void alignData4PS(String fileName) {
        ExperimentSetup es = new ExperimentSetup();
        //String fileName = "E:\\tem\\myEAs\\experimentsData\\F0\\F0-K0\\summary.xlsx";
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(fileName + "\\summary.xlsx");
            Workbook wb = WorkbookFactory.create(inputStream);
            //找到以数字命名的sheet,对每一个sheet都处理
            Sheet sheetSrc = wb.getSheet("fitness");
            Sheet sheetDst = wb.createSheet("alignedOptima");
            //获取其行数
            int rowCount = sheetSrc.getLastRowNum();
            //System.out.println("rowCount"+rowCount);
            for (int i = 8; i <= rowCount; i++) {
                sheetDst.createRow(i);//准备好所有的行
            }
            //获取其列数
            int colCount = sheetSrc.getRow(0).getLastCellNum();
            for (int j = 0; j < colCount; j++) {//列循环
                int rowNumber =8,//目标文件的行数
                        i = 8;//源文的行数，行循环，从第8行开始, 前面几行是其他数据 
                for (; i <= rowCount && sheetSrc.getRow(i).getCell(j) != null; i++) {
                    //取出这行数据
                    double value = sheetSrc.getRow(i).getCell(j).getNumericCellValue();
                    //这行数据要复制k份,k的值取决于与最小种群规模2的关系：
                    //当ps%2==0时，即偶数，则复制ps/2次；
                    //当ps%2==1时，即奇数，则第i行复制ps/2次，第i+1行复制ps/2+1次，为此采用判断第i行的奇偶性决定复次数
                    for (int k = 0; k < es.getPs()[j] / 2; k++) {
                        if (rowNumber > rowCount || sheetDst.getRow(rowNumber) == null) {
                            i = rowCount;//强制退出循环
                            break;
                        }
                        sheetDst.getRow(rowNumber).createCell(j).setCellValue(value);
                        rowNumber++;
                        // 进一步判断，如果ps是奇数，且所在行数也是奇数，则再复制一次
                        if (es.getPs()[j] % 2 == 1 && i % 2 == 1 && sheetDst.getRow(rowNumber) != null) {
                            sheetDst.getRow(rowNumber).createCell(j).setCellValue(value);
                            rowNumber++;
                        }
//                        } else {
//                            k = D1FillUnbalancedata.ps[j];//跳出循环
//                        }
                    }
                }

                for (; rowNumber <= rowCount; rowNumber++) {//没有添满数据
                    if (null == sheetDst.getRow(rowNumber) || sheetSrc.getRow(i - 1).getCell(j) == null) {
                        System.out.println("bad");
                    }
                    if (sheetDst.getRow(rowNumber) == null || sheetDst.getRow(rowNumber).createCell(j) == null || sheetSrc.getRow(i - 1).getCell(j) == null) {
                        System.out.println("bad");
                    } else {
                        sheetDst.getRow(rowNumber).createCell(j).setCellValue(sheetSrc.getRow(i - 1).getCell(j).getNumericCellValue());
                    }
                }
            }
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(fileName + "\\summary.xlsx");
            wb.write(outputStream);
            wb.close();
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(D5AlignData4PS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(D5AlignData4PS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
