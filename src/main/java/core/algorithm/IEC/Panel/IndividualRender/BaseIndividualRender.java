package core.algorithm.IEC.Panel.IndividualRender;

import javax.swing.JComboBox;
import core.problem.FactoryProblems;
import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class BaseIndividualRender extends javax.swing.JPanel {
    protected Individual ind;
    protected JComboBox box = new JComboBox();

    public BaseIndividualRender() {
        this.box.setLightWeightPopupEnabled(false);
        this.box.setMaximumRowCount(20);
    }

    public void paintme() {
    }

    public Individual getInd() {
        return ind;
    }

    public void setInd(Individual ind) {
        this.ind = ind;
    }

    public void repaintme(Individual ind) {
    }

    public void setComboxvalue() {
        this.box.removeAllItems();
        switch (FactoryProblems.currentProblem.getDecisionVariable().getGenecodeType()) {
            case BINARYCODE:
                for (int j = 0; j < FactoryProblems.currentProblem.getVariableSplit().length - 1; j++) {
                    this.box.addItem(ind.getDecisionVariable().getGenecodesString().substring(FactoryProblems.currentProblem.getVariableSplit()[j], FactoryProblems.currentProblem.getVariableSplit()[j + 1]));
                }
                break;
            case DOUBLECODE:
                for (double genecode : ind.getDecisionVariable().getGeneCodes()) {
                    this.box.addItem(genecode);
                }
                break;
            case NORMALFACECODE:
            case AFFINEFACECODE:
                for (int j = 0; j < FactoryProblems.currentProblem.getVariableProperties().length; j++) {
                    this.box.addItem(Double.toString(ind.getDecisionVariable().getGeneCodes()[j]));
                }
                break;
            default:
        }

        this.box.repaint();
        this.validate();
    }
}
