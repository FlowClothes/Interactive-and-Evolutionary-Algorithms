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
public class F42 extends Problem<DoubleDecisionVariable> {
//参考原来的C语言代码，没有旋转与平移
    @Override
    public F42 init(int dimension) {
        super.init(dimension);stopFitness = new double[]{110};
        dimension = 16;
        this.dimension = dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][0] = 16384;
            variableProperties[i][1] = -16384;
            variableProperties[i][2] = 0.00001;
        } 
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
        int i, j, k, b;
        double sum = 0;
        double hilbert[][] = new double[10][10], y[][] = new double[10][10];// Increase matrix size if D > 100
        b = (int) Math.sqrt(dimension);
        for (i = 0; i < b; i++) {
            for (j = 0; j < b; j++) {
                hilbert[i][j] = 1. / (double) (i + j + 1);// Create a static Hilbert matrix
            }
        }
        for (j = 0; j < b; j++) {
            for (k = 0; k < b; k++) {
                y[j][k] = 0;
                for (i = 0; i < b; i++) {
                    y[j][k] += hilbert[j][i] * inputedIndividual.getDecisionVariable().getGeneCodes()[k + b * i];	// Compute matrix product H*x
                }
            }
        }
        for (i = 0; i < b; i++) {
            for (j = 0; j < b; j++) {
                if (i == j) {
                    sum += Math.abs(y[i][j] - 1);// Sum absolute value of deviations
                } else {
                    sum += Math.abs(y[i][j]);
                }
            }
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-sum});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(42, dimension);
    }

    @Override
    public void generateBackground() {
    }

}
