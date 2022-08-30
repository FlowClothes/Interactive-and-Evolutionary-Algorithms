package core.algorithm;

import core.algorithm.operator.Crossover.Crossover;
import core.algorithm.operator.Crossover.FactoryCrossover;
import core.algorithm.operator.Mutation.FactoryMutation;
import core.algorithm.operator.Mutation.Mutation;
import core.algorithm.operator.Selection.FactorySelection;
import core.algorithm.operator.Selection.Selection;
import core.problem.DecisionVariables.DecisionVariable;
import core.problem.Individual;
import core.problem.Population;
import core.tools.MyMath.RandomGenerator;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class GeneticAlgorithm extends AbstractAlgorithm {

    Crossover crossover;
    Mutation mutation;
    Selection selection;

    HashSet<Integer> crossoverIndices, mutationIndices;
    //HashSet保存交叉与变异个体的索引号
    List<Individual> crossedIndividuals, mutationIndividuals;

    //List保存交叉与变异的个体
    public GeneticAlgorithm() {
        super();
        crossover = FactoryCrossover.getCrossover(0);
        mutation = FactoryMutation.getMutation(0);
        selection = FactorySelection.getSelection(0);
        crossoverIndices = new HashSet<>();
        mutationIndices = new HashSet<>();
        crossedIndividuals = new LinkedList<>();
        mutationIndividuals = new LinkedList<>();
    }

    @Override
    public List<Individual> operatePopulation(Population pop) {
        reserveBestInd(pop);//最优个体保留到新的一代的目的是在选择算子中使用，所以在选择前执行，至于从外观指标上来说，则是用于监控算法用的
        List<Individual> selectedIndividuals = selection.getSelectionResult(pop);//选择算子
        //下面进行交叉和变异
        int crossoverNumber = (int) (FactoryAlgorithm.crossMutationCompeteProbability * selectedIndividuals.size());
        crossoverIndices.clear();
        mutationIndices.clear();
        for (int i = 0; i < selectedIndividuals.size(); i++) {
            //先把所有个体放在侯选变异中，后面再把要交叉的移除
            mutationIndices.add(i);
        }
        //交叉个体的索引号的确定
        while (crossoverIndices.size() < crossoverNumber) {
            crossoverIndices.add(RandomGenerator.getRandom(selectedIndividuals.size(), 0));
        }
        //变异，除了交叉的，剩下的进行变异
        mutationIndices.removeAll(crossoverIndices);
        //收集待交叉的个体
        crossedIndividuals.clear();
        for (Integer i : crossoverIndices) {
            crossedIndividuals.add(selectedIndividuals.get(i));
        }
        //收集待变异的个体
        mutationIndividuals.clear();
        for (Integer i : mutationIndices) {
            mutationIndividuals.add(selectedIndividuals.get(i));
        }
        //交叉
        crossedIndividuals = crossover.getCrossoverResult(crossedIndividuals);
        //System.out.println("交叉得到的个体");
        //outputSolution(crossedIndividuals);
        mutationIndividuals = mutation.getMutationResult(mutationIndividuals);
        //System.out.println("变异得到的个体");
        //outputSolution(mutationIndividuals);
        //交叉与变异的汇总到一起
        crossedIndividuals.addAll(mutationIndividuals);
        //System.out.println("这是新一代");
        //outputSolution(crossedIndividuals);
        //过滤重复个体,确保只评价不重复的个体
        removeDuplicated(crossedIndividuals);
        return crossedIndividuals;
    }

    public List<Individual> operatePopulation(Population pop,
                                              int inputSelectionType,
                                              int inputCrossoverType,
                                              int inputCrossoverPointnumValue,
                                              int inputMutationType,
                                              int inputMutationPointNumValue,
                                              double inputMutationProbability) {

        //设置前台传过来的参数
        selection = FactorySelection.getSelection(inputSelectionType);
        crossover = FactoryCrossover.getCrossover(inputCrossoverType);
        mutation = FactoryMutation.getMutation(inputMutationType);

        reserveBestInd(pop);//最优个体保留到新的一代的目的是在选择算子中使用，所以在选择前执行，至于从外观指标上来说，则是用于监控算法用的
        List<Individual> selectedIndividuals = selection.getSelectionResult(pop);//选择算子
        //下面进行交叉和变异
        int crossoverNumber = (int) (FactoryAlgorithm.crossMutationCompeteProbability * selectedIndividuals.size());
        crossoverIndices.clear();
        mutationIndices.clear();
        for (int i = 0; i < selectedIndividuals.size(); i++) {
            //先把所有个体放在侯选变异中，后面再把要交叉的移除
            mutationIndices.add(i);
        }
        //交叉个体的索引号的确定
        while (crossoverIndices.size() < crossoverNumber) {
            crossoverIndices.add(RandomGenerator.getRandom(selectedIndividuals.size(), 0));
        }
        //变异，除了交叉的，剩下的进行变异
        mutationIndices.removeAll(crossoverIndices);
        //收集待交叉的个体
        crossedIndividuals.clear();
        for (Integer i : crossoverIndices) {
            crossedIndividuals.add(selectedIndividuals.get(i));
        }
        //收集待变异的个体
        mutationIndividuals.clear();
        for (Integer i : mutationIndices) {
            mutationIndividuals.add(selectedIndividuals.get(i));
        }
        //交叉
        crossedIndividuals = crossover.getCrossoverResult(crossedIndividuals);
        //System.out.println("交叉得到的个体");
        //outputSolution(crossedIndividuals);
        mutationIndividuals = mutation.getMutationResult(mutationIndividuals);
        //System.out.println("变异得到的个体");
        //outputSolution(mutationIndividuals);
        //交叉与变异的汇总到一起
        crossedIndividuals.addAll(mutationIndividuals);
        //System.out.println("这是新一代");
        //outputSolution(crossedIndividuals);
        //过滤重复个体,确保只评价不重复的个体
        removeDuplicated(crossedIndividuals);
        return crossedIndividuals;
    }

    private void removeDuplicated(List<Individual> candidate) {
        List<Individual> duplicated = new LinkedList<>();//暂存重复的个体
        HashSet<DecisionVariable> tem = new HashSet<>();
        for (Individual ind : candidate) {
            if (tem.contains(ind.getDecisionVariable())) {
                duplicated.add(ind);
            } else {
                tem.add(ind.getDecisionVariable());
            }
        }
        duplicated.forEach((ind) -> {
            candidate.remove(ind);
        });
    }

    @Override
    public String getName() {
        return "Genetic Algorithm";
    }

    private void outputSolution(List<Individual> individuals) {
        for (Individual ind : individuals) {
            System.out.printf("%6.4f;", ind.getDecisionVariable().getGeneCodes()[0]);
        }
        System.out.println("");
    }
}
