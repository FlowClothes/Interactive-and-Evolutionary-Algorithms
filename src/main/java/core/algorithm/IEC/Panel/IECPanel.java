package core.algorithm.IEC.Panel;

import core.algorithm.IEC.Render.FitnessAssignMethods.BaseFitnessAssignMethod;
import core.algorithm.IEC.Render.FitnessAssignMethods.FactoryFitnessAssign;
import core.GUI.LabelName;
import core.algorithm.FactoryAlgorithm;
import core.problem.IGA.GraphPanel.FactoryIGAGraph;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import core.tools.DesignPatters.ColleagueButton;
import core.tools.DesignPatters.Mediator;
import core.algorithm.IEC.Panel.IndividualRender.BaseIndividualRender;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Population;
import core.tools.MyDataStructure.LinearList.ListTools;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class IECPanel extends javax.swing.JPanel implements Mediator {
//该类的主要功能是:
//1.提供一个容器,容纳IGA的各种应用,各种应用的主要不同在于个体的表现型不同;
//2.该容器主要包括:1)上面的状态信息；2)每个个体的适应值赋值方法;3)下面的命令钮,包括命令按钮（“开始进化”、“结束退出”、“满意”等）。
    //命令按钮

    // Problem problem;
    private static final int goOnNum = 1, okNum = 2;
    protected ColleagueButton buttonGoOn;//由于不同的进化对象,不同的进化策略,该命令按钮执行的操作也会大有不同,这里使其基类,以便于适应不同情况
    protected ColleagueButton buttonOk;
    protected JPanel commandPane = new JPanel(new GridLayout());
    //进化个体============begin===============
    protected JPanel evoPane;
    //容纳所有进化个体的相关内容
    //进化个体表现型
    public BaseIndividualRender[] onlyIndividualPanel;
    protected JPanel[] IndividualContainerPane;//该Panel用来容纳进化个体表现型、适应值赋值方法和基因型观察下拉列表框
    //进化个体适应值赋值
    private BaseFitnessAssignMethod[] fitnessAssign;
//这个可以确定是一维的适应值
    //进化个体============end==============
    //进化状态
    protected JPanel upStatusPane = new JPanel(new BorderLayout());
    protected JTextArea statusTextfield = new JTextArea();
    private int generationNum = 1, bestNum = 0;
    //其它
    protected JPanel parentPane;

    public IECPanel(JPanel jp) {
        this.parentPane = jp;
    }

    @Override
    public void createColleagues() {
        //geneticAlgorithm = new GeneticAlgorithm();
        //geneticAlgorithm.registerProblems(problem);
        //进化个体============begin===============
        int populationsize=FactoryProblems.currentProblem.getPopulation().getIndividuals().size();
        this.evoPane = new JPanel(new GridLayout(2, populationsize / 2));
        //容纳所有进化个体的相关内容
        //进化个体表现型
        onlyIndividualPanel = new BaseIndividualRender[populationsize];
        IndividualContainerPane = new JPanel[populationsize];//该Panel用来容纳进化个体表现型、适应值赋值方法和基因型观察下拉列表框
        //进化个体适应值赋值
        fitnessAssign = new BaseFitnessAssignMethod[populationsize];
//这个可以确定是一维的适应值
        //进化个体============end==============
        Population population = FactoryProblems.currentProblem.getPopulation();
        population.setIndividuals(population.init(populationsize));
//种群初始化,对OnlyIndividualPanel进行初始化
        FactoryProblems.initIndividualPanel(population,  onlyIndividualPanel);
        //Initiate the components of the pane
        for (int i = 0; i < populationsize; i++) {
            fitnessAssign[i] = new FactoryFitnessAssign().getFitnessAssign(this);
            IndividualContainerPane[i] = new JPanel(new BorderLayout());
            IndividualContainerPane[i].add(onlyIndividualPanel[i], BorderLayout.CENTER);
            JPanel temPanel = new JPanel(new BorderLayout());
            temPanel.add(fitnessAssign[i], BorderLayout.CENTER);
            IndividualContainerPane[i].add(temPanel, BorderLayout.SOUTH);
            this.evoPane.add(IndividualContainerPane[i]);
        }
        buttonGoOn = new ColleagueButton(LabelName.goonEvolvingInIGAPanel, goOnNum);
        buttonOk = new ColleagueButton(LabelName.SatisfactionInIGAPanel, okNum);
        buttonGoOn.setMediator(this);
        buttonOk.setMediator(this);
        this.commandPane.add(buttonGoOn);
        this.commandPane.add(buttonOk);
        this.statusTextfield.setText(LabelName.StatusTextInIGAPanel + generationNum++);
        this.upStatusPane.add(this.statusTextfield, BorderLayout.CENTER);
//        this.upStatusPane.add(new JLabel(new ImageIcon(ControlParameters.resource + "img/" + FaceControlParameters.whichFace + ".gif")), BorderLayout.CENTER);

        //Initiate the Frame
        this.setLayout(new BorderLayout());
        this.add(this.upStatusPane, BorderLayout.NORTH);
        this.add(this.evoPane, BorderLayout.CENTER);
        this.add(this.commandPane, BorderLayout.SOUTH);
        this.validate();
        this.parentPane.repaint();
    }

    @SuppressWarnings("static-access")
    @Override
    public void colleagueInformationHandled(int ColleagueNum) {
        switch (ColleagueNum) {
            case goOnNum://Go On button is clicked
                this.dealEvolving();
                break;
            case okNum: //得到最优个体的编号
            {
                bestNum = 0;
                for (int i = 1; i < FactoryProblems.currentProblem.getPopulation().getIndividuals().size(); i++) {//确定适应值最高的个体编号
                    if (fitnessAssign[bestNum].getFitness() < fitnessAssign[i].getFitness()) {
                        bestNum = i;
                    }
                }
                BaseIndividualRender bestInd = new FactoryIGAGraph().getIndividualPanel(ListTools.getIndividualAt(bestNum,FactoryProblems.currentProblem.getPopulation().getIndividuals()));
                JPanel pane = new JPanel(new BorderLayout());
                pane.add(bestInd, BorderLayout.CENTER);
                //parentPane.add(onlyIndividualPanel[bestNum], BorderLayout.CENTER);//这应该是种群的最优个体的表现型
                Button exitButton = new Button("Exit");
                Button saveResult = new Button("Save Data");
                JPanel buttonPane = new JPanel(new GridLayout(1, 2));
                buttonPane.add(saveResult);
                buttonPane.add(exitButton);
                this.removeAll();
//                parentPane.removeAll();
//                parentPane.add(js, BorderLayout.CENTER);
//                parentPane.add(buttonPane, BorderLayout.SOUTH);
//                parentPane.validate();
                this.add(pane, BorderLayout.CENTER);
                this.add(buttonPane, BorderLayout.SOUTH);
                this.validate();
                saveResult.addActionListener((java.awt.event.ActionEvent evt) -> {
                    //把最优结果记录下来
                });
                exitButton.addActionListener((java.awt.event.ActionEvent evt) -> {
                    parentPane.removeAll();
                    JLabel lb = new JLabel("Thank for your trial");
                    lb.setFont(new Font("", Font.BOLD, 20));
                    parentPane.add(lb, BorderLayout.CENTER);
                    parentPane.validate();
                });
            }

            break;
        }

    }

    private void dealEvolving() {
        Population population = FactoryProblems.currentProblem.getPopulation();
        int i=0;
        for (Object ind:population.getIndividuals()) {//计算适应值,并为个体赋予适应值
            ((Individual)ind).getDecisionVariable().setFitness(new double[]{fitnessAssign[i++].getFitness()});
        }
        FactoryAlgorithm.currentAlgorithm.operates(population);
        FactoryAlgorithm.currentAlgorithm.reserveBestInd(population);
        //重绘个体
        i = 0;
        for (; i < population.getIndividuals().size(); i++) {
            onlyIndividualPanel[i].repaintme(ListTools.getIndividualAt(i, population.getIndividuals()));
            fitnessAssign[i].repaintme();
        }
        this.statusTextfield.setText(LabelName.StatusTextInIGAPanel + generationNum++);
        this.upStatusPane.validate();
        this.validate();
        this.parentPane.repaint();
    }
}
