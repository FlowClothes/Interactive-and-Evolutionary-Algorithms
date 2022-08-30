package core.problem.DecisionVariables;

import core.tools.MyMath.RandomGenerator;

import java.util.List;

/**
 * @author hgs07
 */
public abstract class AbstractDecisionVariable implements DecisionVariable, Cloneable, Comparable
{

    protected String genecodesString;
    protected double[] geneCodes;
    protected double[][] mutationScope;
    //保存获得的适应值，目的是在利用历史信息中使用，从而避免再次重复计算,又不影响面向决策变量的排序
    private double[] fitness;

    @Override
    public abstract int compareTo(Object o);

    @Override
    public double[] getGeneCodes()
    {
        return this.geneCodes;
    }

    @Override
    public void setGeneCodes(double[] genecodes)
    {
        if (null != genecodes && genecodes.length > 0)
        {
            this.geneCodes = new double[genecodes.length];
            System.arraycopy(genecodes, 0, this.geneCodes, 0, genecodes.length);
        }
    }

    @Override
    public abstract boolean equals(Object object);
    //hashCode();在具体的方法中

    @Override
    public abstract int hashCode();

    @Override
    public abstract GenecodeType getGenecodeType();

    @Override
    public String getGenecodesString()
    {
        return genecodesString;
    }

    @Override
    public void setGenecodesString(String genecodesString)
    {
        this.genecodesString = genecodesString;
    }

    @Override
    public abstract String getX();

    @Override
    public abstract DecisionVariable clone();

    public double[][] getMutationScope()
    {
        return mutationScope;
    }

    public void setMutationScope(double[][] mutationScope)
    {
        this.mutationScope = mutationScope;
    }

    @Override
    public abstract String getGeneralGenecodeString();

    @Override
    public abstract int getSolutionNumberDistance(DecisionVariable decisionVariable);

    @Override
    public abstract List<DecisionVariable> crossover(DecisionVariable decisionVariable);

    protected abstract char flip(char ch, int mutateStep);

    @Override
    public DecisionVariable mutate(int mutateStep, int mutationNumber)
    {
        //对于字符串和二进制的变异
        DecisionVariable result = this.clone();
        char[] tem = this.getGenecodesString().toCharArray();
        for (int i = 0; i < mutationNumber; i++)
        {
            int location = RandomGenerator.getRandom(1, tem.length);
            tem[location] = flip(tem[location], mutateStep);
        }
        result.setGenecodesString(String.valueOf(tem));
        return result;
    }

    @Override
    public DecisionVariable chaosMutate(int mutateStep, int mutationNumber)
    {
        return null;
    }

    @Override
    public double getDistanceWithFitness(DecisionVariable another)
    {
        double result = 0.0;
        for (int i = 0; i < this.getFitness().length; i++)
        {
            result += Math.pow(this.getFitness()[i] - another.getFitness()[i], 2);
        }
        return Math.sqrt(result);
    }

    @Override
    public double[] getFitness()
    {
        return fitness;
    }

    @Override
    public void setFitness(double[] fitness)
    {
        if (null != fitness)
        {
            this.fitness = new double[fitness.length];
            System.arraycopy(fitness, 0, this.fitness, 0, fitness.length);
        }
    }

    @Override
    public String getFitnessString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getFitness().length; i++)
        {
            sb.append(getFitness()[i]).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
