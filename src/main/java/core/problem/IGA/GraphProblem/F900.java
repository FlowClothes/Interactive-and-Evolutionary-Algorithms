package core.problem.IGA.GraphProblem;

import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.Problem;
import core.problem.IGA.Graph.Face.Base.FaceVertexData;
import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class F900 extends Problem {//类似于FloatProblem，但是由于某些点的x,y,z坐标值要相等，所以需要调整，而FloatProblem并不解决该问题，因此，这里单独定义这个问题

    @Override
    public F900 init(int dimension) {
        super.init(this.dimension);
        FaceVertexData.init();
        setDecisionVariable(new DoubleDecisionVariable());
        setVariableProperties(FaceVertexData.getRandomScope());
        //会自动根据所选人脸部分计算变异范围
        individualLength = getVariableProperties().length;
        this.dimension = individualLength;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCodeScope(String codeScope) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return "Face";
    }

    @Override
    public void generateBackground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
