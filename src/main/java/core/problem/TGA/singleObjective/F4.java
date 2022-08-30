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
public class F4 extends Problem<DoubleDecisionVariable> {
//http://infinity77.net/global_optimization/test_functions_nd_K.html
    //maxf(x_i) = 0.00030748610 for \mathbf{x} = [0.192833, 0.190836, 0.123117, 0.135766].
    @Override
    public F4 init(int dimension) {
        super.init(dimension);stopFitness = new double[]{0.00030748610};
        this.dimension=dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][2] = 0.0001;
            variableProperties[i][1] = -5.0;
            variableProperties[i][0] = 5.0;
        } 
        return this;
    }
    
double a[] = {4.0, 2.0, 1, 1/2, 1/4, 1/8, 1/10, 1/12, 1/14, 1/16};
double b[] = {0.1957, 0.1947, 0.1735, 0.1600, 0.0844, 0.0627, 0.0456, 0.0342, 0.0323, 0.0235, 0.0246};
    @Override
    public void evaluate(Individual inputedIndividual) {
        double tem = 0;
        for (int i = 0; i < 10; i++) {
            tem +=  Math.pow(a[i]-inputedIndividual.getDecisionVariable().getGeneCodes()[0]*(b[i]*b[i]+b[i]*inputedIndividual.getDecisionVariable().getGeneCodes()[1])
                    /(b[i]*b[i]+b[i]*inputedIndividual.getDecisionVariable().getGeneCodes()[2]+inputedIndividual.getDecisionVariable().getGeneCodes()[3])
                    , 2);
        }
        inputedIndividual.getDecisionVariable().setFitness(new double[]{ -tem});
    }
    @Override
    public boolean isIECProblem() {
        return false;
    } 

    @Override
    public String getName() {
       return  FactoryProblems.getName(4, dimension);
    }
        @Override
    public void generateBackground() {
       
    }
}
