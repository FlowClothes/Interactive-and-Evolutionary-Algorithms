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
public class F29 extends Problem<DoubleDecisionVariable> {

    @Override
    public F29 init(int dimension) {
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
        double fitness = 0, tem1 = 0, tem2 = 0, y1 = 0, fitness1 = 0;
        tem2 = (inputedIndividual.getDecisionVariable().getGeneCodes()[0] + 1) / 4;
        y1 = tem2;
        for (int i = 0; i < dimension - 1; i++) {
            tem1 = tem2;
            tem2 = (inputedIndividual.getDecisionVariable().getGeneCodes()[i + 1] + 1) / 4;
            fitness += Math.pow(tem1, 2)
                    * (1 + Math.sin(Math.PI * (tem2 + 1)));
            fitness1 +=uxi(inputedIndividual.getDecisionVariable().getGeneCodes()[i], 10, 100, 4);
        }
        fitness1+=uxi(inputedIndividual.getDecisionVariable().getGeneCodes()[dimension-1], 10, 100, 4);//最后一维
        fitness = Math.PI * (fitness + tem2 * tem2 + 10 * Math.pow(Math.sin(Math.PI * (y1 + 1)), 2)+fitness1) / dimension;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});//把求最小值问题转换为求最大值问题了
    }

    public static double uxi(double xi, int a, int k, int m) {
        double result = 0;
        if (xi > a) {
            result = k * Math.pow((xi - a), m);
        } else if (xi <= a && xi >= -a) {
            result = 0;
        } else if (xi < -a) {
            result = k * Math.pow((-xi - a), m);
        }
        return result;
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(29, dimension);
    }

}
