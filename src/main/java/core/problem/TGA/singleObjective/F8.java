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
public class F8 extends Problem<DoubleDecisionVariable> {
//该函数详细描述可以参见http://benchmarkfcns.xyz/benchmarkfcns/goldsteinpricefcn.html
    //最大值为-3，位置是(x1,x2)=(0,-1)
    @Override
    public F8 init(int dimension){
        super.init(dimension);stopFitness = new double[]{-3.0f};
        this.dimension=dimension;
        variableProperties=new double[][]{{2,-2,0.01f},{2,-2,0.01f}}; 
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
        double tem11 = Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0] + inputedIndividual.getDecisionVariable().getGeneCodes()[1] + 1, 2);
        double tem12 = 19 - 14 * inputedIndividual.getDecisionVariable().getGeneCodes()[0] + 3 * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2) - 14 * inputedIndividual.getDecisionVariable().getGeneCodes()[1] + 6 * inputedIndividual.getDecisionVariable().getGeneCodes()[0] * inputedIndividual.getDecisionVariable().getGeneCodes()[1] + 3 * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2);
        double tem1 = 1 + tem11 * tem12;
        double tem21 = Math.pow(2 * inputedIndividual.getDecisionVariable().getGeneCodes()[0] - 3 * inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2);
        double tem22 = 18 - 32 * inputedIndividual.getDecisionVariable().getGeneCodes()[0] + 12 * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[0], 2) + 48 * inputedIndividual.getDecisionVariable().getGeneCodes()[1] - 36 * inputedIndividual.getDecisionVariable().getGeneCodes()[0] * inputedIndividual.getDecisionVariable().getGeneCodes()[1] + 27 * Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[1], 2);
        double tem2 = 30 + tem21 * tem22;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{- (tem1 * tem2)}); 
    }
    @Override
    public boolean isIECProblem() {
        return false;
    } 

    @Override
    public String getName() {
       return FactoryProblems.getName(8, dimension);
    }
        @Override
    public void generateBackground() {
        //这是一决策变量是2维的函数
    }
}
