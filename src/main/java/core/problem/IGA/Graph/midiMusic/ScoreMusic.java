package core.problem.IGA.Graph.midiMusic;

import core.problem.IGA.GraphPanel.BaseIGAGraphIndividual;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import core.problem.FactoryProblems;
import core.problem.IGA.Graph.midiMusic.problem.ScoreMusicProblem;
import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ScoreMusic extends BaseIGAGraphIndividual implements ActionListener {

    String[] musicCodeUnit;
    int[] musicCodeUnitInteger;
    String str[];
    String s[];
    public static int NOTESIZE = 100;
    public static int[][] tem = new int[NOTESIZE][2];//tem[][0]音调tem[][2]节拍
    JButton play, save;
    JPanel p;
    String b;

    public ScoreMusic() {
        play = new JButton("Play");
        save = new JButton("save");
        p = new JPanel();
        musicCodeUnit = new String[FactoryProblems.currentProblem.getVariableProperties().length];
        musicCodeUnitInteger = new int[FactoryProblems.currentProblem.getVariableProperties().length];//存储音符编码
        str = new String[FactoryProblems.currentProblem.getVariableProperties().length];
        s = new String[FactoryProblems.currentProblem.getVariableProperties().length];
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
        //
        this.add(box, BorderLayout.SOUTH);
        this.setComboxvalue();
        play.addActionListener(this);
        save.addActionListener(this);
        this.setBorder(BorderFactory.createLineBorder(Color.blue));
        this.validate();
    }

    @Override
    public void repaintme(Individual ind) {
        this.setInd(ind);
        this.commonPaint(1);//重新显示基因编码值
        this.validate();
    }

    private void commonPaint(int rep) {

        this.setComboxvalue();//设置新的编码值
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == play) {
            //对基因编码分解
//            for (int i = 0; i <problem.getVariableProperties().length; i++) {
//                musicCodeUnit[i] = this.getInd().getGeneCode().substring(problem.getVariableSplit()[i],problem.getVariableSplit()[i + 1]);
//            }
//            //对基因编码转换成数值--二进制转换成十进制
//            BinaryToDecimalX temBD = new BinaryToDecimalX();
//            for (int i = 0; i < musicCodeUnitInteger.length; i++) {
//                temBD.calculateX(this.musicCodeUnit[i].substring(0, 7), 127f, 0f, 127f);
//                musicCodeUnitInteger[i] = (int) temBD.getX();
//
//            }
            for (int i = 0; i < FactoryProblems.currentProblem.getVariableProperties().length; i++) {
                musicCodeUnitInteger[i] = (int) FactoryProblems.currentProblem.getVariableSplit()[i];
            }

            //     try {
            SelectSound kone = new SelectSound();
            try {
                File file = new File("resources/music/1.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                b = reader.readLine();
                s = b.split(",");
                reader.close();
            } catch (Exception e1) {
                System.out.println(e1);
            }
            int j;
            j = 2;
            for (int i = 1; i < s.length / 2; i++) {

                TranstoMid mid = new TranstoMid();
                tem[i][0] = mid.trans((Integer.parseInt(s[j]) + this.musicCodeUnitInteger[i]) % 127);

                tem[i][1] = Integer.parseInt(s[j + 1]);
                j = j + 2;
            }
            tem[0][0] = Integer.parseInt(s[0]);
            // tem[0][0]=Integer.parseInt(s[0]);
            kone.setNoteCode(tem, s.length / 2, 0);
            kone.open();
            //    } catch (Exception e1) {
            //     System.out.println(e1);
            //   }
        }
        if (e.getSource() == save) {
            String var = new String();
//            for (int i = 0; i < problem.getVariableProperties().length; i++) {
//                musicCodeUnit[i] = this.getInd().getGeneCode().substring(problem.getVariableSplit()[i], problem.getVariableSplit()[i + 1]);
//            }
//            //对基因编码转换成数值--二进制转换成十进制
//            BinaryToDecimalX temBD = new BinaryToDecimalX();
//            for (int i = 2; i < musicCodeUnitInteger.length; i++) {
//                temBD.calculateX(this.musicCodeUnit[i].substring(0, 7), 127f, 0f, 127f);
//                //
//                // TranstoMid mid = new TranstoMid();
//                // this.musicCodeUnitInteger[i] = mid.trans((int) temBD.getX());
//                musicCodeUnitInteger[i] = (int) temBD.getX();
//            }
            for (int i = 0; i < FactoryProblems.currentProblem.getVariableProperties().length; i++) {
                musicCodeUnitInteger[i] = (int) FactoryProblems.currentProblem.getVariableSplit()[i];
            }
            try {

                File file = new File("resources/music/1.txt");
                BufferedReader reader = new BufferedReader(new FileReader(file));
                b = reader.readLine();
                s = b.split(",");
                reader.close();
            } catch (Exception e1) {
            }
            int j;
            j = 2;
            String b, c;
            for (int i = 0; i < s.length / 2; i++) {
                if (i == 0) {
                    tem[i][0] = Integer.parseInt(s[0]);
                    tem[i][1] = Integer.parseInt(s[1]);
                    b = Integer.toString(tem[i][0]) + ",";
                    c = Integer.toString(tem[i][1]) + ",";
                    var = var + b + c;
                } else {
                    TranstoMid mid = new TranstoMid();
                    tem[i][0] = mid.trans((Integer.parseInt(s[j]) + this.musicCodeUnitInteger[i]) % 127);
                    tem[i][1] = Integer.parseInt(s[j + 1]);
                    b = Integer.toString(tem[i][0]) + ",";
                    c = Integer.toString(tem[i][1]) + ",";
                    var = var + b + c;
                    j = j + 2;
                }
            }
            System.out.println(var);
            try {
                File file = new File("resources/music/1.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(var);
                writer.close();
            } catch (Exception e1) {
            }
        }
    }

}
