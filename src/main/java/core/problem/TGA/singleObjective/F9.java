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
public class F9 extends Problem<DoubleDecisionVariable> {
//参考https://www.mathworks.com/matlabcentral/fileexchange/75043-smdo-method-with-benchmark-functions/?s_tid=LandingPageTabfx

    @Override
    public F9 init(int dimension) {
        super.init(dimension);
        stopFitness = new double[]{186f};//186.7309,共有18个全局最小点，也有760个局部极小点，当范围在[-10,10]时
        this.dimension = dimension;
        variableProperties = new double[][]{{2, -2, 0.001f}, {3, -3, 0.001f}};
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
        double tem1 = 0, tem2 = 0;
        for (int i = 0; i < 5; i++) {
            tem1 += (i + 1) * Math.cos((i + 2) * inputedIndividual.getDecisionVariable().getGeneCodes()[0] + i + 1);
            tem2 += (i + 1) * Math.cos((i + 2) * inputedIndividual.getDecisionVariable().getGeneCodes()[1] + i + 1);
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-(tem1 * tem2)});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(9, dimension);
    }

    @Override
    public void generateBackground() {
        //这是一决策变量是2维的函数
    }
}
