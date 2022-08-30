package core.problem.IGA.Graph.OneMaxColor;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
import core.problem.IGA.GraphProblem.HSVOneMaxProblem;
import core.problem.IGA.GraphPanel.BaseIGAGraphIndividual;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import core.problem.Individual;

/**
 *
 * @author hgs
 */
public class HSVOneMax extends BaseIGAGraphIndividual {

    JLabel rgb = new JLabel();
    int hct, hct1, hct2;

      @Override
    public void paintme() {
        this.commonPaint();
        this.setLayout(new BorderLayout());
        this.add(box, BorderLayout.SOUTH);
        this.add(rgb, BorderLayout.CENTER);
        this.setVisible(true);
        this.validate();
    }

    @Override
    public void repaintme(Individual ind) {
        this.setInd(ind);
        this.commonPaint();
        this.validate();
    }

    private void commonPaint() {//rep用来检查是否是初次绘制
//        for (int i = 0; i < problem.getVariableSplit().length - 1; i++) {
//            imgAdsNum[i] = this.getInd().getGenecodesString().substring(problem.getVariableSplit()[i], problem.getVariableSplit()[i + 1]);
//        }
//        //    BinaryEnDeCoder code=new BinaryEnDeCoder();
//        for (int i = 0; i < problem.getGsuCount(); i++) {
//            colorCode[i] = Integer.parseInt(imgAdsNum[i], 2);
//        }
        //编码:H[0,360],S[0,1],V[0,1]
        double H = this.getInd().getDecisionVariable().getGeneCodes()[0];
        double S = this.getInd().getDecisionVariable().getGeneCodes()[1];
        double V = this.getInd().getDecisionVariable().getGeneCodes()[2];
        int R = 0, G = 0, B = 0;
        if (S == 0) {
            R = G = B = (int) V;
        } else {
            H /= 60;
        }
        int i = (int) H;
        double f = H - i;
        double a = V * (1 - S);
        double b = V * (1 - S * f);
        double c = V * (1 - S * (1 - f));
        switch (i) {
            case 0:
                R = (int)V;
                G = (int)c;
                B = (int)a;
                break;
            case 1:
                R = (int)b;
                G = (int)V;
                B = (int)a;
                break;
            case 2:
                R = (int)a;
                G = (int)V;
                B = (int)c;
                break;
            case 3:
                R = (int)a;
                G = (int)b;
                B = (int)V;
                break;
            case 4:
                R = (int)c;
                G = (int)a;
                B = (int)V;
                break;
            case 5:
                R = (int)V;
                G = (int)a;
                B = (int)b;
                break;
        }
        //显示还是通过RGB来显示
        Color color = new Color(R, G, B);
        this.setBackground(color);
        rgb.setText("(" + R + "," + G + "," + B + ")");
        this.setComboxvalue();
    }
}
