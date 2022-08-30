package core.problem.IGA.Graph.Compose;

import core.controllers.ControlParameters;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author 鲍慧慧，BAO Huihui;
 * @author 指导教师郝国生  HAO Guo-Sheng
 */
public class Compose extends JFrame implements ActionListener {
    // JFrame jf;

    public static int NOTESIZE = 100;
    public static int i = 1;
    public static int channel = 1;
    public static int track = -2;
    public static int[][] tem = new int[NOTESIZE][32];//最多16个通道
    public static int StaNode[][] = {{48, 50, 52, 53, 55, 57, 59}, {60, 62, 64, 65, 67, 69, 71}, {72, 74, 76, 77, 79, 81, 83}};
    // MenuBar mb;
    JMenuBar mb;
    JMenu m1, m2, m3, m4;
    JMenuItem me1, me2, me3, me4, me5, me6, me7, me8, me9, me10, me11, me12, me13, me14, me15, me16;
    JButton buttonPlay, buttonSave, buttonStart, buttonStop;
    JButton b111, b112, b113, b121, b122, b123, b131, b132, b133, b141, b142, b143, b151, b152, b153, b161, b162, b163, b171, b172, b173;
    JButton b211, b212, b213, b221, b222, b223, b231, b232, b233, b241, b242, b243, b251, b252, b253, b261, b262, b263, b271, b272, b273;
    JButton b311, b312, b313, b321, b322, b323, b331, b332, b333, b341, b342, b343, b351, b352, b353, b361, b362, b363, b371, b372, b373;
    JPanel p1;
    JPanel p2;
    JPanel mainP;
    JButton b;
    static Dialog dlg;
    JButton Sure_btn;
    JLabel L1;
//     MakeMidi midi;

    public Compose() {
        p1 = new JPanel();
        p2 = new JPanel();
        mainP = new JPanel();
        dlg = new Dialog(this, "错误提示");
        L1 = new JLabel("还没选择通道信息");
        Sure_btn = new JButton("确定");
        dlg.setLayout(new FlowLayout());
//    dlg.setSize(100, 100);
        dlg.setBounds(500, 300, 100, 100);
        dlg.add(L1);
        dlg.add(Sure_btn);
        //   p2=new JPanel();
        //1/4拍图片
        ImageIcon ic111 = new ImageIcon(ControlParameters.resource + "img\\music\\7\\1.gif");
        ImageIcon ic112 = new ImageIcon(ControlParameters.resource + "img\\music\\8\\1.gif");
        ImageIcon ic113 = new ImageIcon(ControlParameters.resource + "img\\music\\9\\1.gif");

        ImageIcon ic121 = new ImageIcon(ControlParameters.resource + "img\\music\\7\\2.gif");
        ImageIcon ic122 = new ImageIcon(ControlParameters.resource + "img\\music\\8\\2.gif");
        ImageIcon ic123 = new ImageIcon(ControlParameters.resource + "img\\music\\9\\2.gif");

        ImageIcon ic131 = new ImageIcon(ControlParameters.resource + "img\\music\\7\\3.gif");
        ImageIcon ic132 = new ImageIcon(ControlParameters.resource + "img\\music\\8\\3.gif");
        ImageIcon ic133 = new ImageIcon(ControlParameters.resource + "img\\music\\9\\3.gif");

        ImageIcon ic141 = new ImageIcon(ControlParameters.resource + "img\\music\\7\\4.gif");
        ImageIcon ic142 = new ImageIcon(ControlParameters.resource + "img\\music\\8\\4.gif");
        ImageIcon ic143 = new ImageIcon(ControlParameters.resource + "img\\music\\9\\4.gif");

        ImageIcon ic151 = new ImageIcon(ControlParameters.resource + "img\\music\\7\\5.gif");
        ImageIcon ic152 = new ImageIcon(ControlParameters.resource + "img\\music\\8\\5.gif");
        ImageIcon ic153 = new ImageIcon(ControlParameters.resource + "img\\music\\9\\5.gif");

        ImageIcon ic161 = new ImageIcon(ControlParameters.resource + "img\\music\\7\\6.gif");
        ImageIcon ic162 = new ImageIcon(ControlParameters.resource + "img\\music\\8\\6.gif");
        ImageIcon ic163 = new ImageIcon(ControlParameters.resource + "img\\music\\9\\6.gif");

        ImageIcon ic171 = new ImageIcon(ControlParameters.resource + "img\\music\\7\\7.gif");
        ImageIcon ic172 = new ImageIcon(ControlParameters.resource + "img\\music\\8\\7.gif");
        ImageIcon ic173 = new ImageIcon(ControlParameters.resource + "img\\music\\9\\7.gif");
        //半拍图片
        ImageIcon ic211 = new ImageIcon(ControlParameters.resource + "img\\music\\4\\1_1.gif");
        ImageIcon ic212 = new ImageIcon(ControlParameters.resource + "img\\music\\5\\1_2.gif");
        ImageIcon ic213 = new ImageIcon(ControlParameters.resource + "img\\music\\6\\1_3.gif");

        ImageIcon ic221 = new ImageIcon(ControlParameters.resource + "img\\music\\4\\2_1.gif");
        ImageIcon ic222 = new ImageIcon(ControlParameters.resource + "img\\music\\5\\2_2.gif");
        ImageIcon ic223 = new ImageIcon(ControlParameters.resource + "img\\music\\6\\2_3.gif");

        ImageIcon ic231 = new ImageIcon(ControlParameters.resource + "img\\music\\4\\3_1.gif");
        ImageIcon ic232 = new ImageIcon(ControlParameters.resource + "img\\music\\5\\3_2.gif");
        ImageIcon ic233 = new ImageIcon(ControlParameters.resource + "img\\music\\6\\3_3.gif");

        ImageIcon ic241 = new ImageIcon(ControlParameters.resource + "img\\music\\4\\4_1.gif");
        ImageIcon ic242 = new ImageIcon(ControlParameters.resource + "img\\music\\5\\4_2.gif");
        ImageIcon ic243 = new ImageIcon(ControlParameters.resource + "img\\music\\6\\4_3.gif");

        ImageIcon ic251 = new ImageIcon(ControlParameters.resource + "img\\music\\4\\5_1.gif");
        ImageIcon ic252 = new ImageIcon(ControlParameters.resource + "img\\music\\5\\5_2.gif");
        ImageIcon ic253 = new ImageIcon(ControlParameters.resource + "img\\music\\6\\5_3.gif");

        ImageIcon ic261 = new ImageIcon(ControlParameters.resource + "img\\music\\4\\6_1.gif");
        ImageIcon ic262 = new ImageIcon(ControlParameters.resource + "img\\music\\5\\6_2.gif");
        ImageIcon ic263 = new ImageIcon(ControlParameters.resource + "img\\music\\6\\6_3.gif");

        ImageIcon ic271 = new ImageIcon(ControlParameters.resource + "img\\music\\4\\7_1.gif");
        ImageIcon ic272 = new ImageIcon(ControlParameters.resource + "img\\music\\5\\7_2.gif");
        ImageIcon ic273 = new ImageIcon(ControlParameters.resource + "img\\music\\6\\7_3.gif");

//一拍图片
        ImageIcon ic311 = new ImageIcon(ControlParameters.resource + "img\\music\\1\\1-1.gif");
        ImageIcon ic312 = new ImageIcon(ControlParameters.resource + "img\\music\\2\\1-2.gif");
        ImageIcon ic313 = new ImageIcon(ControlParameters.resource + "img\\music\\3\\1-3.gif");

        ImageIcon ic321 = new ImageIcon(ControlParameters.resource + "img\\music\\1\\2-1.gif");
        ImageIcon ic322 = new ImageIcon(ControlParameters.resource + "img\\music\\2\\2-2.gif");
        ImageIcon ic323 = new ImageIcon(ControlParameters.resource + "img\\music\\3\\2-3.gif");

        ImageIcon ic331 = new ImageIcon(ControlParameters.resource + "img\\music\\1\\3-1.gif");
        ImageIcon ic332 = new ImageIcon(ControlParameters.resource + "img\\music\\2\\3-2.gif");
        ImageIcon ic333 = new ImageIcon(ControlParameters.resource + "img\\music\\3\\3-3.gif");

        ImageIcon ic341 = new ImageIcon(ControlParameters.resource + "img\\music\\1\\4-1.gif");
        ImageIcon ic342 = new ImageIcon(ControlParameters.resource + "img\\music\\2\\4-2.gif");
        ImageIcon ic343 = new ImageIcon(ControlParameters.resource + "img\\music\\3\\4-3.gif");

        ImageIcon ic351 = new ImageIcon(ControlParameters.resource + "img\\music\\1\\5-1.gif");
        ImageIcon ic352 = new ImageIcon(ControlParameters.resource + "img\\music\\2\\5-2.gif");
        ImageIcon ic353 = new ImageIcon(ControlParameters.resource + "img\\music\\3\\5-3.gif");

        ImageIcon ic361 = new ImageIcon(ControlParameters.resource + "img\\music\\1\\6-1.gif");
        ImageIcon ic362 = new ImageIcon(ControlParameters.resource + "img\\music\\2\\6-2.gif");
        ImageIcon ic363 = new ImageIcon(ControlParameters.resource + "img\\music\\3\\6-3.gif");

        ImageIcon ic371 = new ImageIcon(ControlParameters.resource + "img\\music\\1\\7-1.gif");
        ImageIcon ic372 = new ImageIcon(ControlParameters.resource + "img\\music\\2\\7-2.gif");
        ImageIcon ic373 = new ImageIcon(ControlParameters.resource + "img\\music\\3\\7-3.gif");


//1/4拍按钮


        b111 = new JButton(ic111);
        b112 = new JButton(ic112);
        b113 = new JButton(ic113);
        b121 = new JButton(ic121);
        b122 = new JButton(ic122);
        b123 = new JButton(ic123);
        b131 = new JButton(ic131);
        b132 = new JButton(ic132);
        b133 = new JButton(ic133);
        b141 = new JButton(ic141);
        b142 = new JButton(ic142);
        b143 = new JButton(ic143);
        b151 = new JButton(ic151);
        b152 = new JButton(ic152);
        b153 = new JButton(ic153);
        b161 = new JButton(ic161);
        b162 = new JButton(ic162);
        b163 = new JButton(ic163);
        b171 = new JButton(ic171);
        b172 = new JButton(ic172);
        b173 = new JButton(ic173);

        //半拍按钮

        b211 = new JButton(ic211);
        b212 = new JButton(ic212);
        b213 = new JButton(ic213);
        b221 = new JButton(ic221);
        b222 = new JButton(ic222);
        b223 = new JButton(ic223);
        b231 = new JButton(ic231);
        b232 = new JButton(ic232);
        b233 = new JButton(ic233);
        b241 = new JButton(ic241);
        b242 = new JButton(ic242);
        b243 = new JButton(ic243);
        b251 = new JButton(ic251);
        b252 = new JButton(ic252);
        b253 = new JButton(ic253);
        b261 = new JButton(ic261);
        b262 = new JButton(ic262);
        b263 = new JButton(ic263);
        b271 = new JButton(ic271);
        b272 = new JButton(ic272);
        b273 = new JButton(ic273);


//一拍按钮

        b311 = new JButton(ic311);
        b312 = new JButton(ic312);
        b313 = new JButton(ic313);
        b321 = new JButton(ic321);
        b322 = new JButton(ic322);
        b323 = new JButton(ic323);
        b331 = new JButton(ic331);
        b332 = new JButton(ic332);
        b333 = new JButton(ic333);
        b341 = new JButton(ic341);
        b342 = new JButton(ic342);
        b343 = new JButton(ic343);
        b351 = new JButton(ic351);
        b352 = new JButton(ic352);
        b353 = new JButton(ic353);
        b361 = new JButton(ic361);
        b362 = new JButton(ic362);
        b363 = new JButton(ic363);
        b371 = new JButton(ic371);
        b372 = new JButton(ic372);
        b373 = new JButton(ic373);


        this.setLayout(new BorderLayout());
        mb = new JMenuBar();
        this.setBounds(300, 50, 550, 700);
        m1 = new JMenu("通道选择");
        m2 = new JMenu("播放");
        m3 = new JMenu();
        m4 = new JMenu();
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(m4);
        me1 = new JMenuItem("Piano");
        me2 = new JMenuItem("Chromatic Perc");
        me3 = new JMenuItem("Organ");
        me4 = new JMenuItem("Guitar");
        me5 = new JMenuItem("Bass");
        me6 = new JMenuItem("String");
        me7 = new JMenuItem("Ensemble");
        me8 = new JMenuItem("Brass");
        me9 = new JMenuItem("Reed");
        me10 = new JMenuItem("Pipe");
        me11 = new JMenuItem("Synth Lead");
        me12 = new JMenuItem("Synth Pad");
        me13 = new JMenuItem("Synth Effects");
        me14 = new JMenuItem("Ethnic");
        me15 = new JMenuItem("Percussive");
        me16 = new JMenuItem("Sound Effects");
        //  mb1.add(t);
        m1.add(me1);
        m1.add(me2);
        m1.add(me3);
        m1.add(me4);
        m1.add(me5);
        m1.add(me6);
        m1.add(me7);
        m1.add(me8);
        m1.add(me9);
        m1.add(me10);
        m1.add(me11);
        m1.add(me12);
        m1.add(me13);
        m1.add(me14);
        m1.add(me15);
        m1.add(me16);



        buttonPlay = new JButton("Play");
        buttonSave = new JButton("Save");
        buttonStart = new JButton("Start");
        buttonStop = new JButton("Stop");

        p2.setLayout(new GridLayout(9, 8));
        p2.add(new JLabel("1/4拍"));
        p2.add(b111);
        p2.add(b121);
        p2.add(b131);
        p2.add(b141);
        p2.add(b151);
        p2.add(b161);
        p2.add(b171);

        p2.add(new JLabel());
        p2.add(b112);
        p2.add(b122);
        p2.add(b132);
        p2.add(b142);
        p2.add(b152);
        p2.add(b162);
        p2.add(b172);

        p2.add(new JLabel());
        p2.add(b113);
        p2.add(b123);
        p2.add(b133);
        p2.add(b143);
        p2.add(b153);
        p2.add(b163);
        p2.add(b173);

        p2.add(new JLabel("半拍"));

        p2.add(b211);
        p2.add(b221);
        p2.add(b231);
        p2.add(b241);
        p2.add(b251);
        p2.add(b261);
        p2.add(b271);
        p2.add(new JLabel());

        p2.add(b212);
        p2.add(b222);
        p2.add(b232);
        p2.add(b242);
        p2.add(b252);
        p2.add(b262);
        p2.add(b272);
        p2.add(new JLabel());

        p2.add(b213);
        p2.add(b223);
        p2.add(b233);
        p2.add(b243);
        p2.add(b253);
        p2.add(b263);
        p2.add(b273);

        p2.add(new JLabel("一拍"));

        p2.add(b311);
        p2.add(b321);
        p2.add(b331);
        p2.add(b341);
        p2.add(b351);
        p2.add(b361);
        p2.add(b371);
        p2.add(new JLabel());

        p2.add(b312);
        p2.add(b322);
        p2.add(b332);
        p2.add(b342);
        p2.add(b352);
        p2.add(b362);
        p2.add(b372);
        p2.add(new JLabel());

        p2.add(b313);
        p2.add(b323);
        p2.add(b333);
        p2.add(b343);
        p2.add(b353);
        p2.add(b363);
        p2.add(b373);



        this.add("North", p2);
        Container con = getContentPane();

        mainP.setLayout(new FlowLayout());
        con.add(mainP);
        p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 30));
        p1.add(buttonPlay);
        p1.add(buttonSave);
        p1.add(buttonStart);
        p1.add(buttonStop);
        //   p1.add(b);
        //jf.setMenuBar(mb);
        this.setJMenuBar(mb);
        // this.setMenuBar(mb);
        //  this.add(l);
        //  this.add(tx);/////////////
        // this.add(p2);
        this.add("South", p1);
        this.setVisible(true);
        this.validate();

        //1/4拍菜单控件
        b111.addActionListener(this);
        b112.addActionListener(this);
        b113.addActionListener(this);
        b121.addActionListener(this);
        b122.addActionListener(this);
        b123.addActionListener(this);
        b131.addActionListener(this);
        b132.addActionListener(this);
        b133.addActionListener(this);
        b141.addActionListener(this);
        b142.addActionListener(this);
        b143.addActionListener(this);
        b151.addActionListener(this);
        b152.addActionListener(this);
        b153.addActionListener(this);
        b161.addActionListener(this);
        b162.addActionListener(this);
        b163.addActionListener(this);
        b171.addActionListener(this);
        b172.addActionListener(this);
        b173.addActionListener(this);

        //半拍菜单控件
        b211.addActionListener(this);
        b212.addActionListener(this);
        b213.addActionListener(this);
        b221.addActionListener(this);
        b222.addActionListener(this);
        b223.addActionListener(this);
        b231.addActionListener(this);
        b232.addActionListener(this);
        b233.addActionListener(this);
        b241.addActionListener(this);
        b242.addActionListener(this);
        b243.addActionListener(this);
        b251.addActionListener(this);
        b252.addActionListener(this);
        b253.addActionListener(this);
        b261.addActionListener(this);
        b262.addActionListener(this);
        b263.addActionListener(this);
        b271.addActionListener(this);
        b272.addActionListener(this);
        b273.addActionListener(this);
        //一拍菜单控件

        b311.addActionListener(this);
        b312.addActionListener(this);
        b313.addActionListener(this);
        b321.addActionListener(this);
        b322.addActionListener(this);
        b323.addActionListener(this);
        b331.addActionListener(this);
        b332.addActionListener(this);
        b333.addActionListener(this);
        b341.addActionListener(this);
        b342.addActionListener(this);
        b343.addActionListener(this);
        b351.addActionListener(this);
        b352.addActionListener(this);
        b353.addActionListener(this);
        b361.addActionListener(this);
        b362.addActionListener(this);
        b363.addActionListener(this);
        b371.addActionListener(this);
        b372.addActionListener(this);
        b373.addActionListener(this);

        me1.addActionListener(this);
        me2.addActionListener(this);
        me3.addActionListener(this);
        me4.addActionListener(this);
        me5.addActionListener(this);
        me6.addActionListener(this);
        me7.addActionListener(this);
        me8.addActionListener(this);
        me9.addActionListener(this);
        me10.addActionListener(this);
        me11.addActionListener(this);
        me12.addActionListener(this);
        me13.addActionListener(this);
        me14.addActionListener(this);
        me15.addActionListener(this);
        me16.addActionListener(this);

        buttonPlay.addActionListener(this);
        buttonSave.addActionListener(this);
        buttonStart.addActionListener(this);
        Sure_btn.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                this.windowDeactivated(e);
                //System.exit(0);
            }
        });

    }

    public void SetIcon(Icon ic) {
        mainP.add(new JLabel(ic));
        mainP.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //int num[] = new int[100];
        if (e.getSource() == Sure_btn) {
            dlg.dispose();
        }
        if (e.getSource() == me1) {
            i = 1;
            channel = 1;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me2) {
            i = 1;
            channel = 2;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me3) {
            i = 1;
            channel = 3;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me4) {
            i = 1;
            channel = 4;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me5) {
            i = 1;
            channel = 5;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me6) {
            i = 1;
            channel = 6;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me7) {
            i = 1;
            channel = 7;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me8) {
            i = 1;
            channel = 8;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);

        }
        if (e.getSource() == me9) {
            i = 1;
            channel = 9;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me10) {
            i = 1;
            channel = 10;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me11) {
            i = 1;
            channel = 11;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me12) {
            i = 1;
            channel = 12;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me13) {
            i = 1;
            channel = 13;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me14) {
            i = 1;
            channel = 13;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me15) {
            i = 1;
            channel = 15;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == me16) {
            i = 1;
            channel = 16;
            track = track + 2;
            mainP.removeAll();
            mainP.validate();
            mainP.updateUI();
            System.out.println(channel);
            System.out.println(track);
        }
        if (e.getSource() == b111 || e.getSource() == b211 || e.getSource() == b311) {
            // i++;
            Icon ic;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                if (e.getSource() == b111) {
                    ic = b111.getIcon();
                } else if (e.getSource() == b211) {
                    ic = b211.getIcon();
                } else {
                    ic = b311.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[0][0];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b111) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b211) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b311) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b112 || e.getSource() == b212 || e.getSource() == b312) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b112) {
                    ic = b112.getIcon();
                } else if (e.getSource() == b212) {
                    ic = b212.getIcon();
                } else {
                    ic = b312.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[1][0];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b112) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b212) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b312) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b113 || e.getSource() == b213 || e.getSource() == b313) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b113) {
                    ic = b113.getIcon();
                } else if (e.getSource() == b213) {
                    ic = b213.getIcon();
                } else {
                    ic = b313.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[0][3];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b113) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b213) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b313) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }

        if (e.getSource() == b121 || e.getSource() == b221 || e.getSource() == b321) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b121) {
                    ic = b121.getIcon();
                } else if (e.getSource() == b221) {
                    ic = b221.getIcon();
                } else {
                    ic = b321.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[0][1];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b121) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b221) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b321) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b122 || e.getSource() == b222 || e.getSource() == b322) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b122) {
                    ic = b122.getIcon();
                } else if (e.getSource() == b222) {
                    ic = b222.getIcon();
                } else {
                    ic = b322.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[1][1];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b122) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b222) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b322) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b123 || e.getSource() == b223 || e.getSource() == b323) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b123) {
                    ic = b123.getIcon();
                } else if (e.getSource() == b223) {
                    ic = b223.getIcon();
                } else {
                    ic = b323.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[2][1];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b123) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b223) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b323) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b131 || e.getSource() == b231 || e.getSource() == b331) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b131) {
                    ic = b131.getIcon();
                } else if (e.getSource() == b231) {
                    ic = b231.getIcon();
                } else {
                    ic = b331.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[0][2];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b131) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b231) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b331) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b132 || e.getSource() == b232 || e.getSource() == b332) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b132) {
                    ic = b132.getIcon();
                } else if (e.getSource() == b232) {
                    ic = b232.getIcon();
                } else {
                    ic = b332.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[1][2];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b132) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b232) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b332) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b133 || e.getSource() == b233 || e.getSource() == b333) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b133) {
                    ic = b133.getIcon();
                } else if (e.getSource() == b233) {
                    ic = b233.getIcon();
                } else {
                    ic = b333.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[2][2];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b133) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b233) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b333) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }

        if (e.getSource() == b141 || e.getSource() == b241 || e.getSource() == b341) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b141) {
                    ic = b141.getIcon();
                } else if (e.getSource() == b241) {
                    ic = b241.getIcon();
                } else {
                    ic = b341.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[0][3];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b141) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b241) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b341) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b142 || e.getSource() == b242 || e.getSource() == b342) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b142) {
                    ic = b142.getIcon();
                } else if (e.getSource() == b242) {
                    ic = b242.getIcon();
                } else {
                    ic = b342.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[1][3];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b142) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b242) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b342) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b143 || e.getSource() == b243 || e.getSource() == b343) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b143) {
                    ic = b143.getIcon();
                } else if (e.getSource() == b243) {
                    ic = b243.getIcon();
                } else {
                    ic = b343.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[2][3];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b143) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b243) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b343) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }

        if (e.getSource() == b151 || e.getSource() == b251 || e.getSource() == b351) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b151) {
                    ic = b151.getIcon();
                } else if (e.getSource() == b251) {
                    ic = b251.getIcon();
                } else {
                    ic = b351.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[0][4];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b151) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b251) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b351) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b152 || e.getSource() == b252 || e.getSource() == b352) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b152) {
                    ic = b152.getIcon();
                } else if (e.getSource() == b252) {
                    ic = b252.getIcon();
                } else {
                    ic = b352.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[1][4];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b152) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b252) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b352) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b153 || e.getSource() == b253 || e.getSource() == b353) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b153) {
                    ic = b153.getIcon();
                } else if (e.getSource() == b253) {
                    ic = b253.getIcon();
                } else {
                    ic = b353.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[2][4];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b153) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b253) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b353) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }

        if (e.getSource() == b161 || e.getSource() == b261 || e.getSource() == b361) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b161) {
                    ic = b161.getIcon();
                } else if (e.getSource() == b261) {
                    ic = b261.getIcon();
                } else {
                    ic = b361.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[0][5];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b161) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b261) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b361) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b162 || e.getSource() == b262 || e.getSource() == b362) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b162) {
                    ic = b162.getIcon();
                } else if (e.getSource() == b262) {
                    ic = b262.getIcon();
                } else {
                    ic = b362.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[1][5];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b162) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b262) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b362) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b163 || e.getSource() == b263 || e.getSource() == b363) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b163) {
                    ic = b163.getIcon();
                } else if (e.getSource() == b263) {
                    ic = b263.getIcon();
                } else {
                    ic = b363.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[2][5];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b163) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b263) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b363) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }

        if (e.getSource() == b171 || e.getSource() == b271 || e.getSource() == b371) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b171) {
                    ic = b171.getIcon();
                } else if (e.getSource() == b271) {
                    ic = b271.getIcon();
                } else {
                    ic = b371.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[0][6];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b171) {
                    tem[i][2] = 200;
                }
                if (e.getSource() == b271) {
                    tem[i][2] = 400;
                }
                if (e.getSource() == b371) {
                    tem[i][2] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b172 || e.getSource() == b272 || e.getSource() == b372) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b172) {
                    ic = b172.getIcon();
                } else if (e.getSource() == b272) {
                    ic = b272.getIcon();
                } else {
                    ic = b372.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[1][6];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b172) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b272) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b372) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }
        if (e.getSource() == b173 || e.getSource() == b273 || e.getSource() == b373) {
            // i++;
            if (track < 0) {
                dlg.setVisible(true);
            } else {
                Icon ic;
                if (e.getSource() == b173) {
                    ic = b173.getIcon();
                } else if (e.getSource() == b273) {
                    ic = b273.getIcon();
                } else {
                    ic = b373.getIcon();
                }

                SetIcon(ic);
                int t = StaNode[2][6];
                tem[0][track] = channel;
                tem[i][track] = t;
                if (e.getSource() == b173) {
                    tem[i][track + 1] = 200;
                }
                if (e.getSource() == b273) {
                    tem[i][track + 1] = 400;
                }
                if (e.getSource() == b373) {
                    tem[i][track + 1] = 800;
                }
                i++;
            }
        }


        if (e.getSource() == buttonPlay) {
            try {
                Sound kone = new Sound();
                int j;

                kone.setNoteCode(tem, i, track);
                kone.open();
            } catch (Exception e1) {
                System.out.println(e1);
            }
            System.out.println(i);

        }
        if (e.getSource() == buttonStart) {
            // midi.buildTrackAndStart();
            System.out.print(track);
            MakeMidi midi = new MakeMidi();
            midi.setnoteCode(tem, track);
            midi.buildTrackAndStart();
            String s = new String();

            for (int j = 0; j <= track; j = j + 2) {
                for (int i1 = 0; i < NOTESIZE; i++) {
                    if (tem[i1][j] != 0) {
                        String b2;
                        b2 = Integer.toString(tem[i][j]) + ",";
                        // c=Integer.toString(tem[i][track+1])+",";
                        s = s + b2;
                    } else {
                        break;
                    }
                }
            }
            System.out.println(s);
            try {
                File file = new File("resources/music/2.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(s);
                writer.close();

            } catch (Exception e1) {
            }
        }
        if (e.getSource() == buttonSave) {
            String s = new String();

            for (int j = 0; j < i; j++) {
                String b1, c;
                b1 = Integer.toString(tem[j][track]) + ",";
                c = Integer.toString(tem[j][track + 1]) + ",";
                s = s + b1 + c;
            }
            System.out.println(s);
            try {
                File file = new File("resources/music/1.txt");
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(s);
                writer.close();
            } catch (Exception e1) {
            }

        }
    }
}
