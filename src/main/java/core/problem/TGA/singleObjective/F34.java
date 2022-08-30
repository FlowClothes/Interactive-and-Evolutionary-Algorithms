package core.problem.TGA.singleObjective;

import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.DecisionVariables.GenecodeType;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;
import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class F34 extends Problem<DoubleDecisionVariable> {

    @Override
    public F34 init(int dimension) {
        super.init(dimension);this.dimension = dimension;
        stopFitness = new double[]{0};
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i] = new double[]{1.28, -1.28, 0.000001};
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = 0;
        for (int i = 0; i < dimension; i++) {
            fitness += (i + 1) * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[i], 4);
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-(fitness - RandomGenerator.nextDouble())});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(34, dimension);
    }

}
