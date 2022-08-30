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
public class F36 extends Problem<DoubleDecisionVariable> {
//参见 http://www-optima.amp.i.kyoto-u.ac.jp/member/student/hedar/Hedar_files/TestGO_files/Page1488.htm
//在这里改出了一个修改版：https://www.sfu.ca/~ssurjano/hart4.html
    //maxf(x_i) = 3.86 for \mathbf{x} = [0.114,0.556,0.852].
    @Override
    public F36 init(int dimension) {
        super.init(dimension);dimension = 3;//系数矩阵a,p,c决定了其维数只能是3维
        stopFitness = new double[]{3.86278};
        this.dimension = dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][2] = 0.0001;
            variableProperties[i][1] = 0;
            variableProperties[i][0] = 1;
        }
        return this;
    }

    double a[][] = {{3, 10, 30}, {0.1, 10, 35}, {3, 10, 30}, {0.1, 10, 35}};
    double p[][] = {
        {0.3689, 0.117, 0.2673},
        {0.4699, 0.4387, 0.7470},
        {0.1091, 0.8732, 0.5547},
        {0.038150, 0.5743, 0.8828}};
    double c[] = {1, 1.2, 3, 3.2};

    @Override
    public void evaluate(Individual inputedIndividual) {
        double tem = 0;
        for (int i = 0; i < 4; i++) {
            double jvalue = 0;
            for (int j = 0; j < dimension; j++) {
                jvalue += a[i][j]
                        * Math.pow(
                                (inputedIndividual.getDecisionVariable().getGeneCodes()[j] - p[i][j]),
                                 2);
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
        return FactoryProblems.getName(36, dimension);
    }

    @Override
    public void generateBackground() {

    }

}
