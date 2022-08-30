package core.problem.IGA.Graph.midiMusic;

import core.problem.IGA.GraphPanel.BaseIGAGraphIndividual;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import core.problem.FactoryProblems;
import core.problem.IGA.Graph.midiMusic.problem.ChordMusicProblem;
import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ChordMusic extends BaseIGAGraphIndividual implements ActionListener {

    public static int NOTESIZE = 300;
    String[] musicCodeUnit;
    int[] musicCodeUnitInteger;//存储音符编码
    public int[][] tem = new int[NOTESIZE][32];//tem[][0]音调,

    JButton play, save;
    JPanel p;
    String str[];
    String s[];
    String b;
    int k = 0;

    public ChordMusic() {
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

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play) {
            //对基因编码分解
            int track = 0;
//            for (int i = 0; i < problem.getVariableProperties().length; i++) {
//                musicCodeUnit[i] = this.getInd().getGeneCode().substring(problem.getVariableSplit()[i], problem.getVariableSplit()[i + 1]);
//            }
//            //对基因编码转换成数值--二进制转换成十进制
//            BinaryToDecimalX temBD = new BinaryToDecimalX();
//            for (int i = 0; i < musicCodeUnitInteger.length; i++) {
////
//                temBD.calculateX(this.musicCodeUnit[i].substring(0, 7), 127f, 0f, 127f);
//                //
//
//                musicCodeUnitInteger[i] = (int) temBD.getX();
//
//            }
            for (int i = 0; i <FactoryProblems.currentProblem.getVariableProperties().length; i++) {
                musicCodeUnitInteger[i] = (int) FactoryProblems.currentProblem.getVariableSplit()[i];
            }
            try {
                File file = new File("resources/music/2.txt");
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    b = reader.readLine();
                    s = b.split(",");
                }
            } catch (IOException e1) {
                //  System.out.println(e1);
            }

            int j = 0;
            for (int i = 0; i < s.length; i++) {
                if (Integer.parseInt(s[i]) < 17) {
                //这儿用17的原因是：
                    j = 0;
                    int t = Integer.parseInt(s[i]);
                    tem[j][track] = t;
                    k = track;
                    track = track + 2;//
                    j++;
                } else {
                    TranstoMid mid = new TranstoMid();
                    tem[j][k] = mid.trans((Integer.parseInt(s[i]) + this.musicCodeUnitInteger[i]) % 127);
                    //这里用127的原因是
                    j++;

                }

            }

            MakeMidi midi = new MakeMidi();
            midi.setnoteCode(tem, k);
            midi.buildTrackAndStart();

        }

        if (e.getSource() == save) {
            String s = new String();

            for (int m = 0; m <= k; m = m + 2) {
                for (int i = 0; i < NOTESIZE; i++) {
                    if (tem[i][m] != 0) {
                        String b;
                        b = Integer.toString(tem[i][m]) + ",";
                        // c=Integer.toString(tem[i][track+1])+",";
                        s = s + b;
                    } else {
                        break;
                    }
                }
            }
            // System.out.println(s);
            try {
                File file = new File("resources/music/2.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(s);
                writer.close();

            } catch (Exception e1) {
            }

        }
    }

}
