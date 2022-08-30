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
public class F22 extends Problem<DoubleDecisionVariable> {
//这个与F7的名字虽然相同，但是函数却不一样

    @Override
    public F22 init(int dimension) {
        super.init(dimension);this.dimension = dimension;
        stopFitness = new double[]{0};
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i] = new double[]{100, -100,0.0001};
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = 0, sum = 0, sum1 = 0;
        for (int i = 0; i < dimension - 1; i++) {
            sum = inputedIndividual.getDecisionVariable().getGeneCodes()[i] * inputedIndividual.getDecisionVariable().getGeneCodes()[i]
                    + inputedIndividual.getDecisionVariable().getGeneCodes()[i + 1] * inputedIndividual.getDecisionVariable().getGeneCodes()[i + 1];
            sum1 = Math.pow(sum, 0.25)
                    * (Math.pow(
                            Math.sin(50 * Math.pow(sum, 0.1)),
                            2)
                    + 1);
            fitness += sum1;
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(22, dimension);
    }

    @Override
    public void generateBackground() {
        yListData.get(0).clear();
        xListData.get(0).clear();
        double step = (this.getVariableProperties()[0][0] - this.getVariableProperties()[0][1]) / 300;
        for (double i = this.getVariableProperties()[0][1]; i < this.getVariableProperties()[0][0]; i = i + step) {//300个数据显示，应该足够了
            xListData.get(0).add(i);
            yListData.get(0).add(1000000 * (i + 0.5) * (i + 0.5));
        }
    }

}
