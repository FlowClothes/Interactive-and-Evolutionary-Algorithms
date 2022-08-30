package core.tools.xChart;

import core.controllers.ControlParameters;
import core.entrance.MainCmdRuning;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.demo.charts.RealtimeExampleChart;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;
import core.problem.FactoryProblems;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class MyRealtimeChart01 implements MyExampleChart<XYChart>, RealtimeExampleChart {

    private XYChart xyChart;
    public String SERIES_NAME = "population";
    public String SERIES_BACKGROUND = "Optimzation Function";

    public static void main(String[] args) {

        // Setup the panel
        final MyRealtimeChart01 realtimeChart01 = new MyRealtimeChart01();
        realtimeChart01.go();
    }

    public void go() {
        final SwingWrapper<XYChart> swingWrapper = new SwingWrapper<XYChart>(getChart());
        swingWrapper.displayChart();
        // Simulate a data feed
        TimerTask chartUpdaterTask = new TimerTask() {

            @Override
            public void run() {

                updateData();

                javax.swing.SwingUtilities.invokeLater(
                        new Runnable() {
                    @Override
                    public void run() {

                        swingWrapper.repaintChart();
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(chartUpdaterTask, 0, 500);
    }

    @Override
    public XYChart getChart() {
        mainCmdRuning.callMeByChart(this);
        xyChart = mainCmdRuning.getXYChart();
        //背景曲线
        XYSeries series = xyChart.addSeries(SERIES_NAME,
                (List) FactoryProblems.currentProblem.getXListData().get(0),
                (List) FactoryProblems.currentProblem.getYListData().get(0));
        series.setLineStyle(SeriesLines.DOT_DOT);
        series.setMarker(SeriesMarkers.NONE);
        series.setMarkerColor(Color.GREEN);

        //下面是动态数据
        //显示当前种群
        mainCmdRuning.getPopulationData();
        series = xyChart.addSeries(FactoryProblems.currentProblem.getName(), xData, yData);
        series.setLineStyle(SeriesLines.NONE);
        series.setMarker(SeriesMarkers.CIRCLE);
        series.setMarkerColor(Color.CYAN);
        //显示上一代种群
        if (yLastData.isEmpty()) {
            yLastData.add(0.0);
            xLastData.add(0.0);
        }
//        series = xyChart.addSeries(ControlParameters.currentProblem.getName() + "last", xLastData, yLastData);
//        series.setLineStyle(SeriesLines.NONE);
//        series.setMarker(SeriesMarkers.DIAMOND);
//        series.setMarkerColor(Color.BLUE);
//显示最优个体发展变化 
        if (yBestData.isEmpty()) {
            yBestData.add(0.0);
            xBestData.add(0.0);
        }
        series = xyChart.addSeries(FactoryProblems.currentProblem.getName() + "best", xBestData, yBestData);
        series.setLineStyle(SeriesLines.NONE);
        series.setMarker(SeriesMarkers.SQUARE);
        series.setMarkerColor(Color.RED);
        return xyChart;
    }

    MainCmdRuning mainCmdRuning = new MainCmdRuning();

    @Override
    public void updateData() {
        mainCmdRuning.getPopulationData();
        xyChart.updateXYSeries(FactoryProblems.currentProblem.getName(), xData, yData, null);
        //xyChart.updateXYSeries(ControlParameters.currentProblem.getName() + "last", xLastData, yLastData, null);
        xyChart.updateXYSeries(FactoryProblems.currentProblem.getName() + "best", xBestData, yBestData, null);
    }

    @Override
    public String getExampleChartName() {

        return getClass().getSimpleName() + ControlParameters.dynamic;
    }

    private List<Double> xData = new LinkedList<>();
    private List<Double> yData = new LinkedList<>();

    private List<Double> xLastData = new LinkedList<>();
    private List<Double> yLastData = new LinkedList<>();
    private List<Double> xBestData = new LinkedList<>();
    private List<Double> yBestData = new LinkedList<>();

    @Override
    public void setxLastData(List<Double> xLastData) {
        this.xLastData = xLastData;
    }

    @Override
    public void setyLastData(List<Double> yLastData) {
        this.yLastData = yLastData;
    }

    @Override
    public void setxData(List<Double> xData) {
        this.xData = xData;
    }

    @Override
    public void setyData(List<Double> yData) {
        this.yData = yData;
    }

    /**
     * @param xBestData the xBestData to set
     */
    @Override
    public void setxBestData(List<Double> xBestData) {
        this.xBestData = xBestData;
    }

    /**
     * @param yBestData the yBestData to set
     */
    @Override
    public void setyBestData(List<Double> yBestData) {
        this.yBestData = yBestData;
    }

    @Override
    public void setYData(List<Double> bestFitness) {
    }
}
