package core.problem.IGA.Graph.midiMusic.problem;

import core.problem.DecisionVariables.GenecodeType;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 * @author 鲍慧慧 BAO Hui-Hui
 */
public class RandMidiMusicProblem extends Problem {

    @Override
    public RandMidiMusicProblem init(int dimension) {
        super.init(dimension);
        this.dimension = dimension;
        maxFitness = new double[]{0};
        stopFitness = new double[]{0};
        variableProperties = new double[30][3];
        individualLength = 24;//
        variableSplit = new int[31];
        for (int i = 0; i < 30; i++) {
            variableProperties[i][2] = 1f;
            variableProperties[i][1] = 0;
            variableProperties[i][0] = 511;
            variableSplit[i + 1] = variableSplit[i] + 9;//9在这里包括6位的音符编码（48~83）和节拍（1/8，1/4、1/2,1,3/2,2,3,4）
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
        return "RandomMidiMusic";
    }

    @Override
    public void generateBackground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
