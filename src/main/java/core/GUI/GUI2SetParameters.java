package core.GUI;

import core.algorithm.AbstractAlgorithm;
import core.algorithm.Algorithm;
import core.algorithm.FactoryAlgorithm;
import core.algorithm.GeneticAlgorithm;
import core.algorithm.operator.Crossover.FactoryCrossover;
import core.algorithm.operator.Mutation.FactoryMutation;
import core.algorithm.operator.Selection.FactorySelection;
import core.controllers.Connection4SetAndRunning;
import core.controllers.ControlParameters;
import core.problem.FactoryProblems;
import core.tools.DesignPatters.ColleagueButton;
import core.tools.DesignPatters.ColleagueJComboBox;
import core.tools.DesignPatters.Mediator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class GUI2SetParameters extends JPanel implements Mediator {

    Connection4SetAndRunning connection4SetAndRunning;
    private static final int colleagueSelection = 0, colleagueCrossovertype = 1, colleagueMutationtype = 2, Okbutton = 12;//, defaultParameters = 11
    //private int crossoverPointnum;
    private final JPanel parentPane;
    private JComboBox fitnessAssignType;//适应值赋值方法
    //private JComboBox codeType;//个体编码方法，主要有二进制编码，实数编码，字符串编码，有的问题只能用一种编码方法，如服装设计，只能用字符串编码；而人脸优化只能用实数编码；但对于常规的函数优化，则可以用实数和二进制编码
    private ColleagueJComboBox selectionType, crossoverType, mutationType;//, mutationType
    private JTextField
            crossoverPointnumValue,
    //crossoverProbability,
    mutationProbability,//变异概率的文本框
            mutationPointNumValue,//变异点数量的文本框
            stopEvaluationValue,
            populationsizeValue,
            runningTime;

    private ColleagueButton okButton;//setDefault,
    protected final JTabbedPane tabpaneParameters = new JTabbedPane();

    public GUI2SetParameters(JPanel parentpane, Connection4SetAndRunning connection4SetAndRunning) {
        parentPane = parentpane;
        this.connection4SetAndRunning = connection4SetAndRunning;
    }

    @Override
    public void createColleagues() {
        setLayout(new BorderLayout());
        createOperatorsColleague();
        createRunningColleague();
        createExperimentColleague();
        addInputVerifiers();
        createOtherColleague();
    }

    @Override
    public void colleagueInformationHandled(int ColleaguNumber) {
        switch (ColleaguNumber) {
            case colleagueCrossovertype: {
                switch (crossoverType.getSelectedIndex()) {
                    case 0://单点交叉
                        crossoverPointnumValue.setText("1");
                        crossoverPointnumValue.setEnabled(false);
                        break;
                    case 1://多点交叉
                        crossoverPointnumValue.setText("2");
                        crossoverPointnumValue.setEnabled(true);
                        break;
                    default:
                        throw new UnsupportedOperationException("Not supported yet.");
                }
            }
            break;
            case colleagueMutationtype:
                switch (this.mutationType.getSelectedIndex()) {
                    case 0://单点变异
                        mutationPointNumValue.setText("1");
                        mutationPointNumValue.setEnabled(false);
                        break;
                    case 1://混沌变异
                        mutationPointNumValue.setText("2");
                        mutationPointNumValue.setEnabled(true);
                        break;
                    default:
                        throw new UnsupportedOperationException("Not supported yet.");
                }
                break;
            case Okbutton:
                this.okButtonActionPerformed();
                break;
            default:
                throw new UnsupportedOperationException("Not supported yet");
        }
    }

    protected void okButtonActionPerformed() {
        if (null == FactoryAlgorithm.currentAlgorithm) {
            FactoryAlgorithm.currentAlgorithm = new GeneticAlgorithm();
        }
        Algorithm algorithm = FactoryAlgorithm.currentAlgorithm;
        FactorySelection.selectionType = this.selectionType.getSelectedIndex();
        FactoryCrossover.crossoverType = this.crossoverType.getSelectedIndex();
//        FactoryCrossover.crossProbability = Float.parseFloat(this.crossoverProbability.getText());
        FactoryCrossover.crossoverPointNum = Integer.parseInt(this.crossoverPointnumValue.getText());

        FactoryMutation.mutationType = this.mutationType.getSelectedIndex();
        FactoryMutation.mutationPointNum = Integer.parseInt(this.mutationPointNumValue.getText());
        FactoryMutation.mutationProbability = Float.parseFloat(this.mutationProbability.getText());

        ControlParameters.stopEvaluationNumber = Integer.parseInt(this.stopEvaluationValue.getText());
        AbstractAlgorithm.populationSize = Integer.parseInt(this.populationsizeValue.getText());
        //algorithm.setPopulationSize(Integer.parseInt(this.populationsizeValue.getText()));
        ControlParameters.experimentRunningTime = Integer.parseInt(runningTime.getText());
        if (!FactoryProblems.currentProblem.isIECProblem()) {
            //Traditonal GA
            this.removeAll();
            this.parentPane.removeAll();
            JTextArea jta = new JTextArea();
            jta.setEditable(false);
            jta.setText("");
            JScrollPane main = new JScrollPane(jta);
            main.getViewport().setView(jta);
            this.parentPane.add(main, BorderLayout.CENTER);
            connection4SetAndRunning.setTextArea(jta);//指定运行内容要显示指定的pane上
            connection4SetAndRunning.running(
                    parentPane,
                    FactorySelection.selectionType,
                    FactoryCrossover.crossoverType,
                    FactoryCrossover.crossoverPointNum,
                    FactoryMutation.mutationType,
                    FactoryMutation.mutationPointNum,
                    FactoryMutation.mutationProbability);
            //parentPane.validate();
        } else {
            //以下为IEC
            FactoryProblems.dealIEC(parentPane, algorithm);
        }
    }

    private void createOperatorsColleague() {//进化计算操作算子
        JPanel operatorsPane = new JPanel(new BorderLayout()),
                selectionPane = new JPanel(new GridLayout(1, 2)),
                crossoverPane = new JPanel(new GridLayout(2, 2)),
                mutationPane = new JPanel(new GridLayout(3, 2));

        JPanel tempanel1 = new JPanel(new BorderLayout()),
                tempanel2 = new JPanel(new BorderLayout());


        {//选择算子的参数设置
            selectionPane.setBorder(new TitledBorder(LabelName.SetParametersGUI_BorderSelection));
            selectionPane.add(new JLabel(LabelName.SetParametersGUI_SelectionType));
            selectionType = new ColleagueJComboBox(LabelName.SetParametesGUI_SelectionTypeValue, colleagueSelection);
            selectionType.setMediator(this);
            selectionPane.add(selectionType);
            operatorsPane.add(selectionPane, BorderLayout.NORTH);
        }
        {//交叉算子的参数设置
            crossoverPane.setBorder(new TitledBorder(LabelName.SetParametersGUI_BorderCrossover));
            crossoverPane.add(new JLabel(LabelName.SetParametersGUI_CrossoverTypeLabel));
            crossoverType = new ColleagueJComboBox(LabelName.SetParametersGUI_CrossoverTypeValue, colleagueCrossovertype);
            crossoverType.setMediator(this);
            crossoverPane.add(crossoverType);
            crossoverPane.add(new JLabel(LabelName.SetParametersGUI_CrossoverPointnumLabel));
            crossoverPointnumValue = new JTextField("1");
            crossoverPane.add(crossoverPointnumValue);
//            crossoverPane.add(new JLabel(LabelName.SetParametersGUI_CrossoverProbabilityLabel));
//            crossoverProbability = new JTextField("0.8");
//            crossoverPane.add(crossoverProbability);
            tempanel1.add(crossoverPane, BorderLayout.NORTH);
            operatorsPane.add(tempanel1, BorderLayout.CENTER);
        }
        {//变异算子的参数设置
            mutationPane.setBorder(new TitledBorder(LabelName.SetParametersGUI_BorderMutation));
            mutationPane.add(new JLabel(LabelName.SetParametersGUI_MutationTypeLabel));
            mutationType = new ColleagueJComboBox(LabelName.SetParametersGUI_MutationTypeValue, colleagueMutationtype);
            mutationType.setMediator(this);
            mutationPane.add(mutationType);
            mutationPane.add(new JLabel(LabelName.SetParametersGUI_MutationPointNumLabel));
            mutationPointNumValue = new JTextField("1");
            mutationPane.add(mutationPointNumValue);
            mutationPane.add(new JLabel(LabelName.SetParametersGUI_MutationProbabilityLabel));
            mutationProbability = new JTextField("0.1");
            mutationPane.add(mutationProbability);
            tempanel2.add(mutationPane, BorderLayout.NORTH);
            tempanel1.add(tempanel2, BorderLayout.CENTER);
        }
        {//其它
            JPanel OtherPane = new JPanel(new GridLayout(1, 2));
            OtherPane.setBorder(new TitledBorder(LabelName.SetParametersGUI_BorderOthers));
//下面注释掉的是“是否允许种群内出现重复个体”的选项，去掉它的原因是“不允许重复出现”效率肯定比“允许重复出现”效率高，因此，没必要诱导用户选择一个效率低的方法
//            OtherPane.add(new JLabel(LabelName.SetParametersGUI_ReappearingLabel));
//            JRadioButton[] yesORno = {new JRadioButton(LabelName.Yes), new JRadioButton(LabelName.Not)};
//            yesORno[1].setSelected(true);
//            ButtonGroup bg = new ButtonGroup();
//            bg.add(yesORno[0]);
//            bg.add(yesORno[1]);
//            JPanel temP = new JPanel(new GridLayout(1, 2));
//            temP.add(yesORno[0]);
//            temP.add(yesORno[1]);
//            OtherPane.add(temP);
            operatorsPane.add(OtherPane, BorderLayout.SOUTH);
        }

        tabpaneParameters.add(LabelName.SetParametersGUI_Tabbed_Operaters, operatorsPane);
    }

    private void createRunningColleague() {//该参数表明，是与problem相关的，当然该参数也可以从成员变量中获取
        JPanel RuningPane = new JPanel(new GridLayout(3, 2));
        RuningPane.add(new JLabel(LabelName.SetParametersGUI_PopulationSize));
        populationsizeValue = new JTextField("50");
        RuningPane.add(populationsizeValue);
        RuningPane.add(new JLabel(LabelName.SetParametersGUI_StopEvaluation));
        stopEvaluationValue = new JTextField();
        RuningPane.add(stopEvaluationValue);
        if (FactoryProblems.currentProblem.isIECProblem()) {
            RuningPane.add(new JLabel(LabelName.SetParametersGUI_FitnessAssign));
            fitnessAssignType = new JComboBox(LabelName.SetParametersGUI_fitnessAssingMethods);
            fitnessAssignType.addActionListener((ActionEvent e) -> {
                ControlParameters.fitnessAssign = fitnessAssignType.getSelectedIndex();
            });
            RuningPane.add(fitnessAssignType);
        }
        tabpaneParameters.add(LabelName.SetParametersGUI_Tabbed_Running, RuningPane);
        //下面设置种群规模和进化评价个数
        String[] popSizeAndStopEvaluation = FactoryProblems.setPopulationSize();
        populationsizeValue.setText(popSizeAndStopEvaluation[0]);
        stopEvaluationValue.setText(popSizeAndStopEvaluation[1]);
    }

    private void createExperimentColleague() {
        JPanel experimentPanel = new JPanel(new GridLayout(2, 1));
        runningTime = new JTextField("200");
        JPanel temP = new JPanel(new GridLayout(1, 2));
        temP.add(new JLabel(LabelName.SetParametersGUI_ExperimentRunningTimes));
        temP.add(runningTime);
        if (!FactoryProblems.currentProblem.isIECProblem()) {//非交互式的进化计算可以使用该选项
            temP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            experimentPanel.add(temP);
        }
        tabpaneParameters.add(LabelName.SetParametersGUI_Tabbed_ExperimentBased, experimentPanel);
    }

    protected void addInputVerifiers() {
        crossoverPointnumValue.setInputVerifier(new InputVerifier() {

            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                int tem = Integer.parseInt(tf.getText());
                if (tem < 1) {//|| ControlParameters.gsuCount <= 2) {
                    tf.setText("1");
                }
//                else {
//                    if (tem > ControlParameters.gsuCount - 1) {
//                        tf.setText(Integer.toString(ControlParameters.gsuCount - 1));
//                    }
//                }
                return true;
            }
        });
        runningTime.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField rt = (JTextField) input;
                int tem = Integer.parseInt(rt.getText());
                if (tem < 0 || tem > 2000) {
                    rt.setText("200");
                }
                return true;
            }
        });

//        crossoverProbability.setInputVerifier(new InputVerifier() {
//
//            @Override
//            public boolean verify(JComponent input) {
//                JTextField tf = (JTextField) input;
//                float tem = Float.parseFloat(tf.getText());
//                if (tem < 0) {
//                    tf.setText("0");
//                } else {
//                    if (tem > 0.8) {
//                        tf.setText("0.8");
//                    }
//                }
//                return true;
//            }
//        });
//        mutationPointNumValue.setInputVerifier(new InputVerifier() {
//
//            @Override
//            public boolean verify(JComponent input) {
//                JTextField tf = (JTextField) input;
//                int tem = Integer.parseInt(tf.getText());
//                if (tem < 0) {
//                    tf.setText("1");
//                }
//                return true;
//            }
//        });
//        mutationProbability.setInputVerifier(new InputVerifier() {
//
//            @Override
//            public boolean verify(JComponent input) {
//                JTextField tf = (JTextField) input;
//                float tem = Float.parseFloat(tf.getText());
//                if (tem < 0) {
//                    tf.setText("0");
//                } else {
//                    if (tem > 0.1) {
//                        tf.setText("0.1");
//                    }
//                }
//                return true;
//            }
//        });
        stopEvaluationValue.setInputVerifier(new InputVerifier() {

            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                int tem = 0;
                if (Double.parseDouble(tf.getText()) > 655350) {
                    tem = 655350;//Excel 97能保存的数据的最大行数为65,536，这里用655350对应于种群规模是10，且空出10个用于记录其他
                    tf.setText(String.valueOf(tem));
                } else {
                    tem = Integer.parseInt(tf.getText());
                    if (tem < 0) {
                        tem = 655350;
                        tf.setText(String.valueOf(tem));
                    }
                }
                tf.setText(FactoryProblems.verifyStopEvaluation(Integer.parseInt(tf.getText())));
                return true;
                //tem % Integer.parseInt(populationsizeValue.getText().split(",")[0]) != 0;//保证评价次数除以种群规模是个整数
            }
        });

        populationsizeValue.setInputVerifier(new InputVerifier() {

            @Override
            public boolean verify(JComponent input) {
                JTextField tf = (JTextField) input;
                try {
                    tf.setText(FactoryProblems.setPopulationSize(Integer.valueOf(tf.getText())));
                    return true;
                } catch (Exception e) {
                    tf.setText("必须是一个整数！");
                    tf.repaint();
                    return false;
                }
            }
        });

    }

    private void createOtherColleague() {
        add(tabpaneParameters, BorderLayout.CENTER);
        JTextArea jtf = new JTextArea();
        jtf.append(FactoryProblems.currentProblem.getName() + "\n");
        jtf.append(LabelName.SetParametersGUI_Instruction);
        add(jtf, BorderLayout.NORTH);
        //setDefault = new ColleagueButton(LabelName.SetParametersGUI_LoadDefault, defaultParameters);
        //.setMediator(this);
        okButton = new ColleagueButton(LabelName.MyOK, Okbutton);
        okButton.setMediator(this);
        JPanel southPane = new JPanel(new GridLayout(1, 2));
        // southPane.add(setDefault);
        southPane.add(okButton);
        add(southPane, BorderLayout.SOUTH);
        validate();
    }

}
