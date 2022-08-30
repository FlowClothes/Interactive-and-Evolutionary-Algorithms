package core.problem.IGA.Graph.OneMaxColor;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */

import core.problem.IGA.GraphProblem.OneMaxColorProblem;
import core.problem.IGA.GraphPanel.BaseIGAGraphIndividual;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author hgs
 */
public class OneMaxColor extends BaseIGAGraphIndividual {
 //   JPanel main = new JPanel();
 //   JPanel main = new JPanel ();
    JLabel rgb=new JLabel();
    int[] colorCode=new int[3];
    private String[] imgAdsNum;
    public OneMaxColor() {
        imgAdsNum=new String[FactoryProblems.currentProblem.getVariableSplit().length];
    }

    @Override
    public void paintme() {
        this.commonPaint();
        this.setLayout(new BorderLayout());
      //  main.setLayout(new BorderLayout());
       // this.setBackground(Color.white);
        this.add(box,BorderLayout.SOUTH);
   //     this.add(main,BorderLayout.CENTER);
        this.add(rgb,BorderLayout.CENTER);
        this.setVisible(true);
        this.validate();
    }

    @Override
    public void repaintme(Individual ind) {
        this.setInd(ind);
        this.commonPaint();
   //     this.main.validate();
        this.validate();
    }

    private void commonPaint() {//rep用来检查是否是初次绘制
        Problem problem=FactoryProblems.currentProblem;
            for (int i = 0; i < problem.getVariableSplit().length - 1; i++) {
            imgAdsNum[i] = this.getInd().getDecisionVariable().getGenecodesString().substring(problem.getVariableSplit()[i], problem.getVariableSplit()[i + 1]);
        }
    //    BinaryEnDeCoder code=new BinaryEnDeCoder();
        for(int i=0;i<FactoryProblems.currentProblem.getGsuCount();i++){
            colorCode[i]=Integer.parseInt(imgAdsNum[i],2);
        }
         Color c=new Color(colorCode[0],colorCode[1],colorCode[2]);
        this.setBackground(c);
        rgb.setText("("+colorCode[0]+","+colorCode[1]+","+colorCode[2]+")");
        this.setComboxvalue();
    }
}
