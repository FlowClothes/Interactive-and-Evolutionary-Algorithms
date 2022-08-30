package core.problem.IGA.Graph.midiMusic;

import core.problem.IGA.GraphPanel.BaseIGAGraphIndividual;
import core.tools.Transfer2_8_10_16.BinaryToDecimalX;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.*;
import core.problem.FactoryProblems;
import core.problem.IGA.Graph.midiMusic.problem.RandMidiMusicProblem;
import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class RandomMidiMusic extends BaseIGAGraphIndividual implements ActionListener {

    String[] musicCodeUnit;
    int[] musicCodeUnitInteger;//存储音符编码
    int[] musicCodeUnitTime;//存储音符节拍
    String[] imgAds;
    // public static int NOTESIZE = 100;
    //public static int i = 0;
    public int[][] tem;//tem[][0]音调tem[][2]节拍
    JButton play, save;
    JPanel p;

    public RandomMidiMusic() {
        play = new JButton("Play");
        save = new JButton("save");
        p = new JPanel();
        musicCodeUnit = new String[FactoryProblems.currentProblem.getVariableSplit().length - 1];
        musicCodeUnitInteger = new int[FactoryProblems.currentProblem.getVariableProperties().length];//存储音符编码
        musicCodeUnitTime = new int[FactoryProblems.currentProblem.getVariableProperties().length];//存储音符节拍
        imgAds = new String[FactoryProblems.currentProblem.getVariableSplit().length];
        // public static int NOTESIZE = 100;
        //public static int i = 0;
        tem = new int[FactoryProblems.currentProblem.getVariableProperties().length][3];//tem[][0]音调tem[][2]节拍
    }

    @Override
    public void paintme() {

        this.setBackground(Color.white);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        p.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));
        p.add(play);
        p.add(save);
        this.add("Center", p);
        play.addActionListener(this);
        save.addActionListener(this);
        this.setComboxvalue();
        this.add(box, BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.validate();

    }

    @Override
    public void repaintme(Individual ind) {
        this.setInd(ind);
        this.commonPaint(1);//重新显示基因编码值
        this.validate();
    }

    private void commonPaint(int rep) {
        //对基因编码分解

        for (int i = 0; i < FactoryProblems.currentProblem.getVariableSplit().length - 1; i++) {
            musicCodeUnit[i] = this.getInd().getDecisionVariable().getGenecodesString().substring(FactoryProblems.currentProblem.getVariableSplit()[i], FactoryProblems.currentProblem.getVariableSplit()[i + 1]);
        }
        //对基因编码转换成数值--二进制转换成十进制
        BinaryToDecimalX temBD = new BinaryToDecimalX();
        for (int i = 0; i < musicCodeUnit.length; i++) {
            temBD.calculateX(this.musicCodeUnit[i].substring(0, 6), 98f, 35f, 63f);
            TranstoMid mid = new TranstoMid();
            this.musicCodeUnitInteger[i] = mid.trans((int) temBD.getX());
            temBD.calculateX(this.musicCodeUnit[i].substring(6), 7f, 0f, 7f);
            this.musicCodeUnitTime[i] = mid.time((int) temBD.getX());
        }
        this.setComboxvalue();//设置新的编码值
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
//            for (int i = 0; i <problem.getVariableSplit().length - 1; i++) {
//                musicCodeUnit[i] = this.getInd().getGeneCode().substring(problem.getVariableSplit()[i], problem.getVariableSplit()[i + 1]);
//            }
//            //对基因编码转换成数值--二进制转换成十进制
//            BinaryToDecimalX temBD = new BinaryToDecimalX();
//            for (int i = 0; i < musicCodeUnit.length; i++) {
//                temBD.calculateX(this.musicCodeUnit[i].substring(0, 6), 98f, 35f, 63f);
//                TranstoMid mid = new TranstoMid();
//                this.musicCodeUnitInteger[i] = mid.trans((int) temBD.getX());
//                temBD.calculateX(this.musicCodeUnit[i].substring(6), 7f, 0f, 7f);
//                this.musicCodeUnitTime[i] = mid.time((int) temBD.getX());
//            }
            for (int i = 0; i < FactoryProblems.currentProblem.getVariableProperties().length; i++) {
                musicCodeUnitInteger[i] = (int) FactoryProblems.currentProblem.getVariableSplit()[i];
            }

            try {
                Sound kone = new Sound();
                int j;
                for (int i = 0; i < FactoryProblems.currentProblem.getVariableProperties().length; i++) {
                    tem[i][0] = musicCodeUnitInteger[i];
                }
                for (int i = 0; i < FactoryProblems.currentProblem.getVariableProperties().length; i++) {
                    tem[i][2] = musicCodeUnitTime[i];
                }
                for (j = 0; j < tem.length; j++) {
                    tem[j][1] = 100;//音量
                }
                kone.setNoteCode(tem, FactoryProblems.currentProblem.getVariableProperties().length);
                kone.open();
            } catch (Exception e1) {
                System.out.println(e1);
            }
            //   System.out.println(i);
        }
        if (e.getSource() == save) {
//            for (int i = 0; i < problem.getVariableSplit().length - 1; i++) {
//                musicCodeUnit[i] = this.getInd().getGeneCode().substring(problem.getVariableSplit()[i], problem.getVariableSplit()[i + 1]);
//            }
//            //对基因编码转换成数值--二进制转换成十进制
//            BinaryToDecimalX temBD = new BinaryToDecimalX();
//            for (int i = 0; i < musicCodeUnit.length; i++) {
//                temBD.calculateX(this.musicCodeUnit[i].substring(0, 6), 98f, 35f, 63f);
//                TranstoMid mid = new TranstoMid();
//                this.musicCodeUnitInteger[i] = mid.trans((int) temBD.getX());
//                temBD.calculateX(this.musicCodeUnit[i].substring(6), 7f, 0f, 7f);
//                this.musicCodeUnitTime[i] = mid.time((int) temBD.getX());
//            }
            for (int i = 0; i < FactoryProblems.currentProblem.getVariableProperties().length; i++) {
                musicCodeUnitInteger[i] = (int) FactoryProblems.currentProblem.getVariableSplit()[i];
            }
            String s = new String();
            for (int i = 0; i < musicCodeUnit.length; i++) {

                String b, c;
                b = Integer.toString(musicCodeUnitInteger[i]) + ",";
                c = Integer.toString(musicCodeUnitTime[i]) + ",";
                s = s + b + c;
            }
            System.out.println(s);
            try {
                File file = new File("resources/music/RandomMusic.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(s);
                writer.close();
            } catch (Exception e1) {
            }
        }
    }
}
