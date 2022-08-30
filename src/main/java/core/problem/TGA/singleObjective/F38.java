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
public class F38 extends Problem<DoubleDecisionVariable> {
    //最优解取1/ci，当x取ai时

    double a[][] = {
        {4, 4, 4, 4},
        {1, 1, 1, 1},
        {8, 8, 8, 8},
        {6, 6, 6, 6},
        {3, 7, 3, 7}};
    double c[] = {0.1, 0.2, 0.2, 0.4, 0.4};

    @Override
    public F38 init(int dimension) {
        super.init(dimension);stopFitness = new double[]{100};
        dimension = 4;//a数组决定了是4维
        this.dimension = dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][0] = 10;
            variableProperties[i][1] = 0;
            variableProperties[i][2] = 0.00001;
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {

        double tem1, tem2 = 0;
        for (int i = 0; i < a.length; i++) {
            tem1 = 0;
            for (int j = 0; j < dimension; j++) {
                tem1 = Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[j] - a[i][j], 2);
                tem1 += c[i];
            }
            tem2 += 1 / tem1;
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{tem2});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(38, dimension);
    }

    @Override
    public void generateBackground() {
    }

}
