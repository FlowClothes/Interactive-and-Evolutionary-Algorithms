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
public class F10 extends Problem<DoubleDecisionVariable> {
    //参见https://www.sfu.ca/~ssurjano/camel6.html
//用x[0]表示x,x[1]表示y

    @Override
    public F10 init(int dimension) {
        super.init(dimension);this.dimension = 2;
        stopFitness = new double[]{1.031628f};
        variableProperties = new double[][]{
            {2, -2, 0.0001f},
            {2, -2, 0.0001f}};
        return this;
    }
  
    @Override
    public void evaluate(Individual inputedIndividual) {
        double tem1 = (4 - 2.1 * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2) + Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 4) / 3) * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2);
        double tem2 = inputedIndividual.getDecisionVariable().getGeneCodes()[0] * inputedIndividual.getDecisionVariable().getGeneCodes()[1] + (-4 + 4 * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2)) * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2);
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-(tem1 + tem2)});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(10, dimension);
    }

    @Override
    public void generateBackground() {
        //这是一决策变量是2维的函数
    }
}
