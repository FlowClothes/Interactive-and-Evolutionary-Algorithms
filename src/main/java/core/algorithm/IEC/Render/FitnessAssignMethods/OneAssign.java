package core.algorithm.IEC.Render.FitnessAssignMethods;

import core.controllers.ControlParameters;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class OneAssign extends BaseFitnessAssignMethod {

    JCheckBox jc = new JCheckBox();

    public OneAssign(JPanel parent) {
        super(parent);
        this.initComponents();
    }

    private void initComponents() {
        Font ft = new Font("楷体", Font.PLAIN, 9);
        jc.setText("最接近目标");
        jc.setFont(ft);
        jc.setSelected(false);
        this.setLayout(new BorderLayout());
        this.add(jc, BorderLayout.CENTER);
    }

    @Override
    protected void calFitness() {
            if (this.jc.isSelected()) {
                this.fitness = 1000;
        }
        return;
    }

    @Override
    public void repaintme() {
        this.jc.repaint();
        this.validate();
        this.parentPane.validate();
    }
}
