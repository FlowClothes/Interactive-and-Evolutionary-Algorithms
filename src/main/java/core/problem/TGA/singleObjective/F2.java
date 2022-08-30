package core.problem.TGA.singleObjective;

import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.DecisionVariables.GenecodeType;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class F2 extends Problem<DoubleDecisionVariable> {
//最大值是x取(-2.048,-2.048)时的值,3905.9262268415996
    @Override
    public F2 init(int dimension) {
       super.init(dimension); this.dimension=dimension;
        stopFitness = new double[]{3905.92f};
        variableProperties = new double[][]{
            {2.048f, -2.048f, 0.01f}, 
            {2.048f, -2.048f, 0.01f}};
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
        inputedIndividual.getDecisionVariable().setFitness(new double[]{ (100 * Math.pow(
                Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2) 
                        - inputedIndividual.getDecisionVariable().getGeneCodes()[1], 
                2) + Math.pow(1 - inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2))});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return  FactoryProblems.getName(2, dimension);
    }
    
        @Override
    public void generateBackground() {
        //这是一决策变量是2维的函数
    }
}
