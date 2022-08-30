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
public class F30 extends Problem<DoubleDecisionVariable> {

    @Override
    public F30 init(int dimension) {
         super.init(dimension);this.dimension = dimension;
        stopFitness = new double[]{0};
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i] = new double[]{50, -50,0.0001};
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = 0, tem1 = 0, tem2 = 0, fitness1 = 0, fitness2 = 0;
        for (int i = 0; i < dimension - 1; i++) {
            tem2 = inputedIndividual.getDecisionVariable().getGeneCodes()[i] - 1;
            tem2 = tem2 * tem2;
            tem1 = 1 + Math.pow(Math.sin(3 * Math.PI * inputedIndividual.getDecisionVariable().getGeneCodes()[i + 1]), 2);
            fitness1 += tem1 * tem2;
            fitness2 += F29.uxi(inputedIndividual.getDecisionVariable().getGeneCodes()[i], 5, 100, 4);
        }
        fitness2 += F29.uxi(inputedIndividual.getDecisionVariable().getGeneCodes()[dimension - 1], 10, 100, 4);//最后一维
        fitness = 0.1 * (Math.pow(Math.sin(3 * Math.PI * inputedIndividual.getDecisionVariable().getGeneCodes()[0]), 2)
                + fitness1
                + Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[dimension - 1] - 1, 2)
                * (1 + Math.pow(
                        Math.sin(2 * Math.PI * inputedIndividual.getDecisionVariable().getGeneCodes()[dimension - 1]),
                         2)));
        fitness = fitness + fitness2;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(30, dimension);
    }
}
