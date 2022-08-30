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
public class F13  extends Problem<DoubleDecisionVariable> {
    @Override
    public F13 init(int dimension) {
        super.init(dimension);this.dimension = dimension;
        stopFitness = new double[]{0};
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i] = new double[]{10, -10,0.0001};
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double sum=0, product=1;
        for (int i = 0; i < dimension; i++) {
            sum+=Math.abs(inputedIndividual.getDecisionVariable().getGeneCodes()[i]);
            product*=Math.abs(inputedIndividual.getDecisionVariable().getGeneCodes()[i]);
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-(sum+product)});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return  FactoryProblems.getName(13, dimension);
    }

    @Override
    public void generateBackground() {
        yListData.get(0).clear();
        xListData.get(0).clear();
       double step=(this.getVariableProperties()[0][0]-this.getVariableProperties()[0][1])/300;
        for (double i = this.getVariableProperties()[0][1]; i < this.getVariableProperties()[0][0]; i = i + step) {//300个数据显示，应该足够了
            xListData.get(0).add(i);
            yListData.get(0).add(2*Math.abs(i));
        }
    }
}
