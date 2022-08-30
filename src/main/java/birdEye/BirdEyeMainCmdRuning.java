package birdEye;

import core.algorithm.AbstractAlgorithm;
import core.algorithm.FactoryAlgorithm;
import core.controllers.Connection4SetAndRunning;
import core.controllers.ControlParameters;
import core.entrance.MainCmdRuning;
import core.problem.FactoryProblems;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class BirdEyeMainCmdRuning extends MainCmdRuning {

    @Override
    public String getSetupFileName() {
        return "src/birdEye/ControlParameters.conf";
    }

    public static void main(String[] args) {
        new BirdEyeMainCmdRuning().mainBody();
        //  System.exit(0);
    }

    @Override
    public void mainrun() {
        //对每一种knowledge的情况作实验
        BirdEyeMainCmdRuning mainCmdRuning = new BirdEyeMainCmdRuning();
        FactoryProblems.initProblem(FactoryProblems.problemNum,AbstractAlgorithm.genecodeType);
        //从配置文件中读内容进来
        mainCmdRuning.setup();
        FactoryAlgorithm.currentAlgorithm = new BirdEyeAlgorithm();
        FactoryAlgorithm.currentAlgorithm.init();
        ControlParameters.fp.readFromFile(getSetupFileName());
        new Connection4SetAndRunning().running();
    }

}
