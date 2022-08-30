package core.algorithm.operator.Crossover;

import java.util.List;

import core.problem.Individual;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class FactoryCrossover {

    public static int crossoverType;//交叉算子类型
    public static int crossoverPointNum;//交叉点的个数，如单点交叉、两点交叉等
//    public static double crossProbability;//交叉概率

    public static Crossover getCrossover(int index) {
        crossoverType = index;
        Crossover result = null;
        switch (FactoryCrossover.crossoverType) {
            case 0:
                result = new RegularCrossover();
        }
        return result;
    }

    public static List<Individual> getRegularCrossoveredPopulation(List<Individual> inputtedIndividual) {
        RegularCrossover rc = new RegularCrossover();
        return rc.getCrossoverResult(inputtedIndividual);
    }

    public static String getName(int type) {
        switch (type) {
            case 0:
                return "RegularCrossover";
            default:
                return "RegularCrossover";
        }
    }
}
