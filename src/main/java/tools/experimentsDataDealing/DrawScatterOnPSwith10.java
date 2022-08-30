package tools.experimentsDataDealing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
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
import core.tools.office.Excel.J2Excel;

/**
 *
 * @author hgs07
 */
public class DrawScatterOnPSwith10 {

    J2Excel j2Excel = new J2Excel();

    public static void main(String[] args) {
        String filePath = "E:\\BaiduNetdiskDownload\\experimentsData\\EAs\\ExperimentsDataNew\\all";
        DrawScatterOnPSwith10 dsops = new DrawScatterOnPSwith10();
        dsops.draw(filePath);

    }

    public void draw(String filePathPart1) {
        String dstFilename = filePathPart1 + "\\plot.xlsx";
        j2Excel.accessFile(dstFilename);
        try {
            FileInputStream plotXlsFile = new FileInputStream(new File(dstFilename));
            XSSFWorkbook wbdst = new XSSFWorkbook(plotXlsFile);
            //在上面这个excel文件中画图
            for (int j = 0; j < 35; j++) {//共35个函数
                XSSFSheet dstSheet = wbdst.createSheet(String.valueOf(j));
                //各个图画到对应编号的sheet中
                String[] filenames = new String[]{filePathPart1 + "\\PS_10_F" + j + ".xlsx",
                    filePathPart1 + "\\CGAPS_10_F" + j + ".xlsx"};
                Double[][] data = new Double[2][];
                System.out.println(j);
                int dataLength = 0;
                for (int i = 0; i < filenames.length; i++) {
                    int k = 7;
                    //确定数组的列数
                    FileInputStream srcName = new FileInputStream(new File(filenames[i]));
                    XSSFWorkbook wb = new XSSFWorkbook(srcName);
                    XSSFSheet srcSheet = wb.getSheet("10");
                    //画适应值的散点图 
                    int rowCount = srcSheet.getLastRowNum();//行数
                    if (i == 0) {
                        dataLength = rowCount - k;
                    } else {
                        dataLength = Math.min(dataLength, rowCount - k);
                    }
                    wb.close();
                    srcName.close();
                }
                for (int i = 0; i < filenames.length; i++) {
                    FileInputStream srcName = new FileInputStream(new File(filenames[i]));
                    XSSFWorkbook wb = new XSSFWorkbook(srcName);
                    XSSFSheet srcSheet = wb.getSheet("10");
                    //画适应值的散点图 

                    int rowCount = srcSheet.getLastRowNum();//行数
                    int columCount = srcSheet.getRow(0).getLastCellNum();//列数
                    data[i] = new Double[dataLength];
                    int k = 7;
                    int row = 0;
                    for (; k < rowCount && row < dataLength; k++, row++) {//控制行数
                        Row srcRow = srcSheet.getRow(k);//从第8行开始读起
                        //读公式的数据有问题，因此，直接求平均值
                        double avg = 0;
                        for (int l = 0; l < columCount - 2; l++) {//控制列数
                            avg += srcRow.getCell(l).getNumericCellValue();
                        }
                        data[i][row] = avg / (columCount - 1);
                    }
                    srcName.close();
                }
                plotScatter(dstSheet, j, "Found best fitness", "Generation", data);
            }
            try (OutputStream outputStream = new FileOutputStream(new File(dstFilename))) {
                wbdst.write(outputStream);
                wbdst.close();
                outputStream.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DrawScatterOnPSwith10.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DrawScatterOnPSwith10.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void plotScatter(XSSFSheet srcSheet, int problemNum, String xTitle, String yTitle, Double[][] data) {
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
        Integer[] horizental = new Integer[data[0].length];
        for (int i = 0; i < horizental.length; i++) {
            horizental[i] = i;
        }
        XDDFDataSource<Integer> xs = XDDFDataSourcesFactory.fromArray(horizental);
        List<XDDFNumericalDataSource<Double>> yss = new LinkedList<>();
        for (int i = 0; i < data.length; i++) {
            yss.add(XDDFDataSourcesFactory.fromArray(data[i]));
        }
        XDDFScatterChartData graphData = (XDDFScatterChartData) chart.createData(ChartTypes.SCATTER, bottomAxis, leftAxis);
        String[] legendTitle = {"GAEA", "CGA"};//
        for (int i = 0; i < data.length; i++) {
            XDDFScatterChartData.Series series1 = (XDDFScatterChartData.Series) graphData.addSeries(xs, yss.get(i));
            series1.setTitle(legendTitle[i], null);
        }

        // https://stackoverflow.com/questions/21855842
        // https://stackoverflow.com/questions/39636138
        chart.plot(graphData);
        // es.getKnowledgeName().length==4，所以下面有5行,最后一行是实际的最大适应值
        solidLineSeries(graphData, 0, PresetColor.BLUE);
        solidLineSeries(graphData, 1, PresetColor.RED);

    }

    private void solidLineSeries(XDDFChartData data, int index, PresetColor color) {
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
/*
先拷贝文件到指定文件夹
 for ($x=0;$x -lt 35;$x++){
 if($x -gt 10){
 copy E:\BaiduNetdiskDownload\experimentsData\EAs\ExperimentsDataNew\F$x\D30\NoReappearOnSearchSpaceHashMapAndPopulation1\PS_10_*.xlsx  "E:\BaiduNetdiskDownload\experimentsData\EAs\ExperimentsDataNew\all\PS_10_F$X.xlsx"
 }else{
  copy E:\BaiduNetdiskDownload\experimentsData\EAs\ExperimentsDataNew\F$x\NoReappearOnSearchSpaceHashMapAndPopulation1\PS_10_*.xlsx  "E:\BaiduNetdiskDownload\experimentsData\EAs\ExperimentsDataNew\all\PS_10_F$X.xlsx"
 }
 }
 */

 /*
试验起见，可以跑跑下面的程序
for ($x=0;$x -lt 35;$x++){
 if($x -gt 10){
 echo "good"
 }else{
 echo "bad"
 }
 }
 */
