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
public class F5 extends Problem<DoubleDecisionVariable> {

    @Override
    public F5 init(int dimension) {
        super.init(dimension);stopFitness = new double[]{500.0000002f};
        dimension = 2;
        this.dimension = dimension;
        variableProperties = new double[][]{{65.536f, -65.536f, 0.01f}, {65.536f, -65.536f, 0.01f}}; 
        return this;
    }
    int[][] aMatrix = {
        {-32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32, -32, -16, 0, 16, 32},
        {-32, -32, -32, -32, -32, -16, -16, -16, -16, -16, 0, 0, 0, 0, 0, 16, 16, 16, 16, 16, 32, 32, 32, 32, 32}
    };

    @Override
    public void evaluate(Individual inputedIndividual) {

        double tem1, tem2 = 0;
        for (int j = 0; j < 25; j++) {
            tem1 = 0;
            for (int i = 0; i < 2; i++) {
                tem1 += Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[i] - aMatrix[i][j], 6);
            }
            tem2 += 1 / (j + 1 + tem1);
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{(1f / (tem2 + 0.002f))});//F5最小值为0.998，最大值约为500，平均值约为473
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return  FactoryProblems.getName(5, dimension);
    }

    @Override
    public void generateBackground() {
        //这是一决策变量是2维的函数
    }
}
