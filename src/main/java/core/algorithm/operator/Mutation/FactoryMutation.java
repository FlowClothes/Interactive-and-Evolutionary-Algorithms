package core.algorithm.operator.Mutation;

import java.util.List;

import core.problem.Individual;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class FactoryMutation
{

    public static double mutationProbability;//变异概率
    public static int mutationType;//变异类型
    public static int mutationPointNum;//变异点数目

    public static Mutation getMutation(int index)
    {
        mutationType = index;
        Mutation result = null;
        switch (mutationType)
        {
            case 0:
                result = new RegularMutation();
                break;
            case 1:
                result = new ChaosMutation();
                break;
        }
        return result;
    }

    public static String getName(int type)
    {
        switch (type)
        {
            case 0:
                return "Regular Mutation";
            case 1:
                return "Chaos Mutation";
            default:
                return "Regular Mutation";
        }
    }

}
