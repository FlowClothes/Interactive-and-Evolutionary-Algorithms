/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.algorithm.IEC.Render.FitnessAssignMethods;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class Eyetrack extends BaseFitnessAssignMethod {

    JTextField ts = new JTextField(10);

    public Eyetrack(JPanel parent) {
        super(parent);
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout());
        this.add(ts,BorderLayout.CENTER);
    }

    @Override
    protected void calFitness() {
        this.fitness = Integer.parseInt(ts.getText());
    }

    @Override
    public void repaintme() {
        this.ts.repaint();
        this.validate();
        this.parentPane.validate();
    }
}
