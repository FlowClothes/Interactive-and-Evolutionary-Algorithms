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
public class F44 extends Problem<DoubleDecisionVariable> {
//未旋转和平移

    @Override
    public F44 init(int dimension) {
        super.init(dimension);stopFitness = new double[]{110};
        dimension = 10;
        this.dimension = dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][0] = 100;
            variableProperties[i][1] = -100;
            variableProperties[i][2] = 0.00001;
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
        double fitness = 0;
        int k_max = 20;
        double sum = 0, sum2 = 0, a = 0.5, b = 3.0;
        k_max = 20;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j <= k_max; j++) {
                sum += Math.pow(a, j) * Math.cos(2.0 * Math.PI * Math.pow(b, j) * (inputedIndividual.getDecisionVariable().getGeneCodes()[i] + 0.5));
                sum2 += Math.pow(a, j) * Math.cos(Math.PI * Math.pow(b, j));
            }
        }
        fitness = sum -dimension* sum2;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(44, dimension);
    }

    @Override
    public void generateBackground() {
    }

}
