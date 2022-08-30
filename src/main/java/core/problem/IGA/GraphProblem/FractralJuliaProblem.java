package core.problem.IGA.GraphProblem;

import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author hgs07
 */
public class FractralJuliaProblem extends Problem {
    @Override
    public FractralJuliaProblem init(int dimension) {
        //individualLength = 12;
        super.init(this.dimension);variableSplit = new int[]{0, 6, 12, 15, 18, 21, 24, 27, 30};
        maxFitness = new double[]{0};
        stopFitness = new double[]{1000};
        variableProperties = new double[][]{{1.2f, -1.2f, 0.00001f}, {1.2f, -1.2f, 0.00001f}, {255, 0, 1.0f}, {255, 0, 1.0f}, {255, 0, 1.0f}, {255, 0, 1.0f}, {255, 0, 1.0f},{255, 0, 1.0f}};
        this.dimension=8;
        
        return this;
    }
    //ControlParameters.individualLength = 12;

    @Override
    public void evaluate(Individual inputedIndividual) {
    }

    @Override
    public boolean isIECProblem() {
        return true;
    }

    @Override
    public String getCodeScope() {
        return "0123456789";
    }

    @Override
    public String getName() {
        return "FractralJulia";
    }

    @Override
    public void generateBackground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
