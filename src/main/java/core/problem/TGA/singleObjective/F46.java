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
public class F46 extends Problem<DoubleDecisionVariable> {
//未旋转和平移

    @Override
    public F46 init(int dimension) {
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
        for (int i = 0; i < dimension - 1; i++) {
            fitness += gxy(inputedIndividual.getDecisionVariable().getGeneCodes()[i], inputedIndividual.getDecisionVariable().getGeneCodes()[i + 1]);
        }
        fitness += gxy(inputedIndividual.getDecisionVariable().getGeneCodes()[dimension - 1], inputedIndividual.getDecisionVariable().getGeneCodes()[0]);
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});
    }

    private double gxy(double x, double y) {
        return 0.5
                + (Math.pow(Math.sin(Math.sqrt(x * x + y * y)), 2) - 0.5)
                / Math.pow(1 + 0.001 * (x * x + y * y), 2);
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(46, dimension);
    }

    @Override
    public void generateBackground() {
    }

}
