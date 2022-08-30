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
public class F31 extends Problem<DoubleDecisionVariable> {

    @Override
    public F31 init(int dimension) {
        super.init(dimension);this.dimension = dimension;
        stopFitness = new double[]{0};
        variableProperties = new double[dimension][3];
        int tem = dimension * dimension;
        for (int i = 0; i < dimension; i++) {
            variableProperties[i] = new double[]{tem, -tem,0.0001};
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = 0, tem1 = 0, tem2 = 0, tem3 = 0;
        tem1 = inputedIndividual.getDecisionVariable().getGeneCodes()[0]  - 1;
        tem1 = tem1 * tem1;
        for (int i = 1; i < dimension ; i++) {
            tem3 = inputedIndividual.getDecisionVariable().getGeneCodes()[i] - 1;
            tem1 += tem3 * tem3;
            tem2 += inputedIndividual.getDecisionVariable().getGeneCodes()[i] * inputedIndividual.getDecisionVariable().getGeneCodes()[i - 1];
        }
        fitness = tem1 + tem2 + dimension * (dimension + 4) * (dimension - 1) / 6;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(31, dimension);
    }
}
