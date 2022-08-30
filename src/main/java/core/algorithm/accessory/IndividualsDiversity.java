package core.algorithm.accessory;

import java.util.TreeSet;
import core.problem.DecisionVariables.DecisionVariable;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Population;

/**
 *
 * @author 郝国生 HAO Guo-Sheng 给出个体的多样性统计
 * @param <T>要使用DecisionVariable的求距离方法
 */
public class IndividualsDiversity<T extends DecisionVariable> {

    //种群内任意两个体之间的最大距离
    public static double[] maxDistanceAmongIndividuals(TreeSet<Individual> individualList) {
        double minDistance = 0, averageDistance = 0, maxDistance = 0, tem = 0;
        DecisionVariable foreOne = null;//前面一个
        TreeSet<DecisionVariable> adapter = new TreeSet<>();
        individualList.forEach((individual) -> {
            adapter.add(individual.getDecisionVariable());
        });
        for (DecisionVariable decisionVariable : adapter) {
            if (foreOne == null) {//第1个
                foreOne = decisionVariable;
            } else {//求最小与平均的距离
                {
                    tem = decisionVariable.getDistance(foreOne);
                    averageDistance += tem;
                    if (tem < minDistance) {
                        minDistance = tem;
                    }
                }
                foreOne = decisionVariable;
            }
        }
        averageDistance = averageDistance / adapter.size();
        return new double[]{adapter.first().getDistance(adapter.last()), minDistance, averageDistance};
    }

    public static double hyperVolumeQuotient(Population population) {
        return population.getVolume() / FactoryProblems.currentProblem.getVolume();
    }
}
