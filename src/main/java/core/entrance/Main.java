package core.entrance;

import core.GUI.GUI2SetParameters;
import core.GUI.LabelName;
import core.GUI.MyMenuBar;
import core.algorithm.AbstractAlgorithm;
import core.algorithm.GeneticAlgorithm;
import core.controllers.Connection4SetAndRunning;
import core.controllers.ControlParameters;
import core.problem.FactoryProblems;
import core.problem.Problem;
import core.tools.DesignPatters.Mediator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author 郝国生 HAO Guo-Sheng 系统入口
 * 运行本系统的人脸优化部分，需要安装Java3d:https://www.oracle.com/technetwork/java/javase/tech/index-jsp-138252.html
 * 项目源代码位于https://gitee.com/guoshenghao/Interactive-and-Evolutionary-Algorithms/tree/master
 */
public class Main extends javax.swing.JFrame implements Mediator {

    private final JPanel mainPane = new JPanel(new BorderLayout());
    Container cp;
    GUI2SetParameters scp;
    Problem problem;
    GeneticAlgorithm geneticAlgorithm;

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JPopupMenu.setDefaultLightWeightPopupEnabled(false);
                new LabelName().init();  //设置菜单语言选项，及各种label的名称            
                Main main = new Main();
                main.createColleagues();
                Toolkit kit = Toolkit.getDefaultToolkit();
                main.setIconImage(kit.getImage(ControlParameters.resource + "/img/systemIco.GIF"));
                main.setTitle(LabelName.title);
                main.setVisible(true);
                main.setExtendedState(main.MAXIMIZED_BOTH);
            }
        });
    }

    @Override
    public void createColleagues() {
        cp = getContentPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(cp);
        cp.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
        cp.setLayout(new BorderLayout());
        mainPane.add(new JLabel("开源 Open Source:https://gitee.com/guoshenghao/Interactive-and-Evolutionary-Algorithms/tree/master"), BorderLayout.NORTH);
        mainPane.add(new JLabel(new ImageIcon(ControlParameters.resource + "/img/backgroud/mhgk.jpg")), BorderLayout.CENTER);
        mainPane.setBackground(Color.white);
        cp.add(this.mainPane, BorderLayout.CENTER);
        this.setJMenuBar(new MyMenuBar(this));
    }

    @Override
    public void colleagueInformationHandled(int colleagueNum) throws IOException {//如果要调用遗传算法的程序，最重要的设置ControlParameteres的参数，然后供甚直接调用；

        if (colleagueNum < LabelName.EnglishIndex) {//1000以内是函数
            FactoryProblems.problemNum = colleagueNum;
            FactoryProblems.initProblem(colleagueNum,AbstractAlgorithm.genecodeType);
            this.geneticAlgorithm = new GeneticAlgorithm();
            this.geneticAlgorithm.init();
            Connection4SetAndRunning connection4SetAndRunning = new Connection4SetAndRunning();
            mainPane.removeAll();
            scp = new GUI2SetParameters(mainPane, connection4SetAndRunning);
            scp.createColleagues();
            mainPane.add(scp);
            mainPane.validate();
        } else if (colleagueNum == LabelName.EnglishIndex) {//以上为问题设置,以下为语言设置，详见MyMenuBar类
            LabelName.fp.setValue("language", "0");
            setLanguage();
        } else if (colleagueNum == LabelName.ChineseIndex) {
            LabelName.fp.setValue("language", "1");
            setLanguage();
        } else if (colleagueNum == LabelName.exitIndex) {//点击了exitmenu
            System.exit(0);
        }
    }

    private void setLanguage() throws IOException {
        try {
            LabelName.fp.writeToFile(LabelName.resourcePath + "setup.conf");
        } catch (IOException ex1) {
            Logger.getLogger(LabelName.class.getName()).log(Level.SEVERE, null, ex1);
        }
        new LabelName().init();//因为更改了语言，所以需要重新生成一下
        this.setJMenuBar(null);
        this.setJMenuBar(new MyMenuBar(this));
        this.validate();
    }

}
