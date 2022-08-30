package core.problem.TGA.singleObjective;

import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.DecisionVariables.GenecodeType;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;

/**
 * @author 郝国生  HAO Guo-Sheng
 */
public class F7 extends Problem<DoubleDecisionVariable> {

    @Override
    public F7 init(int dimension){
       super.init(dimension); stopFitness = new double[]{0.000001f};
        this.dimension=dimension;
        variableProperties=new double[][]{{100,-100,0.001f},{100,-100,0.001f}}; 
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
       double a = (Math.pow(Math.sin(Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2) - Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2)), 2) - 0.5);
        double b =  (1.0 + 0.001 * (Math.pow(Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2) + Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2), 2)));
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-(0.5 + a / b)}); 
    }
    @Override
    public boolean isIECProblem() {
        return false;
    } 

    @Override
    public String getName() {
        return FactoryProblems.getName(7, dimension);
    }
        @Override
    public void generateBackground() {
        //这是一决策变量是2维的函数
    }
}