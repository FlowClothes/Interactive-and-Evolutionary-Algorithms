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
public class F47 extends Problem<DoubleDecisionVariable> {
//未旋转和平移

    @Override
    public F47 init(int dimension) {
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
        double fitness = 0,tem1=0,tem2=0;
        for (int i = 0; i < inputedIndividual.getDecisionVariable().getGeneCodes().length; i++) {
            tem1+=inputedIndividual.getDecisionVariable().getGeneCodes()[i]*inputedIndividual.getDecisionVariable().getGeneCodes()[i];
            tem2=inputedIndividual.getDecisionVariable().getGeneCodes()[i];
        }
        fitness=Math.pow(Math.abs(tem1-dimension), 0.25)
                +(0.5*tem1+tem2)/dimension
                +0.5;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(47, dimension);
    }

    @Override
    public void generateBackground() {
    }
    
}
