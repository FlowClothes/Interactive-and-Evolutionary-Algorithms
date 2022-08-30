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
public class F6 extends Problem<DoubleDecisionVariable> {
//在这个链接中有更详细的说明https://www.cs.unm.edu/~neal.holts/dga/benchmarkFunction/schafferf6.html
    @Override
    public F6 init(int dimension){
        super.init(dimension);stopFitness = new double[]{0.000000001f};
        this.dimension=dimension;//2
        variableProperties=new double[][]{{100f,-100f,0.01f},{100f,-100f,0.01f}}; 
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
        double a = (Math.pow(Math.sin(Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2) + Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2)), 2) - 0.5);
        double b =  (1.0 + 0.001 * (Math.pow(Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2) + Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2), 2)));
        inputedIndividual.getDecisionVariable().setFitness(new double[]{- (0.5 + a / b)}); 
    }
    @Override
    public boolean isIECProblem() {
        return false;
    } 

    @Override
    public String getName() {
      return FactoryProblems.getName(6, dimension);
    }
        @Override
    public void generateBackground() {
        //这是一决策变量是2维的函数
    }
}
