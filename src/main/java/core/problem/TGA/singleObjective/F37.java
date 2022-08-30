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
public class F37 extends Problem<DoubleDecisionVariable> {

    //参见：http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page1488.htm
    //maxf(x_i) = 3.32 for \mathbf{x} = [0.201,0.150,0.477,0.275,0.311,0.657].
    @Override
    public F37 init(int dimension) {
        super.init(dimension);dimension = 6;//系数矩阵a,p,c决定了其维数只能是6维
        stopFitness = new double[]{100};
        this.dimension = dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][2] = 0.0001;
            variableProperties[i][1] = 0;
            variableProperties[i][0] = 1;
        }
        return this;
    }

    double a[][] = {
        {10, 3, 17, 3.5, 1.7, 8},
        {0.05, 10, 17, 0.1, 8, 14},
        {3, 3.5, 1.7, 10, 17, 8},
        {17, 8, 0.05, 10, 0.1, 14}
    };
    double p[][] = {
        {0.1312, 0.1696, 0.5569, 0.0124, 0.8283, 0.5886},
        {0.2329, 0.4135, 0.8307, 0.3736, 0.1004, 0.9991},
        {0.2348, 0.1415, 0.3522, 0.2883, 0.3047, 0.6650},
        {0.4047, 0.8828, 0.8732, 0.5743, 0.1091, 0.0381}
    };
    double c[] = {1, 1.2, 3, 3.2};

    @Override
    public void evaluate(Individual inputedIndividual) {
        double tem = 0;
        for (int i = 0; i < 4; i++) {
            double jvalue = 0;
            for (int j = 0; j < dimension; j++) {
                jvalue += a[i][j] * Math.pow((inputedIndividual.getDecisionVariable().getGeneCodes()[j] - p[i][j]), 2);
            }
            tem += c[i] * Math.exp(-jvalue);
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{tem});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(37, dimension);
    }

    @Override
    public void generateBackground() {

    }

}
