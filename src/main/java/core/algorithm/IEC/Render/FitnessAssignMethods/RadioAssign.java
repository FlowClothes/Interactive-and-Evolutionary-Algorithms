package core.algorithm.IEC.Render.FitnessAssignMethods;

import core.controllers.ControlParameters;
import core.GUI.LabelName;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
//import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class RadioAssign extends BaseFitnessAssignMethod {
    JRadioButton[] jb = {new JRadioButton(), new JRadioButton(), new JRadioButton(), new JRadioButton(), new JRadioButton(), new JRadioButton(), new JRadioButton(), new JRadioButton(),};
    ButtonGroup bg = new ButtonGroup();
    float[] fitnessArray={8f,7f,6f,5f,4f,3f,2f,1f};

    public RadioAssign(JPanel parent) {
        super(parent);
        this.initComponents();
    }

    private void initComponents() {
        JPanel jrPane = new JPanel(new GridLayout(4, 2));
        Font ft = new Font("楷体", Font.PLAIN, 9);
        for (int i = 0; i < jb.length; i++) {
            jb[i]=new JRadioButton(LabelName.fitnessGradation[i]);
            jb[i].setFont(ft);
            bg.add(jb[i]);
            jrPane.add(jb[i]);
        }
        jb[3].setSelected(true);
        this.setLayout(new BorderLayout());
        this.add(jrPane, BorderLayout.CENTER);
    }

    @Override
    protected void calFitness() {
        //ButtonModel bm=bg.getSelection();
        for (int i = 0; i < this.jb.length; i++) {
            if (this.jb[i].isSelected()) {
                this.fitness = this.fitnessArray[i];
                break;
            }
        }
        return;
    }

    @Override
    public void repaintme() {
        this.jb[3].setSelected(true);
        this.validate();
        this.parentPane.validate();
    }

}
