package exclusion;

import core.algorithm.AbstractAlgorithm;
import core.algorithm.FactoryAlgorithm;
import core.algorithm.accessory.AdaptiveTricks;
import core.algorithm.accessory.HistoryIndividuals;
import core.controllers.Connection4SetAndRunning;
import core.controllers.ControlParameters;
import core.entrance.MainCmdRuning;
import core.problem.FactoryProblems;
import exclusion.algorithm.ExclusionAlgorithm;
import exclusion.algorithm.operator.filter.Enum4OffspringFilter;
import exclusion.algorithm.operator.filter.parentFilter.ParentFilter;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ExclusionMainCmdRuning  extends MainCmdRuning {

    @Override
    public String getSetupFileName() {
        return "src/exclusion/ControlParameters.conf";
    }

    public static void main(String[] args) {
        ExclusionMainCmdRuning exclusionMainCmdRuning=new ExclusionMainCmdRuning();
        exclusionMainCmdRuning.mainBody();
        exclusionMainCmdRuning.mainrun();
        //  System.exit(0);
    }

    @Override
    public void mainrun() {
        String[] knowledges = ControlParameters.fp.getValue("knowledge").split(",");
        for (String knowledgeValue : knowledges) {
            //对每一种knowledge的情况作实验
            Enum4OffspringFilter temKnowledge = Enum4OffspringFilter.valueOf(knowledgeValue);
            ExclusionMainCmdRuning mainCmdRuning = new ExclusionMainCmdRuning();
            FactoryProblems.dimensionNumber = Integer.parseInt(ControlParameters.fp.getValue("dimensionNumber"));
            FactoryProblems.initProblem(FactoryProblems.problemNum,AbstractAlgorithm.genecodeType);
            //从配置文件中读内容进来
            mainCmdRuning.setup();
            FactoryAlgorithm.currentAlgorithm = new ExclusionAlgorithm();
            FactoryAlgorithm.currentAlgorithm.init();
            ((ExclusionAlgorithm)FactoryAlgorithm.currentAlgorithm).setKnowledge(temKnowledge);
            ControlParameters.fp.readFromFile(getSetupFileName());
            Connection4SetAndRunning connection4SetAndRunning = new Connection4SetAndRunning();
            connection4SetAndRunning.running();
        }
    }
    @Override
    public void setup() {
        super.setup();
        //对于非界面运行情况，从文件中读取配置内容
        AbstractAlgorithm.upperBoundPopulationSize = Integer.parseInt(ControlParameters.fp.getValue("upperboundPopulationSize"));
        //是否对父代进行过滤选择
        ParentFilter.parentFilte = Boolean.valueOf(ControlParameters.fp.getValue("parentFilter"));
        ParentFilter.lifeAge = Integer.valueOf(ControlParameters.fp.getValue("lifeAge"));
        ControlParameters.birdEye = Boolean.valueOf(ControlParameters.fp.getValue("birdEye"));
        HistoryIndividuals.sizeOfParentRepository = Integer.parseInt(ControlParameters.fp.getValue("sizeOfParentRepository"));
        if (HistoryIndividuals.sizeOfParentRepository < 2 * AbstractAlgorithm.upperBoundPopulationSize) {//不能太小
            HistoryIndividuals.sizeOfParentRepository = 2 * AbstractAlgorithm.upperBoundPopulationSize;
        }
        HistoryIndividuals.sizeOfOffspringRepository = Integer.parseInt(ControlParameters.fp.getValue("sizeOfOffspringRepository"));
        AdaptiveTricks.upParentExclusionRadius = Double.parseDouble(ControlParameters.fp.getValue("upParentExclusionRadius"));
        AdaptiveTricks.lowParentExclusionRadius = Double.parseDouble(ControlParameters.fp.getValue("lowParentExclusionRadius"));
    }
}