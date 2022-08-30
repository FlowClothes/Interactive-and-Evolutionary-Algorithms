package core.algorithm;

import exclusion.algorithm.operator.filter.offspringFilter.OffspringFilter;

import java.util.List;

import core.problem.Individual;
import core.problem.Population;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public interface Algorithm {

    public void init();

    public void operates(Population populations);// 完成对解的更新

    /**
     * 关键一步！
     * 将前台的各种参数传递到后台！
     *
     * @param populations
     */
    public void operates(Population populations,
                         int inputSelectionType,
                         int inputCrossoverType,
                         int inputCrossoverPointnumValue,
                         int inputMutationType,
                         int inputMutationPointNumValue,
                         double inputMutationProbability);

    public String getName();

    //public void onceGetFitness4TEC();
    public List<Individual> operatePopulation(Population pop);

    public List<Individual> operatePopulation(Population pop,
                                              int inputSelectionType,
                                              int inputCrossoverType,
                                              int inputCrossoverPointnumValue,
                                              int inputMutationType,
                                              int inputMutationPointNumValue,
                                              double inputMutationProbability);

    public void outputBest(Individual best, int i);

    public void reserveBestInd(Population population);

    public void onceGetFitness4TEC(Population population);
}
