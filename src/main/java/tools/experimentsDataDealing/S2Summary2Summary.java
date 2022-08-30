package tools.experimentsDataDealing;

import core.algorithm.AbstractAlgorithm;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xddf.usermodel.PresetColor;
import org.apache.poi.xddf.usermodel.XDDFColor;
import org.apache.poi.xddf.usermodel.XDDFLineProperties;
import org.apache.poi.xddf.usermodel.XDDFShapeProperties;
import org.apache.poi.xddf.usermodel.XDDFSolidFillProperties;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFScatterChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTChart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTitle;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTTx;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import core.problem.FactoryProblems;
import core.tools.MyMath.AverageAndStd;
import core.tools.office.Excel.J2Excel;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class S2Summary2Summary {
//把各种策略情况下的summary的数据汇总到一个SensitivityComparison文件中

    J2Excel j2Excel = new J2Excel();
    String targeFileName = "SensitivityComparison.xlsx";

    public static void main(String[] args) {
        String fileName = "E:\\backupFile\\Fangcloud of JSNUV2\\个人文件\\study\\papers\\20\\mistake\\ExclusionOperator\\paper_new";
        S2Summary2Summary ss = new S2Summary2Summary();
        // ss.beginSumary(fileName);//第一步处理原始数据
        //ss.dealComparison(fileName);//对数据进行加工
        //ss.outputLessOne(fileName);//适应值对种群规模的差分的差的绝对值小于1的情况输出
        ss.outputFitnessDifference(fileName);//适应值差的求取与标识，当差大于0，用1表示，当差小于0，用0表示
    }

    private void outputFitnessDifference(String filePathPart1) {//适应值差的求取与标识，当差大于0，用1表示，当差小于0，用0表示
        ExperimentSetup es = new ExperimentSetup();
        //把35个函数的4种算法下所有种群情况保存在HashMap数组中
        List<ThreeInteger>[] greater = new List[es.getPs().length];//对于所有种群2~9，10~360，共43个
        for (int i = 0; i < greater.length; i++) {
            greater[i] = new LinkedList<>();
        }
        //各函数下各算法下的种群列表
        for (int j = 0; j <= 34; j++) {
            String dstFilename = filePathPart1 + "\\F" + j //+ dimension
                    + "\\" + targeFileName;
            j2Excel.accessFile(dstFilename);
            Workbook wbdst = null;
            Sheet sheet = null;
            System.out.println("f" + j);
            try (FileInputStream dstInputStream = new FileInputStream(new File(dstFilename))) {
                wbdst = WorkbookFactory.create(dstInputStream);
                sheet = wbdst.getSheet("PSFitness");
                //读取列并赋值
                Row rowCGA = sheet.getRow(1), rowGAE1 = sheet.getRow(2), rowGAE2 = sheet.getRow(3), rowGAEA = sheet.getRow(4);
                for (int k = 1; k <= es.getPs().length; k++) {//控制列数
                    ThreeInteger ti = new ThreeInteger();
                    ti.GAS[0] = rowGAE1.getCell(k).getNumericCellValue() > rowCGA.getCell(k).getNumericCellValue() ? 1 : -1;
                    ti.GAS[1] = rowGAE2.getCell(k).getNumericCellValue() > rowCGA.getCell(k).getNumericCellValue() ? 1 : -1;
                    ti.GAS[2] = rowGAEA.getCell(k).getNumericCellValue() > rowCGA.getCell(k).getNumericCellValue() ? 1 : -1;
                    greater[k - 1].add(ti);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(S2Summary2Summary.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(S2Summary2Summary.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //对GAE1，GAE2，GAEA分别进行统计适应的PS的值
        List<Integer>[] psSuitable = new List[es.getKnowledgeName().length - 1];
        for (int i = 0; i < psSuitable.length; i++) {
            psSuitable[i] = new LinkedList<>();
        }
        for (List<ThreeInteger> greater1 : greater) {
            //共有es.getPs().length个种群，每个种群对应着一个ThreeInteger的链表
            ThreeInteger tem = new ThreeInteger();//对于这一种群的knowledge的正负性求和
            for (ThreeInteger ti : greater1) {
                for (int s = 0; s < 3; s++) {
                    tem.GAS[s] += ti.GAS[s];
                }
            }
            for (int i = 0; i <tem.GAS.length; i++) {
                psSuitable[i].add(tem.GAS[i]);
            }
        }
        for (int i = 0; i < es.getPs().length; i++) {
            System.out.print("\t"+es.getPs()[i]);
        }
        System.out.println("");
        for (int i = 0; i < psSuitable.length; i++) {
            System.out.print(es.getKnowledgeName()[i+1]);
            for (int j = 0; j < es.getPs().length; j++) {
                System.out.print("\t"+psSuitable[i].get(j));
            }
            System.out.println("");
        }
    }

    class ThreeInteger {

        //对应于3种算法的值，如对于CGA的占优与否，占优则值为1，否则为-1。 
        int[] GAS = new int[3];
    }

    public void outputLessOne(String filePathPart1) {//适应值对种群规模的差分的差的绝对值小于1的情况输出
        ExperimentSetup es = new ExperimentSetup();
        //把35个函数的4种算法下所有种群情况保存在HashMap数组中
        HashMap<String, List<Integer>>[] greater = new HashMap[35];
        //各函数下各算法下的种群列表
        for (int j = 0; j <= 34; j++) {
            //String dimension = (j > 10 && j <= 43) ? "\\D30" : "";
            String dstFilename = filePathPart1 + "\\F" + j //+ dimension
                    + "\\" + targeFileName;
            j2Excel.accessFile(dstFilename);
            Workbook wbdst = null;
            Sheet sheet = null;
            System.out.print("f" + j);
            try (FileInputStream dstInputStream = new FileInputStream(new File(dstFilename))) {
                HashMap<String, List<Integer>> psGreater = new HashMap<>();
                wbdst = WorkbookFactory.create(dstInputStream);
                sheet = wbdst.getSheet("PSFitness");
                //创建行并设置值
                int k = 11;
                int row = (sheet.getRow(k - 1) == null) ? k + 1 : k;
                int m = 1;//引用knowledge数组的下标
                for (; k < 11 + es.getKnowledgeName().length - 1; k++, row++, m++) {//控制行数,没有CGA行
                    System.out.print("\t" + es.getKnowledgeName()[m]);
                    psGreater.put(es.getKnowledgeName()[m], new LinkedList<>());
                    Row srcRow = sheet.getRow(row);//有的中间空一行，有的没空
                    int i = 2;
                    for (; i < es.getPs().length + 1; i++) {//控制列数
                        if (Math.abs(srcRow.getCell(i).getNumericCellValue()) < 1) {
                            System.out.print("," + es.getPs()[i - 2]);
                            psGreater.get(es.getKnowledgeName()[m]).add(es.getPs()[i - 2]);
                        }
                    }
                    System.out.println("");
                }
                greater[j] = psGreater;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(S2Summary2Summary.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(S2Summary2Summary.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //wordCount统计敏感性低于1的种群规模，作为推荐的种群规模
        HashMap<Integer, Integer> psCount = new HashMap<>();
        for (int i = 0; i < greater.length; i++) {
            greater[i].entrySet().forEach(ps1 -> {
                ps1.getValue().forEach(ps -> {
                    if (psCount.containsKey(ps)) {
                        psCount.put(ps, psCount.get(ps) + 1);
                    } else {
                        psCount.put(ps, 1);
                    }
                });
            });
        }
        psCount.entrySet().forEach((element) -> {
            System.out.println(element.getKey() + "\t" + element.getValue());
        });
    }

    public void beginSumary(String filePathPart1) {
        //把各种进化策略下的summary文件汇总到一个文件中：SensitivityComparison，以方便下一步进行画图与汇总
        ExperimentSetup es = new ExperimentSetup();
        for (int j = 0; j < 34; j++) {
            String dimension = (j > 10 && j <= 43) ? "\\D30" : "";
            //新建一个汇总文件
            String dstFilename = filePathPart1 + "\\F" + j + dimension + "\\" + targeFileName;
            j2Excel.accessFile(dstFilename);
            Workbook wbdst = null;
            Sheet sheetDst = null;
            try (FileInputStream dstInputStream = new FileInputStream(new File(dstFilename))) {
                wbdst = WorkbookFactory.create(dstInputStream);
                sheetDst = wbdst.createSheet("PSFitness");
                //创建行并设置值
                Row row = sheetDst.createRow(0);
                //第1行，是种群规模
                int currentRow = this.labelPS(row, sheetDst, 0, es, 1);//在第0行第1列开始标记种群规模
                //第2行至第5行是拷贝来的数据              
                //把拷贝来的数据从第1行开始放起
                for (int i = 0; i < es.getKnowledgeName().length; i++, currentRow++) {
                    //读取这一文件夹下的summary
                    String fileName = filePathPart1 + "\\F" + j + dimension + "\\" + es.getKnowledgeName()[i] + "\\summary.xlsx";
                    FileInputStream srcfileInputStream = new FileInputStream(new File(fileName));
                    XSSFWorkbook wbsrc = new XSSFWorkbook(srcfileInputStream);
                    Sheet sheetsrc = wbsrc.getSheet("alignedOptima");
                    //读取最后一行,并写入目标文件
                    int rowCount = sheetsrc.getLastRowNum();
                    Row srcRow = sheetsrc.getRow(rowCount);
                    Row dstRowow = sheetDst.createRow(currentRow);
                    dstRowow.createCell(0).setCellValue(es.getKnowledgeName()[i]);
                    for (int k = 1; k <= srcRow.getLastCellNum(); k++) {
                        dstRowow.createCell(k).setCellValue(srcRow.getCell(k - 1).getNumericCellValue());
                    }
                    wbsrc.close();
                }
                //开始计算差分
                //标识种群规模
                Row dstRowow = sheetDst.createRow(currentRow);
                currentRow = this.labelPS(dstRowow, sheetDst, currentRow, es, 2);

                //开始计算导数
                for (int k = 0; k < es.getKnowledgeName().length; k++, currentRow++) {//控制行
                    dstRowow = sheetDst.createRow(currentRow);
                    dstRowow.createCell(0).setCellValue("df/dPS" + es.getKnowledgeName()[k]);
                    for (int i = 2; i < sheetDst.getRow(1).getLastCellNum(); i++) {//控制列
                        Row srcRow = sheetDst.getRow(currentRow - 5);
                        if (i >= 10) {//每隔10代取一个值
                            dstRowow.createCell(i).setCellValue((srcRow.getCell(i).getNumericCellValue()
                                    - srcRow.getCell(i - 1).getNumericCellValue()) / 10);
                        } else {//2~10之间的种群规模
                            dstRowow.createCell(i).setCellValue((srcRow.getCell(i).getNumericCellValue()
                                    - srcRow.getCell(i - 1).getNumericCellValue()));
                        }
                    }
                }
                //计算导数的除数
                currentRow++;//中间空一行
                //标识种群规模
                dstRowow = sheetDst.createRow(currentRow);
                currentRow = this.labelPS(dstRowow, sheetDst, currentRow, es, 2);
                for (int k = 0; k < es.getKnowledgeName().length - 1; k++, currentRow++) {//控制行
                    dstRowow = sheetDst.createRow(currentRow);
                    dstRowow.createCell(0).setCellValue("df/dPS/Q" + es.getKnowledgeName()[k]);
                    // System.out.println(currentRow);
                    Row srcRow = sheetDst.getRow(currentRow - 5);
                    for (int i = 2; i < sheetDst.getRow(6).getLastCellNum(); i++) {//控制列,6表示是GA1的导数所在的行，其他行要除以这一行
                        dstRowow.createCell(i).setCellValue(
                                srcRow.getCell(i).getNumericCellValue() / sheetDst.getRow(6).getCell(i).getNumericCellValue());
                    }
                }
                dstInputStream.close();
                try (OutputStream outputStream = new FileOutputStream(new File(dstFilename))) {
                    wbdst.write(outputStream);
                    wbdst.close();
                    outputStream.close();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(S2Summary2Summary.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(S2Summary2Summary.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void dealComparison(String filePathPart1) {
        //1.敏感度求绝对值，并求绝对值的均值与方差
        ExperimentSetup es = new ExperimentSetup();
        for (int j = 0; j < 35; j++) {
            String dimension = (j > 10 && j <= 43) ? "\\D30" : "";
            //获得理想的最大适应值
            FactoryProblems.initProblem(j,AbstractAlgorithm.genecodeType);
            double stopFitness = FactoryProblems.currentProblem.getStopFitness()[0];

            String filename = filePathPart1 + "\\F" + j + dimension + "\\" + targeFileName;
            System.out.println(j);
            XSSFWorkbook wb = null;
            XSSFSheet srcSheet = null;
            try (FileInputStream dstInputStream = new FileInputStream(new File(filename))) {
                wb = new XSSFWorkbook(dstInputStream);
                srcSheet = wb.getSheet("PSFitness");
                //计算均值与方差
                calculateAverageAndStd(es, srcSheet);
                //直接在当前sheet中计算和画图
                //画适应值的散点图
                plotScatter4Fitness(es, srcSheet, j, stopFitness);
                //画敏感度的散点图
                plotScatter4Sensitivity(es, srcSheet, j, stopFitness);

                dstInputStream.close();
                try (OutputStream outputStream = new FileOutputStream(new File(filename))) {
                    wb.write(outputStream);
                    wb.close();
                    outputStream.close();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(S2Summary2Summary.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(S2Summary2Summary.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void calculateAverageAndStd(ExperimentSetup es, XSSFSheet srcSheet) {
        //求均值与方差
        int currentRow = 6;
        Row lstRowow = srcSheet.getRow(currentRow - 1);
        int colCount = lstRowow.getLastCellNum();
        lstRowow.createCell(colCount).setCellValue("average");
        lstRowow.createCell(colCount + 1).setCellValue("std");
        for (int k = 0; k < es.getKnowledgeName().length; k++) {//控制行数 
            List<Double> data = new LinkedList<>();
            Row srcRow = srcSheet.getRow(currentRow + k);
            int i = 2;
            for (; i < es.getPs().length + 2 - 1; i++) {//控制列数，求第6行及以下4行的敏感度值的绝对值
                data.add(srcRow.getCell(i).getNumericCellValue());
            }
            AverageAndStd avgStd = new AverageAndStd();
            double[] avgAndStd = avgStd.averageAndStd(data);
            srcRow.createCell(i++).setCellValue(avgAndStd[0]);
            srcRow.createCell(i).setCellValue(avgAndStd[1]);
        }
    }

    private void plotScatter4Fitness(ExperimentSetup es, XSSFSheet srcSheet, int j, double stopFitness) {
        Double[][] data = new Double[es.getKnowledgeName().length + 1][es.getPs().length];//最后一行是理想的最大适应值
        for (int k = 0; k < es.getKnowledgeName().length; k++) {//控制行数
            Row srcRow = srcSheet.getRow(k + 1);
            int i = 1;
            for (; i < es.getPs().length + 1; i++) {//控制列数
                data[k][i - 1] = srcRow.getCell(i).getNumericCellValue();
            }
        }
        plotScatter(srcSheet, j, "Best fitness", "Population size", data, stopFitness, es, true);
    }

    private void plotScatter4Sensitivity(ExperimentSetup es, XSSFSheet srcSheet, int j, double stopFitness) {
        //画敏感度的散点图
        Double[][] data = new Double[es.getKnowledgeName().length][es.getPs().length];
        int k = 11;
        int row = (srcSheet.getRow(k - 1) == null) ? k + 1 : k;
        for (; k < 11 + es.getKnowledgeName().length - 1; k++, row++) {//控制行数,没有CGA行
            Row srcRow = srcSheet.getRow(row);//有的中间空一行，有的没空
            int i = 2;
            for (; i < es.getPs().length + 1; i++) {//控制列数
                data[k - 11][i - 2] = srcRow.getCell(i).getNumericCellValue();
            }
        }
        plotScatter(srcSheet, j, "Sensitivity quotient", "Population size", data, stopFitness, es, false);
    }

    private int labelPS(Row dstRowow, Sheet sheetDst, int currentRow, ExperimentSetup es, int start) {
        //标识种群规模
        dstRowow = sheetDst.createRow(currentRow++);
        for (int i = 0; i < es.getPs().length; i++) {
            dstRowow.createCell(i + start).setCellValue(es.getPs()[i]);
        }
        return currentRow;
    }
 public static void plotScatter(XSSFSheet srcSheet, int problemNum, String xTitle, String yTitle, Double[][] data, double stopFitness, ExperimentSetup es, boolean drawStopFitness) {
        XSSFDrawing drawing = srcSheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 15);

        XSSFChart chart = drawing.createChart(anchor);
        //加上标题
        CTChart ctChart = chart.getCTChart();
        CTTitle title = ctChart.addNewTitle();
        CTTx tx = title.addNewTx();
        CTTextBody rich = tx.addNewRich();
        rich.addNewBodyPr();  // body properties must exist, but can be empty
        CTTextParagraph para = rich.addNewP();
        CTRegularTextRun r = para.addNewR();
        r.setT("f" + problemNum);
        //加入legend
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);
        XDDFValueAxis bottomAxis = chart.createValueAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle(yTitle); // https://stackoverflow.com/questions/32010765
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle(xTitle);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        XDDFDataSource<Integer> xs = XDDFDataSourcesFactory.fromArray(es.getPs());
        for (int k = 0; k < data[0].length; k++) {//理想最大值，方便从图中看出与其差距
            data[data.length - 1][k] = stopFitness;
        }
        List<XDDFNumericalDataSource<Double>> yss = new LinkedList<>();
        int endFor = drawStopFitness ? es.getKnowledgeName().length + 1 : es.getKnowledgeName().length - 1;
        for (int i = 0; i < endFor; i++) {
            yss.add(XDDFDataSourcesFactory.fromArray(data[i]));
        }
        XDDFScatterChartData graphData = (XDDFScatterChartData) chart.createData(ChartTypes.SCATTER, bottomAxis, leftAxis);
        endFor = drawStopFitness ? es.getKnowledgeName().length + 1 : es.getKnowledgeName().length - 1;
        String[] legendTitle = {"CGA", "GAE1", "GAE2", "GAEA"};//
        for (int i = 0; i < endFor; i++) {
            XDDFScatterChartData.Series series1 = (XDDFScatterChartData.Series) graphData.addSeries(xs, yss.get(i));
            if (i < es.getKnowledgeName().length) {
                int index = drawStopFitness ? i : i + 1;
                series1.setTitle(legendTitle[index], null);
            } else {
                series1.setTitle("Ideal maximum", null);
            }
            //series1.setSmooth(t);
        }

        // https://stackoverflow.com/questions/21855842
        // https://stackoverflow.com/questions/39636138
        chart.plot(graphData);
        // es.getKnowledgeName().length==4，所以下面有5行,最后一行是实际的最大适应值
        solidLineSeries(graphData, 0, PresetColor.CHARTREUSE);
        solidLineSeries(graphData, 1, PresetColor.TURQUOISE);
        solidLineSeries(graphData, 2, PresetColor.RED);
        if (drawStopFitness) {
            solidLineSeries(graphData, 3, PresetColor.BLUE);
            solidLineSeries(graphData, 4, PresetColor.CORAL);
        }
    }

    private static synchronized void solidLineSeries(XDDFChartData data, int index, PresetColor color) {
        XDDFSolidFillProperties fill = new XDDFSolidFillProperties(XDDFColor.from(color));
        XDDFLineProperties line = new XDDFLineProperties();
        line.setFillProperties(fill);
        XDDFChartData.Series series = data.getSeries().get(index);
        XDDFShapeProperties properties = series.getShapeProperties();
        if (properties == null) {
            properties = new XDDFShapeProperties();
        }
        properties.setLineProperties(line);
        series.setShapeProperties(properties);

    }
}
