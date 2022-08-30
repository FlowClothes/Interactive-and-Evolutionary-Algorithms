package core.problem.TGA.singleObjective;

import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.DecisionVariables.GenecodeType;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class F27 extends Problem<DoubleDecisionVariable> {

    @Override
    public F27 init(int dimension) {
        super.init(dimension);this.dimension = dimension;
        stopFitness = new double[]{0};
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i] = new double[]{30, -30,0.0001};
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = 0,tem1=0, tem2=0;
        //原文在公式中可能有错，把最后一维放在循环中了
        for (int i = 0; i < dimension-1 ; i++) {
           tem1+=inputedIndividual.getDecisionVariable().getGeneCodes()[i]*inputedIndividual.getDecisionVariable().getGeneCodes()[i];
           tem2+=Math.cos(2*Math.PI*inputedIndividual.getDecisionVariable().getGeneCodes()[i]);
        }
        fitness=20*Math.exp(-0.02*Math.sqrt(tem1/dimension))+Math.exp(tem2/dimension)-20-Math.E;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{fitness});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(27, dimension);
    } 
    
}
