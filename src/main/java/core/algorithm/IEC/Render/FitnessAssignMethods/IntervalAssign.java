package core.algorithm.IEC.Render.FitnessAssignMethods;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class IntervalAssign extends BaseFitnessAssignMethod {

    TextSlider[] ts = {new TextSlider(), new TextSlider()};

    public IntervalAssign(JPanel parent) {
        super(parent);
        this.initComponents();
    }

    private void initComponents() {
        Font ft = new Font("楷体", Font.PLAIN, 12);
        JLabel instruction = new JLabel("适应值区间为:"),from=new JLabel("从"), to = new JLabel("到");
        instruction.setFont(ft);
        to.setFont(ft);
        JPanel temPanel=new JPanel(),temPanel1 = new JPanel(), temPanel2 = new JPanel(),temPanel3=new JPanel();
        temPanel.setLayout(new BorderLayout());
        temPanel1.setLayout(new BorderLayout());
        temPanel2.setLayout(new BorderLayout());
        temPanel3.setLayout(new BorderLayout());
        temPanel1.add(instruction, BorderLayout.WEST);
        temPanel2.add(from, BorderLayout.WEST);
        temPanel2.add(ts[0], BorderLayout.CENTER);
        temPanel3.add(to, BorderLayout.WEST);
        temPanel3.add(ts[1], BorderLayout.CENTER);
        temPanel.add(temPanel1,BorderLayout.NORTH);
        temPanel.add(temPanel2,BorderLayout.CENTER);
        temPanel.add(temPanel3,BorderLayout.SOUTH);
        this.setLayout(new BorderLayout());
        this.add(temPanel, BorderLayout.CENTER);
    }

    @Override
    protected void calFitness() {
        this.fitness = (float)(ts[0].getValue()+ts[0].getValue())/2f;
        return;
    }

    @Override
    public void repaintme() {
        this.ts[0].repaint();
        this.ts[1].repaint();
        this.validate();
        this.parentPane.validate();
    }
}
