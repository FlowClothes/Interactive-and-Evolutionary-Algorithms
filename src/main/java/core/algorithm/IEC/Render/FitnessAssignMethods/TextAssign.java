package core.algorithm.IEC.Render.FitnessAssignMethods;

import core.GUI.LabelName;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class TextAssign extends BaseFitnessAssignMethod {

    TextSlider ts = new TextSlider();

    public TextAssign(JPanel parent) {
        super(parent);
        this.initComponents();
    }

    private void initComponents() {
        Font ft = new Font("", Font.PLAIN, 12);
        JLabel instruction = new JLabel(LabelName.HowSatisfactory);
        instruction.setFont(ft);
        JPanel temPanel = new JPanel();
        temPanel.setLayout(new BorderLayout());
        temPanel.add(instruction, BorderLayout.WEST);
        temPanel.add(ts, BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        this.add(temPanel, BorderLayout.CENTER);
    }

    @Override
    protected void calFitness() {
        this.fitness = ts.getValue();
        return;
    }

    @Override
    public void repaintme() {
        this.ts.setDefaultValue();
       // this.ts.repaint();
        this.validate();
    }
}
