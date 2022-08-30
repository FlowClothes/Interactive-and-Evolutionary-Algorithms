package tools.experimentsDataDealing.steps;

import core.tools.office.Excel.J2Excel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hgs
 */
public class ReadFromTxt2Excel {

    //从txt文件中读出数据，写到excel文件中
    //这个txt文件是用C语言写的遗传算法程序跑的代码
    public static void main(String[] args) {
        /////////////////////这几个参数需要设置//////////////////////
        int populationSize = 350, maxLine = 10000/populationSize;
        String excelFileName = "D:\\test\\CppApplication_1\\D_350.xlsx";
        String txtFilename = "D:\\test\\CppApplication_1\\a.txt";
        //////////////////////////////////////////////////
        //从txt文件中读数据
        File file = new File(txtFilename);
        J2Excel j2Excel = new J2Excel();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            Map<Integer, List<Object>> excelData = new TreeMap<>();
            int start = 0;
            while ((line = br.readLine()) != null) {
                if (line.contains("This is the end of")) {//一次实验结束
                    //把剩余的未补充的数据补充完整,用最后一个值补充
                    Object fitnessValue = excelData.get(start - 1).get(excelData.get(start - 1).size() - 1);
                    for (; start < maxLine; start++) {
                        if (excelData.get(start) == null) {//这一行还不存在
                            excelData.put(start, new LinkedList<>());
                        }
                        excelData.get(start).add(fitnessValue);
                    }
                    start = 0;
                } else {//正在读取当前的实验结果
                    if (excelData.get(start) == null) {//这一行还不存在
                        List<Object> fitnessValue = new LinkedList<>();
                        excelData.put(start, fitnessValue);
                    }
                    excelData.get(start++).add(Double.parseDouble(line));
                }
            }
            j2Excel.write2Excel(excelFileName, String.valueOf(populationSize), excelData);
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadFromTxt2Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadFromTxt2Excel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
