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
public class F3 extends Problem<DoubleDecisionVariable> {
//最大值是各变量取5.12时的25

    @Override
    public F3 init(int dimension) {
        super.init(dimension);stopFitness = new double[]{25f};
        variableProperties = new double[][]{
            {5.12f, -5.12f, 0.01f}, 
            {5.12f, -5.12f, 0.01f}, 
            {5.12f, -5.12f, 0.01f}, 
            {5.12f, -5.12f, 0.01f}, 
            {5.12f, -5.12f, 0.01f}};
        this.dimension = dimension;
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
        double fitness = 0;
        for (int i = 0; i < inputedIndividual.getDecisionVariable().getGeneCodes().length; i++) {
            fitness += (int) Math.floor(inputedIndividual.getDecisionVariable().getGeneCodes()[i]);
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{fitness});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
       return  FactoryProblems.getName(3, dimension);
    }

    @Override
    public void generateBackground() {
        //这是一决策变量是5维的函数
    }
}
