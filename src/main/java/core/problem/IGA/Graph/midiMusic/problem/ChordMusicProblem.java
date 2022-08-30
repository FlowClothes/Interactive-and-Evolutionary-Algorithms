package core.problem.IGA.Graph.midiMusic.problem;

import core.problem.DecisionVariables.GenecodeType;
import core.problem.DecisionVariables.StringDecisionVariable;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ChordMusicProblem extends Problem<StringDecisionVariable> {

    @Override
    public ChordMusicProblem init(int dimension) {
        super.init(dimension);
        this.dimension = dimension;
        maxFitness = new double[]{0};
        stopFitness = new double[]{0};
        individualLength = 24;//
        variableProperties = new double[300][3];
        variableSplit = new int[301];
        variableSplit[0] = 0;
        for (int i = 0; i < 300; i++) {//只对音调进化
            variableProperties[i][2] = 1f;
            variableProperties[i][1] = 0;
            variableProperties[i][0] = 127;
            variableSplit[i + 1] = variableSplit[i] + 7;//7在这里包括7位的音符编码（0~127）
        }
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
    public String getName() {
        return "ChordMusic";
    }

    @Override
    public void generateBackground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
