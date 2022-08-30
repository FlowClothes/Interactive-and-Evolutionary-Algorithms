package core.entrance;

import core.GUI.GUI2SetParameters;
import core.algorithm.AbstractAlgorithm;
import core.algorithm.FactoryAlgorithm;
import core.algorithm.GeneticAlgorithm;
import core.algorithm.accessory.HistoryIndividuals;
import core.algorithm.accessory.IndividualWithAge;
import core.algorithm.operator.Crossover.FactoryCrossover;
import core.algorithm.operator.Mutation.FactoryMutation;
import core.algorithm.operator.Selection.FactorySelection;
import core.controllers.Connection4SetAndRunning;
import core.controllers.ControlParameters;
import core.problem.DecisionVariables.GenecodeType;
import static core.problem.DecisionVariables.GenecodeType.DOUBLECODE;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.tools.xChart.MyExampleChart;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.knowm.xchart.XYChart;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class MainCmdRuning {

    //从命令行直接进入可以使用的，从而可以避免使用窗口
    GUI2SetParameters scp;
    protected String setupFileName;
    private MyExampleChart<XYChart> mrc;//可视化的界面，观察个体在搜索空间中的分布情况

    public static void main(String[] args) {
        new MainCmdRuning().mainBody();
        //  System.exit(0);
    }

    public String getSetupFileName() {
        return ControlParameters.setupFileName;
    }

    public void callMeByChart(MyExampleChart<XYChart> mrc) {//不做主类，有观察者对种群在搜索空间的分布进行可视化观察
        this.mrc = mrc;
        ControlParameters.needVisualization = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                //把问题读进来，以便绘制背景
                ControlParameters.fp.readFromFile(getSetupFileName());
                FactoryProblems.dimensionNumber = Integer.parseInt(ControlParameters.fp.getValue("dimensionNumber"));
                FactoryProblems.initProblem(Integer.parseInt(ControlParameters.fp.getValue("problemNum")),AbstractAlgorithm.genecodeType);
                FactoryProblems.currentProblem.getXListData();
                mainBody();
                mainrun();
            }
        }).start();
    }

    public void setup() {
        //对于非界面运行情况，从文件中读取配置内容
        ControlParameters.fp.readFromFile(getSetupFileName());
        ControlParameters.experimentRunningTime = Integer.parseInt(ControlParameters.fp.getValue("experimentRunningTime"));
        FactoryCrossover.crossoverType = Integer.parseInt(ControlParameters.fp.getValue("crossoverType"));
        FactoryProblems.dimensionNumber = Integer.parseInt(ControlParameters.fp.getValue("dimensionNumber"));
        FactoryCrossover.crossoverPointNum = FactoryProblems.dimensionNumber == 1 ? 1 : FactoryProblems.dimensionNumber - 1;
        //FactoryMutation.mutationPointNum = FactoryProblems.dimensionNumber == 1 ? 1 : (FactoryProblems.dimensionNumber - 1);
        FactoryMutation.mutationType = Integer.parseInt(ControlParameters.fp.getValue("mutationType"));
        FactorySelection.selectionType = Integer.parseInt(ControlParameters.fp.getValue("selectionType"));
        ControlParameters.stopEvaluationNumber = Integer.parseInt(ControlParameters.fp.getValue("stopEvaluationNumber"));
        AbstractAlgorithm.populationSize = Integer.parseInt(ControlParameters.fp.getValue("populationsize"));
        FactoryAlgorithm.crossMutationCompeteProbability = Double.parseDouble(ControlParameters.fp.getValue("crossMutationCompeteProbability"));
        AbstractAlgorithm.genecodeType=(GenecodeType.valueOf(ControlParameters.fp.getValue("genecodeType")));
    }

    public void mainBody() {
        FactoryProblems.registerProblems();
        ControlParameters.fp.readFromFile(getSetupFileName());
        FactoryProblems.problemNum = Integer.parseInt(ControlParameters.fp.getValue("problemNum"));
        this.mainrun();
    }

    protected void mainrun() {
        FactoryProblems.dimensionNumber = Integer.parseInt(ControlParameters.fp.getValue("dimensionNumber"));
        FactoryProblems.initProblem(FactoryProblems.problemNum,AbstractAlgorithm.genecodeType);
        //从配置文件中读内容进来
        this.setup();
        FactoryAlgorithm.currentAlgorithm = new GeneticAlgorithm();
        FactoryAlgorithm.currentAlgorithm.init();
        //if (temKnowledge != Knowledge.SimpleGA) {//历史个体不重复出现
        //交叉与变异必须执行，不然的话，历史个体就会重复出现
        // FactoryCrossover.crossProbability = 1f;
        //FactoryMutation.mutationProbability = 1f;
        //}
        //ControlParameters.fp.readFromFile(getSetupFileName());
        Connection4SetAndRunning connection4SetAndRunning = new Connection4SetAndRunning();
        connection4SetAndRunning.running();
    }

    List<Double> bestFitness = new LinkedList<>();

    public void getPopulationData() {
        while (ControlParameters.willContinue || null == FactoryProblems.currentProblem.getPopulation() || null == FactoryProblems.currentProblem.getPopulation().getIndividuals()) {
            //数据还在进化中，等待
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainCmdRuning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        TreeSet<Individual> tem = FactoryProblems.currentProblem.getPopulation().getIndividuals();
        List<Double> xData = new LinkedList<>(), yData = new LinkedList<>();
        if (FactoryProblems.currentProblem.getDecisionVariable().getGenecodeType() == DOUBLECODE) {
            for (Individual ind : tem) {
                xData.add(ind.getDecisionVariable().getGeneCodes()[0]);
                yData.add(ind.getDecisionVariable().getFitness()[0]);
            }
        }
        this.mrc.setxData(xData);
        this.mrc.setyData(yData);

        //再看一下上一代
//        List<Individual> lastPop = HistoryIndividuals.getInstance().getRecentHistory(1);
//        List<Double> xLastData = new LinkedList<>(), yLastData = new LinkedList<>();
//        if (FactoryProblems.currentProblem.getDecisionVariable().getGenecodeType() == DOUBLECODE) {
//            lastPop.stream().map((ind) -> {
//                // ControlParameters.currentProblem.setFitness4(tem.get(i));
//                xLastData.add(ind.getDecisionVariable().getGeneCodes()[0]);
//                return ind;
//            }).forEachOrdered((ind) -> {
//                yLastData.add(ind.getFitness()[0]);
//            });
//        }
//        this.mrc.setxLastData(xLastData);
//        this.mrc.setyLastData(yLastData);
        //最优个体返回
        //对于多模问题，当适应值差距不大，但决策空间距离较大时，就可以显示
        TreeSet<IndividualWithAge> bestIndividual = HistoryIndividuals.getInstance().getHistory4Parent();
        List<Double> xBestData = new LinkedList<>(), yBestData = new LinkedList<>();
        for (IndividualWithAge best : bestIndividual) {
            if (FactoryProblems.currentProblem.getDecisionVariable().getGenecodeType() == DOUBLECODE) {
                xBestData.add(best.getIndividual().getDecisionVariable().getGeneCodes()[0]);
                yBestData.add(best.getIndividual().getDecisionVariable().getFitness()[0]);
                bestFitness.add(best.getIndividual().getDecisionVariable().getFitness()[0]);
            }
        }
        this.mrc.setxBestData(xBestData);
        this.mrc.setyBestData(yBestData);

        //再看最优个体适应值变化
        this.mrc.setYData(bestFitness);
//         数据取完，开始下一代进化
        ControlParameters.willContinue = true;
    }

    public XYChart getXYChart() {
        while (null == FactoryProblems.currentProblem) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainCmdRuning.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return FactoryProblems.currentProblem.getXYChart();
    }
}
