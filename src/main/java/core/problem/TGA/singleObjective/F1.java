package core.problem.TGA.singleObjective;

import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class F1 extends Problem<DoubleDecisionVariable> {
//De Jong F1,最大值78.64319648437504

    @Override
    public F1 init(int dimension) {
        super.init(dimension);
        this.dimension = dimension;
        stopFitness = new double[]{78.64319648437504};
        variableProperties = new double[][]{
            {5.12f, -5.12f, 0.001f},
            {5.12f, -5.12f, 0.001f},
            {5.12f, -5.12f, 0.001f}
        };
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = 0;
        for (int i = 0; i < inputedIndividual.getDecisionVariable().getGeneCodes().length; i++) {
            fitness += Math.pow((inputedIndividual).getDecisionVariable().getGeneCodes()[i], 2);
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{fitness});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(1, dimension);
    }

    @Override
    public void generateBackground() {
        //这是一决策变量是3维的函数
        yListData.get(0).clear();
        xListData.get(0).clear();
        double step = (this.getVariableProperties()[0][0] - this.getVariableProperties()[0][1]) / 300;
        for (double i = this.getVariableProperties()[0][1]; i < this.getVariableProperties()[0][0]; i = i + step) {//300个数据显示，应该足够了
            xListData.get(0).add(i);
            yListData.get(0).add(Math.pow(i, 2));
        }
    }

}
