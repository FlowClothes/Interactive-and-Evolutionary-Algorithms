package core.algorithm.operator.Crossover;

import java.util.*;
import core.problem.DecisionVariables.DecisionVariable;
import core.problem.Individual;
import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class RegularCrossover implements Crossover {

    private float crossoverProbability;
    private int crossoverNumber;

    @Override
    public List<Individual> getCrossoverResult(List<Individual> inputtedIndividual) {
        List<Individual> result = new LinkedList<>();
        int crossNumber = inputtedIndividual.size() / 2;
        boolean isOdd = (inputtedIndividual.size() % 2 != 0);
        //交叉前的个体
        int tem1 = 0, tem2 = 0;
        for (int i = 0; i < crossNumber; i++) {
            do {//不让交叉的两个体相同
                tem1 = RandomGenerator.getRandom(0, inputtedIndividual.size());
                tem2 = RandomGenerator.getRandom(0, inputtedIndividual.size());
            } while (tem1 == tem2);
            //一点交叉是产生一个随机数，进行一次交叉，多点交叉则产生多个随机数，进行多次一点交叉；
            //  if (RandomGenerator.nextDouble()<= FactoryCrossover.crossProbability) {
            //必然交叉,因为采用了交叉与变异竞争的方法
            List<DecisionVariable> crossedDecisionVariables = inputtedIndividual.get(tem1).getDecisionVariable().crossover(inputtedIndividual.get(tem2).getDecisionVariable());
           // System.out.println(inputtedIndividual.get(tem1).getDecisionVariable()+","+inputtedIndividual.get(tem2).getDecisionVariable()+"------>"+crossedDecisionVariables.get(0)+","+crossedDecisionVariables.get(1));
            inputtedIndividual.get(tem1).setDecisionVariable(crossedDecisionVariables.get(0));
            inputtedIndividual.get(tem2).setDecisionVariable(crossedDecisionVariables.get(1));
            result.add(inputtedIndividual.get(tem1));
            result.add(inputtedIndividual.get(tem2));
        }
        if (isOdd) {//再补充1个individual
            do {//不让交叉的两个体相同
                tem1 = RandomGenerator.getRandom(0, inputtedIndividual.size());
                tem2 = RandomGenerator.getRandom(0, inputtedIndividual.size());
            } while (tem1 == tem2);
            List<DecisionVariable> crossedDecisionVariables = inputtedIndividual.get(tem1).getDecisionVariable().crossover(inputtedIndividual.get(tem2).getDecisionVariable());
            inputtedIndividual.get(tem1).setDecisionVariable(crossedDecisionVariables.get(0));
            result.add(inputtedIndividual.get(tem1));
        }
        return result;
    }

    @Override
    public float getCrossoverProbability() {
        return crossoverProbability;
    }

    @Override
    public void setCrossoverProbability(float crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    @Override
    public int getCrossoverNumber() {
        return crossoverNumber;
    }

    @Override
    public void setCrossoverNumber(int crossoverNumber) {
        this.crossoverNumber = crossoverNumber;
    }
}
