/*
 * 本基类只为标量的适应值服务,并没有考虑矢量的适应值问题
 */
package core.problem;

import core.algorithm.Algorithm;
import core.problem.DecisionVariables.AbstractDecisionVariable;
import core.problem.DecisionVariables.BinarycodeDecisionVariable;
import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.DecisionVariables.GenecodeType;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 * @param <T>
 */
public class Problem<T extends AbstractDecisionVariable> {

    protected Population population;
    protected double[] stopFitness;//终止的适应值
    protected Individual inputedIndividual;//传入的个体，由问题返回适应值
    protected int individualLength;//个体长度
    protected double maxFitness[];//最大适应值，对于最大化的问题，初始设置为一个很小的值；对于最小化问题，则设置为一个很大的值，进化开始化，这里边保存最大适应值
    protected double variableProperties[][];//第1维是变量的索引，第2维是该变量取值的上下限及精度，[i][0]是上限，[i][1]是下限，[i][2]是精度
    protected int variableSplit[];//变量分隔数组；其格式是：[0，a1,a2,...,an]其中，a1表示第1个变量的串长，a2表示第2个变量的串开始位置是a1,终止位置是a2,依此类推，第n的变量的串开始位置是an-1,终止位置是an.这是根据variableProperties[][]计算出来的。
    protected Algorithm algorithm;
    protected List<List<Double>> xListData = new LinkedList<>();
    protected List<List<Double>> yListData = new LinkedList<>();
    protected int dimension;
    protected AbstractDecisionVariable abstractDecisionVariable;
    protected double volume = 0, maximumDistance = 0;
    private GenecodeType genecodeType;
//前者是搜索空间的体积，后者是搜索空间中最远的两个点的距离
    //两者虽然不同，但却成线性相关的，因此只用其一即可
    //从计算量安全来说，后者的更安全，因为是求和，而前者是计算积，有可能越界

    public Problem init(int dimenstion) {
        switch (getGenecodeType()) {
            case DOUBLECODE:
                this.setDecisionVariable(new DoubleDecisionVariable());
                break;
            case BINARYCODE:
                this.setDecisionVariable(new BinarycodeDecisionVariable());
                break;
        }
        if (xListData.isEmpty()) {
            xListData.add(new LinkedList<>());
            yListData.add(new LinkedList<>());
        }
        this.dimension = dimenstion;
        return this;
    }

    public int getGsuCount() {
        return variableProperties.length;
    }

    public double getVolume() {
        if (volume == 0) {//还没求取
            volume = 1;
            switch (FactoryProblems.currentProblem.abstractDecisionVariable.getGenecodeType()) {
                case DOUBLECODE:
                    for (double[] variablePropertie : variableProperties) {
                        volume *= Math.pow(variablePropertie[0] - variablePropertie[1], 1.0 / variableProperties.length);
                    }
                    break;

                case STRINGCODE:
                    for (int i = 0; i < getGsuCount(); i++) {
                        volume *= Math.pow(getCodeScope().length(), 1 / getGsuCount());
                    }
                    break;
                case AFFINEFACECODE:
                    System.out.println("还不支持");
                    break;
                case BINARYCODE:
                    System.out.println("还不支持");
                    break;
                case NORMALFACECODE:
                    System.out.println("还不支持");
                    break;
            }
        }
        return volume;
    }

    public double getMaximumDistance() {
        if (0.0 == maximumDistance) {//还没求取
            maximumDistance = 1.0;
            switch (FactoryProblems.currentProblem.abstractDecisionVariable.getGenecodeType()) {
                case DOUBLECODE:
                    for (double[] variablePropertie : variableProperties) {
                        maximumDistance += Math.pow(variablePropertie[0] - variablePropertie[1], 2);
                    }
                    break;
                case STRINGCODE:
                    for (int i = 0; i < getGsuCount(); i++) {
                        maximumDistance += Math.pow(getCodeScope().length(), 2);
                    }
                    break;
                case AFFINEFACECODE:
                    System.out.println("还不支持");
                    break;
                case BINARYCODE:
                    System.out.println("还不支持");
                    break;
                case NORMALFACECODE:
                    System.out.println("还不支持");
                    break;
            }
            maximumDistance = Math.sqrt(maximumDistance);
        }
        return maximumDistance;
    }

    public int getIndividualLength() {
        return individualLength;
    }

    public void setIndividualLength(int individualLength) {
        this.individualLength = individualLength;
    }

    public double[][] getVariableProperties() {
        return variableProperties;
    }

    public void setVariableProperties(double[][] variableProperties) {
        this.variableProperties = variableProperties;
    }

    public int[] getVariableSplit() {
        return variableSplit;
    }

    public void setVariableSplit(int[] variableSplit) {
        if (null != variableSplit && variableSplit.length > 0) {
            this.variableSplit = new int[variableSplit.length];
            System.arraycopy(variableSplit, 0, this.variableSplit, 0, variableSplit.length);
            this.variableSplit = variableSplit;
        }
    }

    public double[] getStopFitness() {
        return stopFitness;
    }

    public void setStopFitness(double[] stopFitness) {
        if (null != stopFitness && stopFitness.length > 0) {
            this.stopFitness = new double[stopFitness.length];
            System.arraycopy(stopFitness, 0, this.stopFitness, 0, stopFitness.length);
        }
    }

    public TreeSet<Individual> getIndividual(int individualNumber) {
        return new Population().init(individualNumber);
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public String getName() {
        return "Abstract";
    }
    //下面为可视化作准备   

    public List<List<Double>> getXListData() {
        if (xListData.get(0).isEmpty()) {
            generateBackground();
        }
        return xListData;
    }

    public List<List<Double>> getYListData() {
        if (yListData.get(0).isEmpty()) {
            generateBackground();
        }
        return yListData;
    }

    public void generateBackground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getDimension() {
        return dimension;
    }
//    protected int evaluationNumber;//调用问题的evaluate方法的次数，用于不同的算法比较用
//
//    public int getEvaluationNumber() {
//        return evaluationNumber;
//    }
//
//    public void initEvaluationNumber() {
//        this.evaluationNumber = 0;
//    }

    public void evaluate(Individual inputedIndividual) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isIECProblem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCodeScope() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setCodeScope(String codeScope) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    protected XYChart exampleChart;

    public void setXYChart(XYChart exampleChart) {
        this.exampleChart = exampleChart;
    }

    public XYChart getXYChart() {
        exampleChart
                = new XYChartBuilder()
                        .width(500)
                        .height(400)
                        .theme(Styler.ChartTheme.Matlab)
                        .title("Real-time XY Chart")
                        .build();
        exampleChart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        //exampleChart.getStyler().setYAxisLogarithmic(true);
        exampleChart.getStyler().setYAxisMin(-0.1);
        exampleChart.getStyler().setYAxisMax(stopFitness[0] + 0.1);
        exampleChart.getStyler().setXAxisMin(variableProperties[0][1] - 0.1);
        exampleChart.getStyler().setXAxisMax(variableProperties[0][0] + 0.1);
        exampleChart.getStyler().setToolTipsEnabled(true);
        return exampleChart;
    }

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public Population generatePopulation() {
        return new Population();
    }

    public AbstractDecisionVariable getDecisionVariable() {
        return abstractDecisionVariable;
    }

    public void setDecisionVariable(AbstractDecisionVariable abstractDecisionVariable) {
        this.abstractDecisionVariable = abstractDecisionVariable;
    }

    public GenecodeType getGenecodeType() {
        return genecodeType;
    }

    public void setGenecodeType(GenecodeType genecodeType) {
        this.genecodeType = genecodeType;
    }
}
