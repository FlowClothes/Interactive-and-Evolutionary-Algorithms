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
public class F41 extends Problem<DoubleDecisionVariable> {

    @Override
    public F41 init(int dimension) {
        super.init(dimension);stopFitness = new double[]{110};
        dimension = 9;
        this.dimension = dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][0] = 8192;
            variableProperties[i][1] = -8192;
            variableProperties[i][2] = 0.00001;
        }
        return this;
    }
    double d = 72.661;

    @Override
    public void evaluate(Individual inputedIndividual) {
        double u = 0, p1 = 0, v = 0, p2 = 0, p3 = 0;

        for (int i = 0; i < dimension; i++) {
            u += inputedIndividual.getDecisionVariable().getGeneCodes()[i] * Math.pow(1.2, dimension - i);
            v += inputedIndividual.getDecisionVariable().getGeneCodes()[i] * Math.pow(-1.2, dimension - i);
        }
        p1 = u < dimension ? Math.pow(u - d, 2) : 0;
        p2 = v < dimension ? Math.pow(v - d, 2) : 0;
        int stop = 32 * dimension;
        for (int k = 0; k < stop; k++) {
            double temWK = 0, temPK = 0;
            for (int j = 0; j < dimension; j++) {
                temWK += inputedIndividual.getDecisionVariable().getGeneCodes()[j] * Math.pow(2 * k / stop - 1, dimension - j);
            }
            temPK = temWK > 1 ? Math.pow(temWK - 1, 2) : (temWK < 1 ? Math.pow(temWK + 1, 2) : 0);
            p3 += temPK;
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-p1 - p2 - p3});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(41, dimension);
    }

    @Override
    public void generateBackground() {
    }

}
