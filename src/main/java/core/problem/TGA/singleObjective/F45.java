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
public class F45 extends Problem<DoubleDecisionVariable> {
//未旋转和平移

    @Override
    public F45 init(int dimension) {
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
        for (int i = 0; i < dimension; i++) {
            double zi = inputedIndividual.getDecisionVariable().getGeneCodes()[i] + 420.9687462275036;
            double gzi = 0;
            if (Math.abs(zi) <= 500) {
                gzi = zi * Math.sin(Math.pow(Math.abs(zi), 0.5));
            }
            if (zi > 500) {
                gzi = (500 - zi % 500) * Math.sin(Math.sqrt(Math.abs(500 - zi % 500))) - Math.pow(zi - 500, 2) / 10000 / dimension;
            }
            if (zi <- 500) {
                gzi = (-500 + Math.abs(zi) % 500) * Math.sin(Math.sqrt(Math.abs(-500 +zi % 500))) - Math.pow(zi + 500, 2) / 10000 / dimension;
            }
            fitness += gzi;
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{fitness - 418.0829 * dimension});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(45, dimension);
    }

    @Override
    public void generateBackground() {
    }
}
