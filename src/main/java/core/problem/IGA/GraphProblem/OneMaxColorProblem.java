package core.problem.IGA.GraphProblem;

import core.problem.DecisionVariables.GenecodeType;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class OneMaxColorProblem extends Problem {

    @Override
    public OneMaxColorProblem init(int dimension) {
        super.init(this.dimension);maxFitness = new double[]{0};
        stopFitness = new double[]{0};
//        variableProperties=new double[3][3];
        individualLength = 24;////RBG共3个颜色，每位有8位二进制组成
//        for (int j = 0; j < 3; j++) {
//            variableProperties[j][2] = 0.00001f;
//            variableProperties[j][1] = -1;
//            variableProperties[j][0] = 1;
//        }
        variableSplit = new int[]{0, 8, 16, 24};
        this.dimension=3;
        
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
    }

    @Override
    public boolean isIECProblem() {
        return true;
    }
    
    @Override
    public String getCodeScope() {
        return "01";
    }
}
