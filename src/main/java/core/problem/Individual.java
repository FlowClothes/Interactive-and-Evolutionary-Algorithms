package core.problem;

import core.problem.DecisionVariables.DecisionVariable;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 继承该类，有两个方法必须要实现：equal()和getClone()
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class Individual implements Comparable<Individual>, Cloneable {

    DecisionVariable decisionVariable;
    //protected double[] fitness;//可以是一维的，也可以是多维的,具体维数要等到执行时具体指定
    private int mutationNumber;
    private int index;//在粒子群算法等群智能算法中，要使用某个粒子的编号
    private int explo_ra_itationFact = 1;
    //实现无级的（需要为double类型，这里采用了int，就是有级的）exploration和exploitation的切换
    //其默认值1表示不进行exploeration或exploitation的调节
    //当这个值大于1时，则是在执行explorer

    public Individual(DecisionVariable decisionVariable) {
        this.decisionVariable = decisionVariable;
        mutationNumber = FactoryProblems.currentProblem.getDimension();
    }

    @Override
    public int compareTo(Individual o) {//求最大化的问题
        int result = 0;
        if (null == this.decisionVariable.getFitness()) {//还没有赋予适应值，不可比，则对比决策变量，如果决策变量相等，则相等
            return this.decisionVariable.compareTo(o.getDecisionVariable());
        }
        for (int i = 0; i < this.decisionVariable.getFitness().length; i++) {
            if (this.decisionVariable.getFitness()[i] < o.decisionVariable.getFitness()[i]) {
                result += -1;
            } else if (this.decisionVariable.getFitness()[i] > o.decisionVariable.getFitness()[i]) {
                result += 1;
            }
        }
        if (result == this.decisionVariable.getFitness().length) {//所有this的目标值都优于o的目标值
            return 1;
        } else if (result == -this.decisionVariable.getFitness().length) {//所有this的目标值都劣于o的目标值
            return -1;
        } else {//两者适应值相等，检查
            return this.decisionVariable.compareTo(o.getDecisionVariable());
        }
    }

    public double getDistanceWithFitness(Individual another) {
        double result = 0.0;
        for (int i = 0; i < this.decisionVariable.getFitness().length; i++) {
            result += Math.pow(this.decisionVariable.getFitness()[i] - another.decisionVariable.getFitness()[i], 2);
        }
        return Math.sqrt(result);
    }

    @Override
    public boolean equals(Object object) {
        boolean result = false;
        if (null == object || !(object instanceof Individual)) {
            return false;
        }
        if (object == this) {
            return true;
        }
        //先比较适应值，如果相等，则比较决策变量，如果决策变量也相等，则相等
        //比起维数动辄几十维的决策变量空间，适应值空间的维数往往较低
        int equalCount = 0;
        if (null != this.decisionVariable.getFitness()) {
            for (int i = 0; i < this.decisionVariable.getFitness().length; i++) {
                if (this.decisionVariable.getFitness()[i] != ((Individual) object).decisionVariable.getFitness()[i]) {
                    result = false;
                    return result;
                } else {
                    equalCount++;
                }
            }

            if (equalCount == this.decisionVariable.getFitness().length) {
                result = true;
            }
            if (result) //适应值相等，下面看决策变量是否相等
            {
                result = this.getDecisionVariable().equals(((Individual) object).getDecisionVariable());
            }
        }else{//适应值不可用，则用决策变量
             result = this.getDecisionVariable().equals(((Individual) object).getDecisionVariable());
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return new HashCodeBuilder(17, 61).
                append(71 * hash + Objects.hashCode(this.decisionVariable)).
                append(71 * hash + Arrays.hashCode(this.decisionVariable.getFitness())).
                toHashCode();
    }

    @Override
    public Individual clone() {
        Individual result = null;
        try {
            super.clone();
            result = new Individual((DecisionVariable) decisionVariable.clone());
            if (null != this.decisionVariable.getFitness()) {
                double[] localfitness = new double[this.decisionVariable.getFitness().length];//注意现在是一维的，可以这样
                System.arraycopy(this.decisionVariable.getFitness(), 0, localfitness, 0, localfitness.length);
                result.decisionVariable.setFitness(localfitness);
            }
            result.setExplo_ra_itationFact(explo_ra_itationFact);
            result.setMutationNumber(mutationNumber);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Individual.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int getExplo_ra_itationFact() {
        return explo_ra_itationFact;
    }

    public void setExplo_ra_itationFact(int explo_ra_itationFact) {
        this.explo_ra_itationFact = explo_ra_itationFact;
    }

    public DecisionVariable getDecisionVariable() {
        return this.decisionVariable;
    }

    public void setDecisionVariable(DecisionVariable decisionVariable) {
        this.decisionVariable = decisionVariable;
    }

    public Individual mutate() {
        Individual ind = this.clone();
        ind.getDecisionVariable().mutate(explo_ra_itationFact, mutationNumber);
        return ind;
    }

    public int getMutationNumber() {
        return mutationNumber;
    }

    public void setMutationNumber(int mutationNumber) {
        this.mutationNumber = mutationNumber;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        String tem = "";
        for (int i = 0; i < decisionVariable.getFitness().length; i++) {
            tem += decisionVariable.getFitness()[i];
        }
        return decisionVariable.toString() + "\t" + tem;
    }
}
