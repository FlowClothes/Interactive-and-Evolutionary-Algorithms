package exclusion;

import exclusion.algorithm.operator.filter.Enum4OffspringFilter;
import core.GUI.*;
import core.algorithm.FactoryAlgorithm;
import core.controllers.ControlParameters;
import core.controllers.Connection4SetAndRunning;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import core.problem.FactoryProblems;
import exclusion.algorithm.ExclusionAlgorithm;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ExclusionGUI2SetParameters extends GUI2SetParameters {

    private JRadioButton knowledge[];
    private JTextField delterValue, commonNumValue;

    public ExclusionGUI2SetParameters(JPanel parentpane, Connection4SetAndRunning connection4SetAndRunning) {
        super(parentpane, connection4SetAndRunning);
    }

    @Override
    public void createColleagues() {
        super.createColleagues();
        createKnowledgeColleague();
    }

    @Override
    protected void okButtonActionPerformed() {
        super.okButtonActionPerformed();
        ExclusionAlgorithm algorithm = (ExclusionAlgorithm) FactoryAlgorithm.currentAlgorithm;
        if (knowledge[0].isSelected()) {
            algorithm.setKnowledge(Enum4OffspringFilter.NoReappearOnThisAndLastPopulationKnowledge);
        } else if (knowledge[1].isSelected()) {//历史非最优个体不再出现
            algorithm.setKnowledge(Enum4OffspringFilter.NoReappearOnSearchSpaceKnowledge);
        }
        if (FactoryProblems.currentProblem.isIECProblem()) {
            ControlParameters.deltValue = Float.parseFloat(this.delterValue.getText());
            ControlParameters.commNum = Integer.parseInt(this.commonNumValue.getText());
        }
    }

    private void createKnowledgeColleague() {
        JPanel KnowledgePane = new JPanel(new GridLayout(2, 1));
        tabpaneParameters.add(LabelName.SetParametersGUI_Tabbed_KnowledgeBased, KnowledgePane);
        knowledge = new JRadioButton[LabelName.SetParametersGUI_knowledge.length];
        JPanel temP = new JPanel(new GridLayout(1, 2)), temP1 = new JPanel(new FlowLayout());
        temP.add(new JLabel(LabelName.SetParametersGUI_KnowledgeIDLabel));
        for (int i = 0; i < knowledge.length; i++) {//
            knowledge[i] = new JRadioButton(LabelName.SetParametersGUI_knowledge[i]);
            temP1.add(knowledge[i]);
            knowledge[i].setEnabled(true);//因为没有处理知识学习这一块的内容，所以这里暂时不处理
        }
        temP.add(temP1);
        KnowledgePane.add(temP);
        KnowledgePane.add(temP);
        temP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JPanel temP2 = new JPanel(new GridLayout(2, 2));
        if (FactoryProblems.currentProblem.isIECProblem()) {
            temP2.add(new JLabel(LabelName.SetParametersGUI_DelterValueLabel));
            delterValue = new JTextField("0.5");
            temP2.add(delterValue);
            temP2.add(new JLabel(LabelName.SetParametersGUI_CommonNumLabel));
            commonNumValue = new JTextField("2");
            temP2.add(commonNumValue);
            temP2.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            KnowledgePane.add(temP2);
        }
        tabpaneParameters.add(KnowledgePane);
    }

    @Override
    protected void addInputVerifiers() {
        if (FactoryProblems.currentProblem.isIECProblem()) {
            delterValue.setInputVerifier(new InputVerifier() {

                @Override
                public boolean verify(JComponent input) {
                    JTextField tf = (JTextField) input;
                    float tem = Float.parseFloat(tf.getText());
                    if (tem < 0) {
                        tf.setText("0");
                    } else {
                        if (tem > 0.8) {
                            tf.setText("0.5");
                        }
                    }
                    return true;
                }
            });
            commonNumValue.setInputVerifier(new InputVerifier() {

                @Override
                public boolean verify(JComponent input) {
                    JTextField tf = (JTextField) input;
//                if (ControlParameters.gsuCount < 2) {
//                    tf.setText("0");
//                    return true;
//                }
                    int tem = Integer.parseInt(tf.getText());
                    if (tem < 0) {
                        tf.setText("2");
                    }
//                else {
//                    if (tem > ControlParameters.gsuCount) {
//                        tf.setText("2");
//                    }
//                }
                    return true;
                }
            });
        }
    }
}
