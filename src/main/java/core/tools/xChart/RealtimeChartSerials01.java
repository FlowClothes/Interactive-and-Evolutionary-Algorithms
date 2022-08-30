package core.tools.xChart;

import core.entrance.MainCmdRuning;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.demo.charts.RealtimeExampleChart;
import org.knowm.xchart.style.Styler;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class RealtimeChartSerials01 implements MyExampleChart<XYChart>, RealtimeExampleChart {

    private XYChart xyChart;

    private List<Double> yData = new LinkedList<>();
    public static final String SERIES_NAME = "Best fitness";

    public static void main(String[] args) {

        // Setup the panel
        final RealtimeChartSerials01 realtimeChart01 = new RealtimeChartSerials01();
        realtimeChart01.go();
    }

    private void go() {
        final SwingWrapper<XYChart> swingWrapper = new SwingWrapper<>(getChart());
        swingWrapper.displayChart();

        // Simulate a data feed
        TimerTask chartUpdaterTask
                = new TimerTask() {

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
        xyChart = new XYChartBuilder()
                .width(500)
                .height(400)
                .theme(Styler.ChartTheme.Matlab)
                .title("Dynamic fitness")
                .build();
        if (yData.isEmpty()) {
            yData.add(0.0);
        }
        xyChart.addSeries(SERIES_NAME, null, yData);
        return xyChart;
    }

    MainCmdRuning mainCmdRuning = new MainCmdRuning();

    @Override
    public void updateData() {
        mainCmdRuning.getPopulationData();
        while (yData.size() > 200) {
            yData.remove(0);
        }
        xyChart.updateXYSeries(SERIES_NAME, null, yData, null);
    }

    @Override
    public String getExampleChartName() {

        return getClass().getSimpleName() + " - Real-time XY Chart";
    }

    @Override
    public void setYData(List<Double> ydata) {
        this.yData = ydata;
    }

    @Override
    public void setxData(List<Double> xData) {
    }

    @Override
    public void setyData(List<Double> yData) {
    }

    @Override
    public void setxLastData(List<Double> DxLastData) {
    }

    @Override
    public void setyLastData(List<Double> yLastData) {
    }

    @Override
    public void setxBestData(List<Double> xBestData) {
    }

    @Override
    public void setyBestData(List<Double> yBestData) {
    }
}
