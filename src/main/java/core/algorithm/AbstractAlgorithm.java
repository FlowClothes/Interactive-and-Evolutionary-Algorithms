package core.algorithm;

import core.algorithm.accessory.HistoryIndividuals;
import core.problem.DecisionVariables.GenecodeType;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Population;

import java.util.List;
import java.util.TreeSet;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public abstract class AbstractAlgorithm implements Algorithm {

    public static int populationSize;//种群规模的下限与上限
    public static int upperBoundPopulationSize;//种群规模的下限与上限
    public static GenecodeType genecodeType;
    //如PSO需要保存多个最优解

    @Override
    public final void operates(Population population) {//final不允许子类对该方法进行修改，这里组装了必要的步骤，属于模板模式
        // population.outputPopulation();
        List<Individual> operatedIndividuals = this.operatePopulation(population);//进化过程,不同的算法有不同的方法，会不一样，因此，这个方法要求各个算法自己实现
        ecapsule(operatedIndividuals, population);
        population.getIndividuals().add(HistoryIndividuals.getInstance().getBestSorfar().clone());//加入最优个体
        if (population.getIndividuals().size() > populationSize) {
            population.getIndividuals().remove(population.getIndividuals().first());
        }
        FactoryProblems.dealwithJulia(population);//这个问题需要处理
    }

    @Override
    public final void operates(Population population,
                               int inputSelectionType,
                               int inputCrossoverType,
                               int inputCrossoverPointnumValue,
                               int inputMutationType,
                               int inputMutationPointNumValue,
                               double inputMutationProbability) {//final不允许子类对该方法进行修改，这里组装了必要的步骤，属于模板模式
        // population.outputPopulation();
        List<Individual> operatedIndividuals = this.operatePopulation(
                population,
                inputSelectionType,
                inputCrossoverType,
                inputCrossoverPointnumValue,
                inputMutationType,
                inputMutationPointNumValue,
                inputMutationProbability);//进化过程,不同的算法有不同的方法，会不一样，因此，这个方法要求各个算法自己实现

        ecapsule(operatedIndividuals, population);
        population.getIndividuals().add(HistoryIndividuals.getInstance().getBestSorfar().clone());//加入最优个体
        if (population.getIndividuals().size() > populationSize) {
            population.getIndividuals().remove(population.getIndividuals().first());
        }
        FactoryProblems.dealwithJulia(population);//这个问题需要处理
    }

    @Override
    public void onceGetFitness4TEC(Population population) {
        //只有非交互式进化计算才需要计算适应值
        //这个方法在一次实验中只会被调用1次，即只针对初始种群
        TreeSet<Individual> newIndividualSet = new TreeSet<>();
        population.getIndividuals().forEach((ind) -> {
            //计算个体的适应值，第0个不用计算，因为会最优保留
            FactoryProblems.currentProblem.evaluate((Individual) ind);
            newIndividualSet.add((Individual) ind);//add过程是重新排序的过程
        });
        population.setIndividuals(newIndividualSet);
        //下面把Individual加入到histroy时，会在后来被克隆
        HistoryIndividuals.getInstance().getParentAndUpdateHistory(newIndividualSet);
    }

    @Override
    public void reserveBestInd(Population population) {
        population.getIndividuals().add(HistoryIndividuals.getInstance().getHistory4Parent().last().getIndividual().clone());
    }

    @Override
    public void init() {
        //重新初始化历史个体，因为开始了一个新的实验
        HistoryIndividuals.getInstance().init();
    }

    // 包装种群
    public void ecapsule(List<Individual> operatedIndividuals, Population population) {
        //获取过滤后个体的适应值
        operatedIndividuals.forEach((ind) -> {
            FactoryProblems.currentProblem.evaluate((Individual) ind);
        });
        //把List<Individual>加入到TreeSet中
        population.getIndividuals().clear();
        //移除第一个，相当于遗传操作后的随机个体，目的为最优保留，把上代的加入进来
        //population.getIndividuals().remove(population.getIndividuals().first());
        operatedIndividuals.forEach((ind) -> {
            population.getIndividuals().add(ind);
        });
        System.out.println("good");
    }

    @Override
    public String getName() {
        return "Abstract algorithm";
    }

    @Override
    public void outputBest(Individual best, int i) {
        System.out.println(i + ":=====" + best.getDecisionVariable().getGeneCodes()[0] + "::" + best.getDecisionVariable().getFitness()[0]);
    }
}
