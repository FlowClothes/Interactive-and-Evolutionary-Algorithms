package core.problem.IGA.GraphProblem;

import core.problem.DecisionVariables.GenecodeType;
import core.problem.Problem;


/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class FashionProblem extends Problem {

    @Override
    public FashionProblem init(int dimension) {
        super.init(this.dimension);individualLength = 12;
        this.dimension=4;
        variableSplit = new int[]{0, 4, 8, 12};
        
        return this;
    }

    @Override
    public boolean isIECProblem() {
        return true;
    }
    
    @Override
    public String getName() {
        return "Fashion Design";
    }
}
