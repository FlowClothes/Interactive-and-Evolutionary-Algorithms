package core.problem.IGA.GraphPanel;

import core.controllers.ControlParameters;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import core.problem.Individual;
import core.problem.Problem;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 * @author 胡长霆 HU Chang-Ting
 */
public class FashionDesign extends BaseIGAGraphIndividual {
    int[] variableSplit = new int[]{0, 4, 8, 12};//领口、袖子和裙子共3部分内容，所以分割共3个间隔
    String[] imgAdsNum = new String[variableSplit.length];
    String[] imgAds = new String[variableSplit.length];
    JPanel main = new JPanel();
    JLabel[] img = new JLabel[4];
    ImageIcon[] icon = new ImageIcon[4];

    @Override
    public void paintme() {
        this.commonPaint(0);
        this.setLayout(null);
        main.setLayout(null);
        img[0].setBounds(92, 80, icon[0].getIconWidth(),icon[0].getIconHeight());
 //     System.out.println(icon[0].getIconHeight());
        img[1].setBounds(172, 79,icon[1].getIconWidth(),icon[1].getIconHeight());
        img[2].setBounds(123, 75, 49, 34);
        img[3].setBounds(99, 109, 94, 86);
        box.setBounds(35, 270, 285, 20);
        main.add(img[0]);
        main.add(img[1]);
        main.add(img[2]);
        main.add(img[3]);
        this.setLayout(null);
        this.add(main);
        this.add(box);
        main.setBounds(35, 20, 295, 250);
        main.setBackground(Color.white);
        this.setVisible(true);
        this.validate();
    }

    @Override
    public void repaintme(Individual ind) {
        this.setInd(ind);
        this.commonPaint(1);
        this.main.validate();
        this.validate();
    }

    private void commonPaint(int rep) {//rep用来检查是否是初次绘制
        for (int i = 0; i < variableSplit.length- 1; i++) {
            imgAdsNum[i] = this.getInd().getDecisionVariable().getGenecodesString().substring(variableSplit[i],variableSplit[i + 1]);
        }
        imgAds[0] = ControlParameters.resource + "img/fashion/sleeve/" + imgAdsNum[0] + ".jpg";
        imgAds[1] = ControlParameters.resource + "img/fashion/sleeve/" + imgAdsNum[0] + "r.jpg";
        imgAds[2] = ControlParameters.resource + "img/fashion/neckline/" + imgAdsNum[1] + ".jpg";
        imgAds[3] = ControlParameters.resource + "img/fashion/skirt/" + imgAdsNum[2] + ".jpg";
       
        for (int i = 0; i < 4; i++) {
            icon[i] = new ImageIcon(imgAds[i]);
            if (rep == 0) {
                img[i] = new JLabel(icon[i]);
            } else {
                img[i].setIcon(icon[i]);
                img[i].validate();
            }
        }
        this.setComboxvalue();
    }
}
