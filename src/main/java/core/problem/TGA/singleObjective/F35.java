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
public class F35 extends Problem<DoubleDecisionVariable> {
//有3个最优解：（-3,142,2.275), (3.142,2.275),(9.425,2.245)

    @Override
    public F35 init(int dimension) {
        super.init(dimension);
        dimension = 2;
        this.dimension = dimension;
        stopFitness = new double[]{100};
        variableProperties = new double[dimension][3];
        variableProperties[0] = new double[]{10, -5, 0.0001};
        variableProperties[1] = new double[]{0, 15, 0.0001};
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1] - 5.14 / (4 * Math.PI) * inputedIndividual.getDecisionVariable().getGeneCodes()[0] * inputedIndividual.getDecisionVariable().getGeneCodes()[0] + 5 / Math.PI * inputedIndividual.getDecisionVariable().getGeneCodes()[0] - 6, 2)
                + 10 * (1 - 0.125 * Math.PI) * Math.cos(inputedIndividual.getDecisionVariable().getGeneCodes()[0]) + 10;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(35, dimension);
    }

}
