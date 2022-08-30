package core.problem.IGA.GraphPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import core.problem.IGA.GraphProblem.FractralJuliaProblem;
import core.problem.Individual;
import core.problem.DecisionVariables.DecisionVariable;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 * @author 季君 JI Jun
 */
public class BaseFractual extends BaseIGAGraphIndividual {

    Julia j1;
    int rb, gb, bb;


    @Override
    public void paintme() {
        j1 = new Julia();
        DecisionVariable decisionVariable=this.getInd().getDecisionVariable();
        j1.setP(decisionVariable.getGeneCodes()[0]);
        j1.setQ(decisionVariable.getGeneCodes()[1]);
        j1.setR((int)decisionVariable.getGeneCodes()[2]);
        j1.setG((int) decisionVariable.getGeneCodes()[3]);
        j1.setB((int) decisionVariable.getGeneCodes()[4]);
        this.setRB((int) decisionVariable.getGeneCodes()[5]);
        this.setGB((int) decisionVariable.getGeneCodes()[6]);
        this.setBB((int) decisionVariable.getGeneCodes()[7]);
        this.setBackground(new Color(rb, gb, bb));
        this.setLayout(new BorderLayout());
        this.setComboxvalue();//将适应值加到box中,
        this.add(this.j1, BorderLayout.CENTER);
        this.add(box, BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createLineBorder(Color.blue));
        this.validate();
    }

    @Override
    public void repaintme(Individual ind) {
        this.setInd(ind);
        DecisionVariable decisionVariable=ind.getDecisionVariable();
        j1.setP(decisionVariable.getGeneCodes()[0]);
        j1.setQ(decisionVariable.getGeneCodes()[1]);//p,q从ind的genecodes中分解
        j1.setR((int) decisionVariable.getGeneCodes()[2]);
        j1.setG((int) decisionVariable.getGeneCodes()[3]);
        j1.setB((int) decisionVariable.getGeneCodes()[4]);
        this.setRB((int) decisionVariable.getGeneCodes()[5]);
        this.setGB((int) decisionVariable.getGeneCodes()[6]);
        this.setBB((int) decisionVariable.getGeneCodes()[7]);
        this.setBackground(new Color(rb, gb, bb));
        this.setComboxvalue();
        j1.repaintme(ind);
        this.validate();
    }

    void setRB(int rb) {
        this.rb = rb;
    }

    void setGB(int gb) {
        this.gb = gb;
    }

    void setBB(int bb) {
        this.bb = bb;
    }
}
