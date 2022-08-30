package bigPouWithWRS_RS;

import com.sun.management.OperatingSystemMXBean;
import core.algorithm.AbstractAlgorithm;
import core.algorithm.Algorithm;
import core.algorithm.FactoryAlgorithm;
import core.algorithm.accessory.HistoryIndividuals;
import core.algorithm.exploerOrExploit;
import core.algorithm.operator.Crossover.FactoryCrossover;
import core.algorithm.operator.Mutation.FactoryMutation;
import core.algorithm.operator.Selection.FactorySelection;
import core.controllers.Connection4SetAndRunning;
import core.controllers.ControlParameters;
import tools.experimentsDataDealing.ExperimentSetup;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Population;
import core.tools.MyMath.RandomGenerator;
import core.tools.office.Excel.J2Excel;
import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class BigPopConnection4SetAndRunning extends Connection4SetAndRunning {

    int runTimes;
    J2Excel j2Excel = new J2Excel();
    double[] oldBest;

    @Override
    public void running() {
        Algorithm algorithm = FactoryAlgorithm.currentAlgorithm;
        //不需要窗口的running
        System.out.println("Begin....\n");
        getEnvironAlgorithmParameter();
        long startTime = System.currentTimeMillis();
        runTimes = ControlParameters.experimentRunningTime;
        //开始实验
        int k = 1;//实验次数
        ExperimentSetup es = new ExperimentSetup();
        runTimes*=es.getPs().length;
        for (int i = 0; i < es.getPs().length; i++) {
            //保持这一种群不变，要运行多次，如200，实验
            {
                AbstractAlgorithm.populationSize = es.getPs()[i];
                AbstractAlgorithm.upperBoundPopulationSize = AbstractAlgorithm.populationSize;
                HistoryIndividuals.sizeOfParentRepository = AbstractAlgorithm.populationSize;
                HistoryIndividuals.sizeOfOffspringRepository = AbstractAlgorithm.populationSize;
            }
            for (int j = ControlParameters.currentExperimentNumber; j < ControlParameters.experimentRunningTime; j++) {
                //准备好存放数据的list，好处是不用每次使用时都判断是否已经被初始化了;
                Map<Integer, List<Object>> excelData = new TreeMap<>();
                //problem被初始化，与上一次运行脱离关系，不受上一次运行的影响
                FactoryProblems.currentProblem.init(FactoryProblems.dimensionNumber);
                //geneticAlgorithm.setPopulationSize(ControlParameters.populationsizeList.get(i));
                algorithm.init();
                try {
                    //开始进化
                    this.beginEvolving(excelData);//直接把实验结果写到result中
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(Connection4SetAndRunning.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.outprint(k++, startTime);
                j2Excel.write2Excel(FactoryProblems.currentProblem.getName() + "_" + AbstractAlgorithm.populationSize + ".xlsx", String.valueOf(AbstractAlgorithm.populationSize), excelData);
            }
        }
        System.out.println("over===========");
    }

    public synchronized void beginEvolving(Map<Integer, List<Object>> excelData) throws CloneNotSupportedException {
        BigPouAlgorithm algorithm = (BigPouAlgorithm) FactoryAlgorithm.currentAlgorithm;
        int generationNum = 1, evaluationNumber = 0;
        long st = System.currentTimeMillis();
        List<Integer> fitnessSteps = new LinkedList<>();
        fitnessSteps.add(0);
        // Begin the TGA       
        Population population = FactoryProblems.currentProblem.generatePopulation();//生成种群;
        population.setIndividuals(population.init(AbstractAlgorithm.populationSize));
        algorithm.onceGetFitness4TEC(population);
        evaluationNumber += population.getIndividuals().size();
//上面完成了最优个体保留
        int startNumber = 7;
        //记录实验过程
        //System.arraycopy(((Individual) (population.getIndividuals().last())).getFitness(), 0, oldBest, 0, bestIndividual.getFitness().length);
        //记录下来，为了观察最优解的跃迁
        //处理停止适应值,借壳，用来对适应值进行比较，因为适应值可能是多目标的
        //下面在generationNum上加这个数是因为要保存下面的信息：
        //0.这次实验运行的时间
        //1.另一个是阶跃效率
        //2.exclusion set的大小
        //3.最优解对应的解X的取值
        //4.最优解
        //5.运行的进化代数
        // if (excelData.get(generationNum + startNumber) == null) {
        //   excelData.put(generationNum + startNumber, new LinkedList<>());
        //}
        for (int i = 0; i < ControlParameters.stopEvaluationNumber / AbstractAlgorithm.populationSize + startNumber + 2; i++) {
            excelData.put(i, new LinkedList<>());
        }
        //找最好的个体
        Individual bestIndividual = ((Individual) population.getIndividuals().last()).clone();
        for (int i = 0; i < bestIndividual.getDecisionVariable().getFitness().length; i++) {//如果是多目标
            excelData.get(generationNum + startNumber).add(bestIndividual.getDecisionVariable().getFitness()[i]);
        }
        oldBest = new double[bestIndividual.getDecisionVariable().getFitness().length];
        System.arraycopy(((Individual) (population.getIndividuals().last())).getDecisionVariable().getFitness(), 0, oldBest, 0, bestIndividual.getDecisionVariable().getFitness().length);

        Individual stopFitnessIndividual = ((Individual) population.getIndividuals().last()).clone();
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
            algorithm.operates(population);//在选择算子中执行选择前进行排序
            evaluationNumber += population.getIndividuals().size();
            //记录最优个体变化情况
            HistoryIndividuals.getInstance().getParentAndUpdateHistory(population.getIndividuals());
            bestIndividual = HistoryIndividuals.getInstance().getBestSorfar();
            //记录实验过程
            fitnessAdjustRecorder(bestIndividual.getDecisionVariable().getFitness(), generationNum, fitnessSteps);
            for (int i = 0; i < bestIndividual.getDecisionVariable().getFitness().length; i++) {
                excelData.get(generationNum + startNumber).add(bestIndividual.getDecisionVariable().getFitness()[i]);
            }

            if (bestIndividual.compareTo(stopFitnessIndividual) >= 0 //所有问题都要转换为求最大值的问题
                    || generationNum * AbstractAlgorithm.populationSize >= ControlParameters.stopEvaluationNumber) {
                continueEvolve = false;
            }
            this.outputPopulation(population, generationNum);
            //每次都重新设置选择的概率
            if (generationNum < 10) {
                algorithm.setRankProbability(exploerOrExploit.EXPLORER);
            } else {
                if (HistoryIndividuals.getInstance().getUnImproved() > 5) {//5代没有提高或改进
                    algorithm.setRankProbability(exploerOrExploit.EXPLORER);
                    System.out.print("EXPLORER\t");
                } else {
                    if (RandomGenerator.nextDouble() > 0.5) {
                        System.out.print("EXPLOIT\t");
                        algorithm.setRankProbability(exploerOrExploit.EXPLOIT);
                    } else {
                        System.out.print("MEDIA\t");
                        algorithm.setRankProbability(exploerOrExploit.MEDIA);
                    }
                }
            }
        }
        System.out.println(FactoryProblems.currentProblem.getName() + ":  best fitness" + HistoryIndividuals.getInstance().getBestSorfar().getDecisionVariable().getFitness()[0]);

        long et = System.currentTimeMillis();
        //下面记录运行时间
        long t3 = et - st;
        //记录最优个体变化情况
        bestIndividual = HistoryIndividuals.getInstance().getBestSorfar();
        fitnessAdjustRecorder(bestIndividual.getDecisionVariable().getFitness(), generationNum, fitnessSteps);
        //===========下面把本代的最优解记录下来================
//            if (excelData.get(generationNum + startNumber) == null) {
//                excelData.put(generationNum + startNumber, new LinkedList<>());
//            }
//        for (int i = 0; i < bestIndividual.getFitness().length; i++) {
//            excelData.get(generationNum + startNumber + 1).add(bestIndividual.getFitness()[i]);
//        }

        //this.outputPopulation(population, generationNum);
        System.out.println(FactoryProblems.currentProblem.getName() + ":  best fitness" + bestIndividual.getDecisionVariable().getFitness()[0]);
        excelData.get(0).add(t3);//算法执行时长
        //把适应值阶跃值记下来，为写到excel中做准备
        StringBuilder fitnessStepString = new StringBuilder();
        for (int i = 0; i < fitnessSteps.size(); i++) {
            fitnessStepString.append(fitnessSteps.get(i)).append(",");
        }
        excelData.get(1).add(fitnessStepString.toString());//第1行是适应值进阶情况
        //第2行记录算法与问题的交互次数
        excelData.get(2).add(evaluationNumber);
        //第3行是最优解所对应的X值
        excelData.get(3).add(bestIndividual.getDecisionVariable().getX());
        //第4行是最优适应值
        for (int i = 0; i < bestIndividual.getDecisionVariable().getFitness().length; i++) {
            excelData.get(4).add(bestIndividual.getDecisionVariable().getFitness()[i]);
        }

        //第5行是记录是否hit了目标适应值
        if (bestIndividual.compareTo(stopFitnessIndividual) >= 0) {//hit了optima
            excelData.get(5).add(1);
        } else {//没有hit optima
            excelData.get(5).add(0);
        }
        excelData.get(6).add(generationNum);
        //如果这一次实验提前结束，则补齐剩余的数据
        if (generationNum * AbstractAlgorithm.populationSize < ControlParameters.stopEvaluationNumber) {
            generationNum++;
            for (; generationNum * AbstractAlgorithm.populationSize < ControlParameters.stopEvaluationNumber; generationNum++) {
                for (int i = 0; i < bestIndividual.getDecisionVariable().getFitness().length; i++) {
                    if (excelData.get(generationNum + startNumber) == null) {//如果这一行未准备好，则初始化
                        excelData.put(generationNum + startNumber, new LinkedList<>());
                    }
                    excelData.get(generationNum + startNumber).add(bestIndividual.getDecisionVariable().getFitness()[i]);
                }
            }
        }

    }

    private void fitnessAdjustRecorder(double[] newfitness, int generationNum, List<Integer> fitnessSteps) {
        int lt = 0;
        for (int i = 0; i < oldBest.length; i++) {
            lt = oldBest[i] < newfitness[i] ? ++lt : --lt;
        }
        if (lt == oldBest.length) {//最优解有变化，发生了一个适应值阶跃
            fitnessSteps.add(generationNum);
            System.arraycopy(newfitness, 0, oldBest, 0, oldBest.length);
        }
    }

    private void getEnvironAlgorithmParameter() {
        List<String> excelSheetName = new LinkedList<>();
        List<Map<Integer, List<Object>>> excelData = new LinkedList<>();
        //下面准备写入运行环境
        excelSheetName.add("Environment");
        Map<Integer, List<Object>> environmentInfo = new TreeMap<>();
        OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        List<Object> cpuList = new LinkedList<>();
        cpuList.add("Available CPU:" + bean.getAvailableProcessors());
        environmentInfo.put(1, cpuList);
        List<Object> memeoryList = new LinkedList<>();
        memeoryList.add("Total physical memory size:" + bean.getTotalPhysicalMemorySize());
        environmentInfo.put(2, memeoryList);
        List<Object> operatingversion = new LinkedList<>();
        operatingversion.add("Operating version" + bean.getVersion());
        environmentInfo.put(3, operatingversion);
        excelData.add(environmentInfo);
        //下面准备写入运行参数信息========================
        excelSheetName.add("Running time parameters");
        Map<Integer, List<Object>> parameterInfo = new TreeMap<>();
        List<Object> algorithmParameters = new LinkedList<>();
        algorithmParameters.add("Algorithm name:" + FactoryAlgorithm.currentAlgorithm.getName());
        parameterInfo.put(1, algorithmParameters);
        List<Object> selectionType = new LinkedList<>();
        selectionType.add("Selection operator:" + FactorySelection.getName(FactorySelection.selectionType));
        parameterInfo.put(2, selectionType);
        List<Object> crossoverType = new LinkedList<>();
        crossoverType.add("Crossover operator:" + FactoryCrossover.getName(FactoryCrossover.crossoverType));
        parameterInfo.put(3, crossoverType);
//        List<Object> crossoverProbability = new LinkedList<>();
//        crossoverProbability.add("Crossover probability:" + FactoryCrossover.crossProbability);
//        parameterInfo.put(4, crossoverProbability);
        List<Object> crossoverPointNumber = new LinkedList<>();
        crossoverPointNumber.add("Crossover point number:" + FactoryCrossover.crossoverPointNum);
        parameterInfo.put(5, crossoverPointNumber);
        List<Object> mutationOpertorType = new LinkedList<>();
        mutationOpertorType.add("Mutation operator:" + FactoryMutation.getName(FactoryMutation.mutationType));
        parameterInfo.put(6, mutationOpertorType);
//        List<Object> mutationProbability = new LinkedList<>();
//        mutationProbability.add("Mutation probability" + FactoryMutation.mutationProbability);
//        parameterInfo.put(7, mutationProbability);
        List<Object> mutationPointNumber = new LinkedList<>();
        mutationPointNumber.add("Mutation point number:" + 1);
        parameterInfo.put(8, mutationPointNumber);
        List<Object> maxEvaluation = new LinkedList<>();
        maxEvaluation.add("The specified maximum evaluation number is:" + ControlParameters.stopEvaluationNumber);
        parameterInfo.put(10, maxEvaluation);
        excelData.add(parameterInfo);
        //准备关于问题的说明
        excelSheetName.add("Problem");
        Map<Integer, List<Object>> problemInfo = new TreeMap<>();
        List<Object> dimension = new LinkedList<>();
        dimension.add("Dimension is:" + FactoryProblems.dimensionNumber);
        problemInfo.put(1, dimension);
        List<Object> problemName = new LinkedList<>();
        problemName.add(FactoryProblems.currentProblem.getName());
        problemInfo.put(2, problemName);
        excelData.add(problemInfo);
        for (int i = 0; i < excelSheetName.size(); i++) {
            j2Excel.write2Excel(FactoryProblems.currentProblem.getName() + ".xlsx", excelSheetName.get(i), excelData.get(i));
        }
    }

    private void outprint(int k, long startTime) {
        System.out.printf("完成第%d次实验", k);
        long consummedTime = System.currentTimeMillis() - startTime;
        System.out.printf("已经运行%d秒，估计还需要%d秒\n", consummedTime / 1000,
                ((consummedTime + 1) * (runTimes - k) / (k * 1000)));//加1的目的是防止出现consummedTime为0的情况
    }

    private void outputPopulation(Population population, int generationNumber) {
        System.out.print(generationNumber + ":\t");
        Individual best = ((Individual) (HistoryIndividuals.getInstance().getHistory4Parent().last().getIndividual())).compareTo((Individual) population.getIndividuals().last()) < 0
                ? (Individual) population.getIndividuals().last()
                : (Individual) (HistoryIndividuals.getInstance().getHistory4Parent().last().getIndividual());
        System.out.print(best.getDecisionVariable().getFitnessString() + "f\t");

        //       double[] maxMinAvg = IndividualsDiversity.maxDistanceAmongIndividuals((TreeSet) population.getIndividuals());
//        System.out.println("MaxDistance:\t" + maxMinAvg[0] /// FactoryProblems.currentProblem.getMaximumDistance()
//                + "\tMinDistance:\t" + maxMinAvg[1] + "\taverage:\t" + maxMinAvg[2]
//                + "\tradius:\t" + AdaptiveTricks.getInstance().getCurrentParentExclusionRadius()
//        );
        for (Object ind : population.getIndividuals()) {
            System.out.print(((Individual) ind).getDecisionVariable() + "\t");
        }
        System.out.println("");
    }

}
