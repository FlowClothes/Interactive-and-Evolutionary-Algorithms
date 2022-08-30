package core.controllers;

import core.algorithm.AbstractAlgorithm;
import core.algorithm.Algorithm;
import core.algorithm.FactoryAlgorithm;
import core.algorithm.accessory.AdaptiveTricks;
import core.algorithm.accessory.HistoryIndividuals;
import core.algorithm.accessory.IndividualsDiversity;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Population;

import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class Connection4SetAndRunning {

    private JTextArea mainTextarea;
    int runTimes;

    int inputSelectionType;
    int inputCrossoverType;
    int inputCrossoverPointnumValue;
    int inputMutationType;
    int inputMutationPointNumValue;
    double inputMutationProbability;

    public void running() {
        Algorithm algorithm = FactoryAlgorithm.currentAlgorithm;
        //不需要窗口的running
        System.out.println("Begin....\n");
        long startTime = System.currentTimeMillis();
        runTimes = ControlParameters.experimentRunningTime;
        //开始实验
        int k = 1;//实验次数
//        for (int i = ControlParameters.currentPopulationsize; i < ControlParameters.populationsizeList.size(); i++) {
        //保持这一种群不变，要运行多次，如200，实验
        for (int j = ControlParameters.currentExperimentNumber; j < ControlParameters.experimentRunningTime; j++) {
            //准备好存放数据的list，好处是不用每次使用时都判断是否已经被初始化了;
            //problem被初始化，与上一次运行脱离关系，不受上一次运行的影响
            FactoryProblems.currentProblem.init(FactoryProblems.dimensionNumber);
            //geneticAlgorithm.setPopulationSize(ControlParameters.populationsizeList.get(i));
            algorithm.init();
            try {
                //开始进化
                this.beginEvolving(
                        inputSelectionType,
                        inputCrossoverType,
                        inputCrossoverPointnumValue,
                        inputMutationType,
                        inputMutationPointNumValue,
                        inputMutationProbability
                );//直接把实验结果写到result中
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Connection4SetAndRunning.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.outprint(k++, startTime);
        }
        //}
        System.out.println("over===========");
    }

    public void running(JPanel parentPane,
                        int inputSelectionType,
                        int inputCrossoverType,
                        int inputCrossoverPointnumValue,
                        int inputMutationType,
                        int inputMutationPointNumValue,
                        double inputMutationProbability) {
        Algorithm algorithm = FactoryAlgorithm.currentAlgorithm;
        //通过GUI进来的running

        this.inputSelectionType = inputSelectionType;
        this.inputCrossoverType = inputCrossoverType;
        this.inputCrossoverPointnumValue = inputCrossoverPointnumValue;
        this.inputMutationType = inputMutationType;
        this.inputMutationPointNumValue = inputMutationPointNumValue;
        this.inputMutationProbability = inputMutationProbability;


        validate(parentPane, "Begin....\n");
        long startTime = System.currentTimeMillis();
        //开始实验
        int k = 1;//实验次数
        for (int j = ControlParameters.currentExperimentNumber; j < ControlParameters.experimentRunningTime; j++) {
            //准备好存放数据的list，好处是不用每次使用时都判断是否已经被初始化了;
            //problem被初始化，与上一次运行脱离关系，不受上一次运行的影响
            FactoryProblems.currentProblem.init(FactoryProblems.dimensionNumber);
            //geneticAlgorithm.setPopulationSize(ControlParameters.populationsizeList.get(i));
            algorithm.init();
            try {
                this.beginEvolving(
                        inputSelectionType,
                        inputCrossoverType,
                        inputCrossoverPointnumValue,
                        inputMutationType,
                        inputMutationPointNumValue,
                        inputMutationProbability
                );
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Connection4SetAndRunning.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.outprint(k++, startTime);
            System.gc();
        }
        validate(parentPane, "over\nThe results can be found in Excel files\n");
    }

    public synchronized void beginEvolving(int inputSelectionType,
                                           int inputCrossoverType,
                                           int inputCrossoverPointnumValue,
                                           int inputMutationType,
                                           int inputMutationPointNumValue,
                                           double inputMutationProbability) throws CloneNotSupportedException {
        Algorithm algorithm = FactoryAlgorithm.currentAlgorithm;
        int generationNum = 0, evaluationNumber = 0;
        long st = System.currentTimeMillis();
        // Begin the TGA
        Population population = FactoryProblems.currentProblem.generatePopulation();//生成种群;
        population.setIndividuals(population.init(AbstractAlgorithm.populationSize));
        algorithm.onceGetFitness4TEC(population);
        evaluationNumber += population.getIndividuals().size();
        //Collections.sort(population.getIndividuals(), Collections.reverseOrder());//完成了种群排序，以实现最优个体保留
        //上面完成了最优个体保留
        //记录实验过程
        outputBest(population, generationNum);
//        System.arraycopy(((Individual) (population.getIndividuals().last())).getFitness(), 0, oldBest, 0, bestIndividual.getFitness().length);
        //记录下来，为了观察最优解的跃迁
        //处理停止适应值,借壳，用来对适应值进行比较，因为适应值可能是多目标的
        Individual stopFitnessIndividual = null;
        stopFitnessIndividual = ((Individual) population.getIndividuals().last()).clone();
        stopFitnessIndividual.getDecisionVariable().setFitness(FactoryProblems.currentProblem.getStopFitness());

        boolean continueEvolve = true;//后面用这个来处理算法是否结束的判断，从而结合多种算法终止条件
        //当再遍历10*populationSize仍然没有新的最优解出现，则结束
        //System.out.println(ControlParameters.currentProblem.getName() + ":  best fitness" + population.getBestIndividual().get(0).getFitness()[0]);
        while (continueEvolve) {
            ////////////观察者尚未取数据
            while (ControlParameters.willContinue) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Connection4SetAndRunning.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //////////////
//            if (generationNum % 50 == 0) {
//                System.out.println("generationNumber" + generationNum);
//            }
            generationNum++;
            algorithm.operates(
                    population,
                    inputSelectionType,
                    inputCrossoverType,
                    inputCrossoverPointnumValue,
                    inputMutationType,
                    inputMutationPointNumValue,
                    inputMutationProbability);//在选择算子中执行选择前进行排序
            evaluationNumber += population.getIndividuals().size();
            //System.out.println(population.getIndividuals().size());
            if (ControlParameters.needVisualization) {
                ControlParameters.willContinue = false;
            }
            //记录最优个体变化情况
            HistoryIndividuals.getInstance().getParentAndUpdateHistory(population.getIndividuals());
            //记录实验过程
            outputBest(population, generationNum);
            if (HistoryIndividuals.getInstance().getBestSorfar().compareTo(stopFitnessIndividual) >= 0 //所有问题都要转换为求最大值的问题
                    || evaluationNumber >= ControlParameters.stopEvaluationNumber) {
                continueEvolve = false;
            }
            this.outputPopulation(population, generationNum);
        }
        System.out.println(FactoryProblems.currentProblem.getName() + ":  best fitness" + HistoryIndividuals.getInstance().getBestSorfar().getDecisionVariable().getFitness()[0]);

        long et = System.currentTimeMillis();
        //下面记录运行时间
        long t3 = et - st;
        System.out.println("imesecondcost: " + (int) t3);//算法执行时长
        //记录算法与问题的交互次数，即实验访问个体的次数，相当于历史个体个数
        System.out.println("evaluationnumber: " + evaluationNumber);
        System.out.println("Bestsolution: " + (HistoryIndividuals.getInstance().getBestSorfar().getDecisionVariable().getX()));
        System.out.println("Bestfitness: " + HistoryIndividuals.getInstance().getBestSorfar().getDecisionVariable().getFitnessString());
    }

    public JTextArea getTextarea() {
        return mainTextarea;
    }

    public void setTextArea(JTextArea mainPane) {
        this.mainTextarea = mainPane;
    }

    private void validate(JPanel parentJPanel, String text) {
        //如果都往这个 mainTextarea里边灌数据，则当实验数目太多时，就会发生OutOfMemoryError: Java heap space的错误
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainTextarea.append(text + "\n");
                parentJPanel.validate();
            }
        });
    }

    private void outprint(int k, long startTime) {
        System.out.printf("完成第%d次实验", k);
        long consummedTime = System.currentTimeMillis() - startTime;
        System.out.printf("已经运行%d秒，估计还需要%d秒\n", consummedTime / 1000,
                ((consummedTime + 1) * (runTimes - k) / (k * 1000)));//加1的目的是防止出现consummedTime为0的情况
    }

    private void outputPopulation(Population population, int generationNumber) {
        System.out.print(generationNumber + "\t");
        Individual best = ((Individual) (HistoryIndividuals.getInstance().getHistory4Parent().last().getIndividual())).compareTo((Individual) population.getIndividuals().last()) < 0
                ? (Individual) population.getIndividuals().last()
                : (Individual) (HistoryIndividuals.getInstance().getHistory4Parent().last().getIndividual());
        System.out.print(best.getDecisionVariable().getFitnessString() + "\t");
        //System.out.print(best.getDecisionVariable().getX() + "\t");
        System.out.print(population.getIndividuals().size() + "\t");
        double[] maxMinAvg = IndividualsDiversity.maxDistanceAmongIndividuals((TreeSet) population.getIndividuals());
        System.out.println("MaxDistance:\t" + maxMinAvg[0] /// FactoryProblems.currentProblem.getMaximumDistance()
                + "\tMinDistance:\t" + maxMinAvg[1] + "\taverage:\t" + maxMinAvg[2]
                + "\tradius:\t" + AdaptiveTricks.getInstance().getCurrentParentExclusionRadius()
        );
//        int i = 0;
//        for (Iterator it = populations.getIndividuals().iterator(); it.hasNext();) {
//            Individual ind = (Individual) it.next();
//            System.out.printf("%5.2f :fitness %6.4f", ind.getDecisionVariable().getGeneCodes()[0], ind.getFitness()[0]);
//        }
    }

    private void outputBest(Population population, int generationNum) {
        System.out.println(generationNum + ((Individual) population.getIndividuals().last()).getDecisionVariable().getFitnessString());
    }
}
