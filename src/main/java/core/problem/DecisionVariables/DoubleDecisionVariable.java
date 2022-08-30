package core.problem.DecisionVariables;

import core.algorithm.FactoryAlgorithm;

import java.util.LinkedList;
import java.util.List;

import core.tools.MyMath.Logistic;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import core.problem.FactoryProblems;
import core.tools.MyMath.RandomGenerator;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class DoubleDecisionVariable extends AbstractDecisionVariable implements Cloneable, Comparable
{

    public DoubleDecisionVariable(double[] genecodes)
    {
        this.geneCodes = genecodes;
    }

    public DoubleDecisionVariable()
    {

    }

    @Override
    public boolean equals(Object obj)
    {
        if (null == obj || !(obj instanceof DoubleDecisionVariable))
        {
            return false;
        }
        if (obj == this)
        {
            return true;
        }
        //每个决策变量都相等，才相等;而没考虑适应值
        int i = 0;
        DoubleDecisionVariable tem = (DoubleDecisionVariable) obj;
        for (int j = 0; j < getGeneCodes().length; j++)
        {
            if (tem.getGeneCodes()[j] == this.getGeneCodes()[j])
            {
                i++;
            }
            else
            {
                break;
            }
        }
        return i == getGeneCodes().length;
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 61).
                append(this.getGeneCodes()).
                toHashCode();
    }

    @Override
    public GenecodeType getGenecodeType()
    {
        return GenecodeType.DOUBLECODE;//表示实数编码
    }

    public double getAdjusted(double value, int location, double[][] scope)
    {
        while (value > scope[location][0] || value < scope[location][1])
        {
            if (value > scope[location][0])
            {//超过了上限
                value = scope[location][0] - RandomGenerator.nextDouble();
            }
            if (value < scope[location][1])
            {//低于了下限
                value = scope[location][1] + RandomGenerator.nextDouble();
            }
        }
        return value;
    }

    @Override
    public String getX()
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < getGeneCodes().length; i++)
        {
            result.append(getGeneCodes()[i]).append(",");
        }
        return result.substring(0, result.length() - 1);
    }

    @Override
    public String getGeneralGenecodeString()
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < geneCodes.length; i++)
        {
            result.append(geneCodes[i]);
        }
        return result.substring(0, result.length() - 1);
    }

    @Override
    public int getSolutionNumberDistance(DecisionVariable individual)
    {
        //返回传入的个体与当前个体之间有多少个个体数目
        //将被用于调节exploer和exploit的平衡。例如淘汰个体数目，对于适应值高的个体，中间个体数目应该少些，即少淘汰一些，以增加exploit；而对于适应低的个体，中间个体数目应该多些，即多淘汰一些，以增加exploer
        int result = 1;
        for (int i = 0; i < this.getGeneCodes().length; i++)
        {
            //对于二维来说，是面积；3维来说，是体积；高维，超体积
            result *= Math.abs(this.getGeneCodes()[i] - ((DoubleDecisionVariable) individual).getGeneCodes()[i]) / FactoryProblems.currentProblem.getVariableProperties()[i][2];//VariableProperties()[i][2]决定了对搜索空间分割的精度precision
        }
        return result;
    }

    @Override
    public List<DecisionVariable> crossover(DecisionVariable crossed)
    {
        List<DecisionVariable> result = new LinkedList<>();
        if (this.equals(crossed))
        {//派来交叉的编码完全一样,求助于变异
            result.add(this.mutate(1, FactoryProblems.currentProblem.getDimension()));
            result.add(crossed.mutate(1, FactoryProblems.currentProblem.getDimension()));
            return result;
        }
        //两个编码不一样
        //定义两个结果的clone
        DecisionVariable individual1 = (DoubleDecisionVariable) this.clone();
        DecisionVariable individual2 = (DoubleDecisionVariable) crossed.clone();
        //修改其基因编码
        double[][] crossresult = swapDoubleArray(individual1.getGeneCodes(), individual2.getGeneCodes());
        individual1.setGeneCodes(crossresult[0]);
        individual2.setGeneCodes(crossresult[1]);
        //把结果加入
        result.add(individual1);
        result.add(individual2);
        return result;
    }

    public double[][] swapDoubleArray(double[] currentDoubleArray0, double[] currentDoubleArray1)
    {

        int doubleLength = currentDoubleArray0.length;//基因变量个数
        double[][] result = new double[2][doubleLength];//2表示交叉后得到两个个体
        if (doubleLength > 1)
        {
            int temInt = RandomGenerator.getRandom(0, doubleLength);
            if (temInt == 0)
            {
                temInt++;
            }
            //先拷贝前半部分
            System.arraycopy(currentDoubleArray0, 0, result[0], 0, temInt);
            System.arraycopy(currentDoubleArray1, 0, result[1], 0, temInt);
            //再拷贝后半部分
            System.arraycopy(currentDoubleArray0, temInt, result[1], temInt, currentDoubleArray0.length - temInt);
            System.arraycopy(currentDoubleArray1, temInt, result[0], temInt, currentDoubleArray1.length - temInt);//

        }
        else
        {//决策变量只有一个,//验证结果在这两个数值的中间
            double max = Math.max(currentDoubleArray0[0], currentDoubleArray1[0]);
            double min = Math.min(currentDoubleArray0[0], currentDoubleArray1[0]);
            if (max == min)
            {//不进行任何操作，因为两者相等
                result[0] = currentDoubleArray0;
                result[1] = currentDoubleArray1;
            }
            else
            {//这种方法只适合于凸问题
                double error = max - min;
                result[0][0] = min + RandomGenerator.nextDouble() * error;
                result[1][0] = max - RandomGenerator.nextDouble() * error;
            }
        }
        return result;
    }

    @Override
    public DecisionVariable mutate(int mutateStep, int mutationNumber)
    {
        DecisionVariable result = this.clone();
        if (this.getGeneCodes().length > 2)
        {
            //首先确定变异的决策变量
            double[] temDoubleArray = new double[this.getGeneCodes().length];//不能直接引用this.getGeneCodes，因为那样就变成引用了
            System.arraycopy(this.getGeneCodes(), 0, temDoubleArray, 0, this.getGeneCodes().length);
            for (int i = 0; i < mutationNumber; i++)
            {
                int muteLocation = (int) RandomGenerator.getRandom(0, temDoubleArray.length - 1);
                double rand = RandomGenerator.nextDouble();
                temDoubleArray[muteLocation] = this.getGeneCodes()[muteLocation] + rand * mutateStep;
            }
            for (int i = 0; i < FactoryProblems.currentProblem.getVariableProperties().length; i++)
            {
                temDoubleArray[i] = this.getAdjusted(temDoubleArray[i], i, FactoryProblems.currentProblem.getVariableProperties());
            }
            result.setGeneCodes(temDoubleArray);//不用设置，也会改变，因为数组是引用传递
        }
        else
        {// 只有一个让第一个进行变异,如果有两个决策变量，则随机选择一个
            double[] temDoubleArray = new double[this.getGeneCodes().length];//不能直接引用this.getGeneCodes，因为那样就变成引用了
            System.arraycopy(this.getGeneCodes(), 0, temDoubleArray, 0, this.getGeneCodes().length);
            if (this.geneCodes.length == 1)
            {
                double rand = RandomGenerator.nextDouble();
                temDoubleArray[0] = this.getGeneCodes()[0] + rand * mutateStep;
                temDoubleArray[0] = this.getAdjusted(temDoubleArray[0], 0, FactoryProblems.currentProblem.getVariableProperties());
            }
            else
            {
                int i = RandomGenerator.nextDouble() < 0.5 ? 0 : 1;
                double rand = RandomGenerator.nextDouble();
                temDoubleArray[i] = this.getGeneCodes()[i]
                        + rand * mutateStep;
                temDoubleArray[i] = this.getAdjusted(temDoubleArray[i], 0, FactoryProblems.currentProblem.getVariableProperties());
            }
            result.setGeneCodes(temDoubleArray);
        }
        return result;
    }

    @Override
    public DecisionVariable chaosMutate(int mutateStep, int mutationNumber)
    {
        if (Logistic.getInitNumber() == 0)
        {
            Logistic.setInitNumber();
        }

        DecisionVariable result = this.clone();
        if (this.getGeneCodes().length > 2)
        {
            //首先确定变异的决策变量
            double[] temDoubleArray = new double[this.getGeneCodes().length];//不能直接引用this.getGeneCodes，因为那样就变成引用了
            System.arraycopy(this.getGeneCodes(), 0, temDoubleArray, 0, this.getGeneCodes().length);
            for (int i = 0; i < mutationNumber; i++)
            {
                int muteLocation = (int) RandomGenerator.getRandom(0, temDoubleArray.length - 1);
                temDoubleArray[muteLocation] = this.getGeneCodes()[muteLocation] + Logistic.getLogistic(mutateStep);
            }
            for (int i = 0; i < FactoryProblems.currentProblem.getVariableProperties().length; i++)
            {
                temDoubleArray[i] = this.getAdjusted(temDoubleArray[i], i, FactoryProblems.currentProblem.getVariableProperties());
            }
            result.setGeneCodes(temDoubleArray);//不用设置，也会改变，因为数组是引用传递
        }
        else
        {// 只有一个让第一个进行变异,如果有两个决策变量，则随机选择一个
            double[] temDoubleArray = new double[this.getGeneCodes().length];//不能直接引用this.getGeneCodes，因为那样就变成引用了
            System.arraycopy(this.getGeneCodes(), 0, temDoubleArray, 0, this.getGeneCodes().length);
            if (this.geneCodes.length == 1)
            {
                temDoubleArray[0] = this.getGeneCodes()[0] + Logistic.getLogistic(mutateStep);
                temDoubleArray[0] = this.getAdjusted(temDoubleArray[0], 0, FactoryProblems.currentProblem.getVariableProperties());
            }
            else
            {
                int i = RandomGenerator.nextDouble() < 0.5 ? 0 : 1;
                double rand = RandomGenerator.nextDouble();
                temDoubleArray[i] = this.getGeneCodes()[i]
                        + rand * mutateStep;
                temDoubleArray[i] = this.getAdjusted(temDoubleArray[i], 0, FactoryProblems.currentProblem.getVariableProperties());
            }
            result.setGeneCodes(temDoubleArray);
        }
        return result;
    }

    @Override
    protected char flip(char ch, int mutateStep)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DoubleDecisionVariable initVariable()
    {
        DoubleDecisionVariable result = new DoubleDecisionVariable();
        double[][] randScope = FactoryProblems.currentProblem.getVariableProperties();
        double[] tem = new double[randScope.length];
        for (int j = 0; j < randScope.length; j++)
        {
            tem[j] = RandomGenerator.getRandom(randScope[j][0], randScope[j][1]);
        }
        result.setGeneCodes(tem);
        return result;
    }

    @Override
    public double getDistance(DecisionVariable another)
    {
        EuclideanDistance ed = new EuclideanDistance();
        return ed.compute(this.getGeneCodes(), another.getGeneCodes());
    }

    @Override
    public DoubleDecisionVariable clone()
    {
        DoubleDecisionVariable result = new DoubleDecisionVariable();
        double[] localgeneCodes = new double[this.getGeneCodes().length];
        System.arraycopy(this.getGeneCodes(), 0, localgeneCodes, 0, this.getGeneCodes().length);
        result.setGeneCodes(localgeneCodes);
        if (null != getMutationScope())
        {//这个主要是针对人脸上点
            double[][] localmutationScope = new double[getMutationScope().length][getMutationScope()[0].length];
            for (int i = 0; i < getMutationScope().length; i++)
            {
                System.arraycopy(getMutationScope()[i], 0, localmutationScope[i], 0, getMutationScope()[0].length);
            }
            result.setMutationScope(localmutationScope);
        }
        return result;
    }

    @Override
    public int compareTo(Object o)
    {
        for (int i = 0; i < this.getGeneCodes().length; i++)
        {
            if (this.getGeneCodes()[i] > ((DoubleDecisionVariable) o).getGeneCodes()[i])
            {
                return 1;
            }
            else if (this.getGeneCodes()[i] < ((DoubleDecisionVariable) o).getGeneCodes()[i])
            {
                return -1;
            }
        }
        return 0;//两者相等，是同一个解
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < geneCodes.length; i++)
        {
            sb.append(geneCodes[i]).append(",");
        }
        return sb.toString();
    }
}
