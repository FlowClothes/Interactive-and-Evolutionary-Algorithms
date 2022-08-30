package bigPouWithWRS_RS;

import core.algorithm.AbstractAlgorithm;
import core.algorithm.FactoryAlgorithm;
import core.entrance.MainCmdRuning;
import core.problem.FactoryProblems;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class BigPopMainCmdRuning extends MainCmdRuning {

    @Override
    public String getSetupFileName() {
        return "src/bigPouWithWRS_RS/ControlParameters.conf";
    }

    public static void main(String[] args) {
        BigPopMainCmdRuning entrance = new BigPopMainCmdRuning();
        entrance.mainBody();
        entrance.mainrun();
        //  System.exit(0);
    }

    @Override
    public void mainrun() { 
        this.setup();
        FactoryProblems.initProblem(FactoryProblems.problemNum,AbstractAlgorithm.genecodeType);
        FactoryAlgorithm.currentAlgorithm = new BigPouAlgorithm();
        ((BigPouAlgorithm)(FactoryAlgorithm.currentAlgorithm)).setProbability();
        FactoryAlgorithm.currentAlgorithm.init();
        new BigPopConnection4SetAndRunning().running();
    }
}
