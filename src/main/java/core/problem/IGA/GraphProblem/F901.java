package core.problem.IGA.GraphProblem;

import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.IGA.AffineMatrixPhenoType;
import core.problem.IGA.Graph.Face.Base.FaceControlParameters;
import core.problem.IGA.Graph.Face.Base.FaceVertexData;
import core.problem.Individual;
import core.problem.DecisionVariables.GenecodeType;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class F901 extends Problem {

    @Override
    public F901 init(int dimension) {
        super.init(this.dimension);FaceVertexData.init();
        setDecisionVariable(new DoubleDecisionVariable());
        individualLength = 77;
        this.dimension=individualLength;
        setVariableProperties(FaceVertexData.faceVertMuteScope);
        //split
        
        return this;
    }
    @Override
    public double [][] getVariableProperties(){
        AffineMatrixPhenoType myAff=FaceVertexData.facePart[FaceControlParameters.part].getAffineMatrix();
        double[][] randomScope=new double[myAff.getAffineMatrixValue().length][2];
        for (int i = 0; i < myAff.getAffineMatrixValue().length; i++) {
            randomScope[i][0] = myAff.getScope(i)[0];
            randomScope[i][1] =myAff.getScope(i)[1];
        }
        return randomScope;
    }

  @Override
    public void evaluate(Individual inputedIndividual) {
    }

    @Override
    public boolean isIECProblem() {
        return true;
    }

    @Override
    public String getName() {
        return "FaceAffine";
    }

    @Override
    public void generateBackground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
