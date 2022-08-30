package core.algorithm.IEC.Render.FitnessAssignMethods;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class FuzzyAssign extends BaseFitnessAssignMethod {

    TextSlider ts = new TextSlider();
    ButtonGroup bg = new ButtonGroup();
    JRadioButton[] jb = {new JRadioButton(), new JRadioButton(), new JRadioButton()};
    String[] FLExpression = {"大约", "接近", "很接近"};

    public FuzzyAssign(JPanel parent) {
        super(parent);
        this.initComponents();
    }

    private void initComponents() {
        Font ft = new Font("楷体", Font.PLAIN, 12);
        JLabel instruction = new JLabel("中心值:");
        JLabel instruction1 = new JLabel("模糊语言:");
        instruction.setFont(ft);
        instruction1.setFont(ft);
        JPanel temPanel = new JPanel(),temPanel1=new JPanel();
        temPanel1.setLayout(new GridLayout(1,4));
        temPanel1.add(instruction1);
        for (int i = 0; i < jb.length; i++) {
            jb[i].setText(FLExpression[i]);
            jb[i].setFont(ft);
            bg.add(jb[i]);
            temPanel1.add(jb[i]);
        }
        jb[0].setSelected(true);
        temPanel.setLayout(new BorderLayout());
        temPanel.add(instruction, BorderLayout.WEST);
        temPanel.add(ts, BorderLayout.CENTER);
        temPanel.add(temPanel1,BorderLayout.SOUTH);
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
        this.ts.repaint();
        this.validate();
        this.parentPane.validate();
    }
}
