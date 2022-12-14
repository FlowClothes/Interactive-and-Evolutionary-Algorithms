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
 * @author ????????? HAO Guo-Sheng
 */
public class S2Summary2Summary {
//???????????????????????????summary????????????????????????SensitivityComparison?????????

    J2Excel j2Excel = new J2Excel();
    String targeFileName = "SensitivityComparison.xlsx";

    public static void main(String[] args) {
        String fileName = "E:\\backupFile\\Fangcloud of JSNUV2\\????????????\\study\\papers\\20\\mistake\\ExclusionOperator\\paper_new";
        S2Summary2Summary ss = new S2Summary2Summary();
        // ss.beginSumary(fileName);//???????????????????????????
        //ss.dealComparison(fileName);//?????????????????????
        //ss.outputLessOne(fileName);//?????????????????????????????????????????????????????????1???????????????
        ss.outputFitnessDifference(fileName);//?????????????????????????????????????????????0??????1?????????????????????0??????0??????
    }

    private void outputFitnessDifference(String filePathPart1) {//?????????????????????????????????????????????0??????1?????????????????????0??????0??????
        ExperimentSetup es = new ExperimentSetup();
        //???35????????????4???????????????????????????????????????HashMap?????????
        List<ThreeInteger>[] greater = new List[es.getPs().length];//??????????????????2~9???10~360??????43???
        for (int i = 0; i < greater.length; i++) {
            greater[i] = new LinkedList<>();
        }
        //???????????????????????????????????????
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
                //??????????????????
                Row rowCGA = sheet.getRow(1), rowGAE1 = sheet.getRow(2), rowGAE2 = sheet.getRow(3), rowGAEA = sheet.getRow(4);
                for (int k = 1; k <= es.getPs().length; k++) {//????????????
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
        //???GAE1???GAE2???GAEA???????????????????????????PS??????
        List<Integer>[] psSuitable = new List[es.getKnowledgeName().length - 1];
        for (int i = 0; i < psSuitable.length; i++) {
            psSuitable[i] = new LinkedList<>();
        }
        for (List<ThreeInteger> greater1 : greater) {
            //??????es.getPs().length???????????????????????????????????????ThreeInteger?????????
            ThreeInteger tem = new ThreeInteger();//?????????????????????knowledge??????????????????
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

        //?????????3???????????????????????????CGA?????????????????????????????????1????????????-1??? 
        int[] GAS = new int[3];
    }

    public void outputLessOne(String filePathPart1) {//?????????????????????????????????????????????????????????1???????????????
        ExperimentSetup es = new ExperimentSetup();
        //???35????????????4???????????????????????????????????????HashMap?????????
        HashMap<String, List<Integer>>[] greater = new HashMap[35];
        //???????????????????????????????????????
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
                //?????????????????????
                int k = 11;
                int row = (sheet.getRow(k - 1) == null) ? k + 1 : k;
                int m = 1;//??????knowledge???????????????
                for (; k < 11 + es.getKnowledgeName().length - 1; k++, row++, m++) {//????????????,??????CGA???
                    System.out.print("\t" + es.getKnowledgeName()[m]);
                    psGreater.put(es.getKnowledgeName()[m], new LinkedList<>());
                    Row srcRow = sheet.getRow(row);//????????????????????????????????????
                    int i = 2;
                    for (; i < es.getPs().length + 1; i++) {//????????????
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
        //wordCount?????????????????????1?????????????????????????????????????????????
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
        //???????????????????????????summary?????????????????????????????????SensitivityComparison??????????????????????????????????????????
        ExperimentSetup es = new ExperimentSetup();
        for (int j = 0; j < 34; j++) {
            String dimension = (j > 10 && j <= 43) ? "\\D30" : "";
            //????????????????????????
            String dstFilename = filePathPart1 + "\\F" + j + dimension + "\\" + targeFileName;
            j2Excel.accessFile(dstFilename);
            Workbook wbdst = null;
            Sheet sheetDst = null;
            try (FileInputStream dstInputStream = new FileInputStream(new File(dstFilename))) {
                wbdst = WorkbookFactory.create(dstInputStream);
                sheetDst = wbdst.createSheet("PSFitness");
                //?????????????????????
                Row row = sheetDst.createRow(0);
                //???1?????????????????????
                int currentRow = this.labelPS(row, sheetDst, 0, es, 1);//??????0??????1???????????????????????????
                //???2?????????5????????????????????????              
                //???????????????????????????1???????????????
                for (int i = 0; i < es.getKnowledgeName().length; i++, currentRow++) {
                    //???????????????????????????summary
                    String fileName = filePathPart1 + "\\F" + j + dimension + "\\" + es.getKnowledgeName()[i] + "\\summary.xlsx";
                    FileInputStream srcfileInputStream = new FileInputStream(new File(fileName));
                    XSSFWorkbook wbsrc = new XSSFWorkbook(srcfileInputStream);
                    Sheet sheetsrc = wbsrc.getSheet("alignedOptima");
                    //??????????????????,?????????????????????
                    int rowCount = sheetsrc.getLastRowNum();
                    Row srcRow = sheetsrc.getRow(rowCount);
                    Row dstRowow = sheetDst.createRow(currentRow);
                    dstRowow.createCell(0).setCellValue(es.getKnowledgeName()[i]);
                    for (int k = 1; k <= srcRow.getLastCellNum(); k++) {
                        dstRowow.createCell(k).setCellValue(srcRow.getCell(k - 1).getNumericCellValue());
                    }
                    wbsrc.close();
                }
                //??????????????????
                //??????????????????
                Row dstRowow = sheetDst.createRow(currentRow);
                currentRow = this.labelPS(dstRowow, sheetDst, currentRow, es, 2);

                //??????????????????
                for (int k = 0; k < es.getKnowledgeName().length; k++, currentRow++) {//?????????
                    dstRowow = sheetDst.createRow(currentRow);
                    dstRowow.createCell(0).setCellValue("df/dPS" + es.getKnowledgeName()[k]);
                    for (int i = 2; i < sheetDst.getRow(1).getLastCellNum(); i++) {//?????????
                        Row srcRow = sheetDst.getRow(currentRow - 5);
                        if (i >= 10) {//??????10???????????????
                            dstRowow.createCell(i).setCellValue((srcRow.getCell(i).getNumericCellValue()
                                    - srcRow.getCell(i - 1).getNumericCellValue()) / 10);
                        } else {//2~10?????????????????????
                            dstRowow.createCell(i).setCellValue((srcRow.getCell(i).getNumericCellValue()
                                    - srcRow.getCell(i - 1).getNumericCellValue()));
                        }
                    }
                }
                //?????????????????????
                currentRow++;//???????????????
                //??????????????????
                dstRowow = sheetDst.createRow(currentRow);
                currentRow = this.labelPS(dstRowow, sheetDst, currentRow, es, 2);
                for (int k = 0; k < es.getKnowledgeName().length - 1; k++, currentRow++) {//?????????
                    dstRowow = sheetDst.createRow(currentRow);
                    dstRowow.createCell(0).setCellValue("df/dPS/Q" + es.getKnowledgeName()[k]);
                    // System.out.println(currentRow);
                    Row srcRow = sheetDst.getRow(currentRow - 5);
                    for (int i = 2; i < sheetDst.getRow(6).getLastCellNum(); i++) {//?????????,6?????????GA1???????????????????????????????????????????????????
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
        //1.?????????????????????????????????????????????????????????
        ExperimentSetup es = new ExperimentSetup();
        for (int j = 0; j < 35; j++) {
            String dimension = (j > 10 && j <= 43) ? "\\D30" : "";
            //??????????????????????????????
            FactoryProblems.initProblem(j,AbstractAlgorithm.genecodeType);
            double stopFitness = FactoryProblems.currentProblem.getStopFitness()[0];

            String filename = filePathPart1 + "\\F" + j + dimension + "\\" + targeFileName;
            System.out.println(j);
            XSSFWorkbook wb = null;
            XSSFSheet srcSheet = null;
            try (FileInputStream dstInputStream = new FileInputStream(new File(filename))) {
                wb = new XSSFWorkbook(dstInputStream);
                srcSheet = wb.getSheet("PSFitness");
                //?????????????????????
                calculateAverageAndStd(es, srcSheet);
                //???????????????sheet??????????????????
                //????????????????????????
                plotScatter4Fitness(es, srcSheet, j, stopFitness);
                //????????????????????????
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
        //??????????????????
        int currentRow = 6;
        Row lstRowow = srcSheet.getRow(currentRow - 1);
        int colCount = lstRowow.getLastCellNum();
        lstRowow.createCell(colCount).setCellValue("average");
        lstRowow.createCell(colCount + 1).setCellValue("std");
        for (int k = 0; k < es.getKnowledgeName().length; k++) {//???????????? 
            List<Double> data = new LinkedList<>();
            Row srcRow = srcSheet.getRow(currentRow + k);
            int i = 2;
            for (; i < es.getPs().length + 2 - 1; i++) {//?????????????????????6????????????4??????????????????????????????
                data.add(srcRow.getCell(i).getNumericCellValue());
            }
            AverageAndStd avgStd = new AverageAndStd();
            double[] avgAndStd = avgStd.averageAndStd(data);
            srcRow.createCell(i++).setCellValue(avgAndStd[0]);
            srcRow.createCell(i).setCellValue(avgAndStd[1]);
        }
    }

    private void plotScatter4Fitness(ExperimentSetup es, XSSFSheet srcSheet, int j, double stopFitness) {
        Double[][] data = new Double[es.getKnowledgeName().length + 1][es.getPs().length];//???????????????????????????????????????
        for (int k = 0; k < es.getKnowledgeName().length; k++) {//????????????
            Row srcRow = srcSheet.getRow(k + 1);
            int i = 1;
            for (; i < es.getPs().length + 1; i++) {//????????????
                data[k][i - 1] = srcRow.getCell(i).getNumericCellValue();
            }
        }
        plotScatter(srcSheet, j, "Best fitness", "Population size", data, stopFitness, es, true);
    }

    private void plotScatter4Sensitivity(ExperimentSetup es, XSSFSheet srcSheet, int j, double stopFitness) {
        //????????????????????????
        Double[][] data = new Double[es.getKnowledgeName().length][es.getPs().length];
        int k = 11;
        int row = (srcSheet.getRow(k - 1) == null) ? k + 1 : k;
        for (; k < 11 + es.getKnowledgeName().length - 1; k++, row++) {//????????????,??????CGA???
            Row srcRow = srcSheet.getRow(row);//????????????????????????????????????
            int i = 2;
            for (; i < es.getPs().length + 1; i++) {//????????????
                data[k - 11][i - 2] = srcRow.getCell(i).getNumericCellValue();
            }
        }
        plotScatter(srcSheet, j, "Sensitivity quotient", "Population size", data, stopFitness, es, false);
    }

    private int labelPS(Row dstRowow, Sheet sheetDst, int currentRow, ExperimentSetup es, int start) {
        //??????????????????
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
        //????????????
        CTChart ctChart = chart.getCTChart();
        CTTitle title = ctChart.addNewTitle();
        CTTx tx = title.addNewTx();
        CTTextBody rich = tx.addNewRich();
        rich.addNewBodyPr();  // body properties must exist, but can be empty
        CTTextParagraph para = rich.addNewP();
        CTRegularTextRun r = para.addNewR();
        r.setT("f" + problemNum);
        //??????legend
        XDDFChartLegend legend = chart.getOrAddLegend();
        legend.setPosition(LegendPosition.TOP_RIGHT);
        XDDFValueAxis bottomAxis = chart.createValueAxis(AxisPosition.BOTTOM);
        bottomAxis.setTitle(yTitle); // https://stackoverflow.com/questions/32010765
        XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
        leftAxis.setTitle(xTitle);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        XDDFDataSource<Integer> xs = XDDFDataSourcesFactory.fromArray(es.getPs());
        for (int k = 0; k < data[0].length; k++) {//???????????????????????????????????????????????????
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
        // es.getKnowledgeName().length==4??????????????????5???,???????????????????????????????????????
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
