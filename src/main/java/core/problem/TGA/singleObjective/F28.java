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
public class F28 extends Problem<DoubleDecisionVariable> {

    @Override
    public F28 init(int dimension) {
        super.init(dimension);this.dimension = dimension;
        stopFitness = new double[]{0};
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i] = new double[]{5, -5,0.0001};
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = 0;
        //原文在公式中可能有错，把最后一维放在循环中了
        for (int i = 0; i < dimension ; i++) {
           fitness+=inputedIndividual.getDecisionVariable().getGeneCodes()[i]*inputedIndividual.getDecisionVariable().getGeneCodes()[i]-10*Math.cos(2*Math.PI*inputedIndividual.getDecisionVariable().getGeneCodes()[i]);
        }
        fitness=10*dimension+fitness;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
       return FactoryProblems.getName(28, dimension);
    }
    
}
