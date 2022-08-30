/*
 * 该类管理各种IGA优化的图形显示
 * 
 */
package core.problem.IGA.GraphPanel;

import core.problem.FactoryProblems;
import core.problem.Individual;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class FactoryIGAGraph {


    public BaseIGAGraphIndividual getIndividualPanel(Individual ind) {
        BaseIGAGraphIndividual indiGraphPanel = FactoryProblems.getIndGraphPanel();
        indiGraphPanel.setInd(ind);
        indiGraphPanel.paintme();
        return indiGraphPanel;
    }
}
