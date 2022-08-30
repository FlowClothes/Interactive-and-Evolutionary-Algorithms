package core.problem.DecisionVariables;

import java.util.List;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public interface DecisionVariable extends Cloneable, Comparable
{

    //不同于Individuals，不包含适应值
    public Object clone();

    @Override
    public int compareTo(Object o);

    public String getX();

    public String getGeneralGenecodeString();//返回基因串，如果是二进制，则返回二进制，如果是实数数组，则返回实数数组对应的字符串

    public int getSolutionNumberDistance(DecisionVariable decisionVariable);//求当前解与这个解中间有多少个个体，如果是浮点类型，则看距离是精度的多少倍；如果是字符串，则看字母的差距

    //GeneCodes针对的最实数编码
    public double[] getGeneCodes();

    public void setGeneCodes(double[] genecodes);

    public GenecodeType getGenecodeType();

    public String getGenecodesString();

    public void setGenecodesString(String genecodesString);

    public abstract List<DecisionVariable> crossover(DecisionVariable decisionVariable);

    public abstract DecisionVariable mutate(int mutateStep, int mutationNumber);

    public abstract DecisionVariable chaosMutate(int mutateStep, int mutationNumber);

    public DecisionVariable initVariable();

    public double[] getFitness();

    public void setFitness(double[] fitness);

    public String getFitnessString();

    public double getDistance(DecisionVariable another);

    public double getDistanceWithFitness(DecisionVariable another);
}
