package core.tools.xChart;

import java.util.List;
import org.knowm.xchart.demo.charts.ExampleChart;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 * @param <T>
 */
public interface MyExampleChart<T> extends ExampleChart {

    public void setxData(List<Double> xData);

    public void setyData(List<Double> yData);

    public void setxLastData(List<Double> DxLastData);

    public void setyLastData(List<Double> yLastData);

    public void setxBestData(List<Double> xBestData);

    public void setyBestData(List<Double> yBestData);
    
    public void setYData(List<Double> bestFitness);
}
