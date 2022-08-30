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
public class F26 extends Problem<DoubleDecisionVariable> {

    @Override
    public F26 init(int dimension) {
        super.init(dimension);this.dimension = dimension;
        stopFitness = new double[]{0};
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i] = new double[]{5, -5,0.0001};
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是DoubleIndividual类型，即决策变量是浮点型
        double fitness = 0,tem1=0, temi=0;
        //原文在公式中可能有错，把最后一维放在循环中了
        tem1=Math.pow(Math.sin(3*Math.PI*inputedIndividual.getDecisionVariable().getGeneCodes()[0]),2);
        for (int i = 0; i < dimension-1 ; i++) {
           fitness+= (inputedIndividual.getDecisionVariable().getGeneCodes()[i]-1)*(inputedIndividual.getDecisionVariable().getGeneCodes()[i]-1)*(
                   1+Math.pow(Math.sin(3*Math.PI*inputedIndividual.getDecisionVariable().getGeneCodes()[i]+1),
                           2)
                   );
        }
        fitness=tem1
                +fitness
                +Math.pow(inputedIndividual.getDecisionVariable().getGeneCodes()[dimension-1]+1, 2)
                *(1+Math.pow(Math.sin(2*Math.PI*inputedIndividual.getDecisionVariable().getGeneCodes()[dimension-1]), 2));
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-fitness*Math.PI/this.dimension});//把求最小值问题转换为求最大值问题了
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
       return FactoryProblems.getName(26, dimension);
    }
    
}
