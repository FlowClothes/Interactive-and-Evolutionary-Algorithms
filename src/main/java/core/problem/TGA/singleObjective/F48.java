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
public class F48 extends Problem<DoubleDecisionVariable> {

    int dataSize = 1000;

    //已知的一元二次函数是y=x^2+2x+3，即最优解是1,2,3
    @Override
    public F48 init(int dimension) {
         super.init(dimension);stopFitness = new double[]{0};//偏差为0
        dimension = 3;
        this.dimension = dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][0] = 10;
            variableProperties[i][1] = -10;
            variableProperties[i][2] = 0.00001;
        }
        //完成训练数据的初始化+++++++++++++
        x = new double[dataSize][2];//分别保存x和x^2
        y = new double[dataSize];
        for (int i = 0; i < dataSize; i++) {
            x[i][0] = RandomGenerator.getRandom(-50, 50.0);//X
            x[i][1] = x[i][0] * x[i][0];
            y[i] = x[i][1]
                    + 2 * x[i][0] + 3;
            ysum += Math.abs(y[i]);
        }
        return this;
    }
    double[][] x;
    double[] y;
    double ysum;

    @Override
    public void evaluate(Individual inputedIndividual) {
        double fitness = 0;
        for (int i = 0; i < dataSize; i++) {
            fitness += Math.abs(inputedIndividual.getDecisionVariable().getGeneCodes()[0] * x[i][1]
                    + inputedIndividual.getDecisionVariable().getGeneCodes()[1] * x[i][0]
                    + inputedIndividual.getDecisionVariable().getGeneCodes()[2]);
        }
        fitness = Math.abs(ysum - fitness);//>=0
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});//- RandomGenerator.getRandom(0.000001, 0)
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(48, dimension);
    }

    @Override
    public void generateBackground() {
    }
}
