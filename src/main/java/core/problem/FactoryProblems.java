/*
 * 该类的主要功能是：
 * 1.根据不同的问题，进行相应的处理
 * 2.
 */
package core.problem;

import core.algorithm.Algorithm;
import core.algorithm.IEC.Panel.IECPanel;
import core.algorithm.IEC.Panel.IndividualRender.BaseIndividualRender;
import core.problem.DecisionVariables.GenecodeType;
import core.problem.IGA.Graph.Face.Face;
import core.problem.IGA.Graph.Face.PreFaceOptimization;
import core.problem.IGA.Graph.OneMaxColor.HSVOneMax;
import core.problem.IGA.Graph.OneMaxColor.OneMaxColor;
import core.problem.IGA.Graph.Validater.FractualValidater;
import core.problem.IGA.Graph.midiMusic.ChordMusic;
import core.problem.IGA.Graph.midiMusic.RandomMidiMusic;
import core.problem.IGA.Graph.midiMusic.ScoreMusic;
import core.problem.IGA.GraphPanel.BaseFractual;
import core.problem.IGA.GraphPanel.BaseIGAGraphIndividual;
import core.problem.IGA.GraphPanel.FactoryIGAGraph;
import core.problem.IGA.GraphPanel.FashionDesign;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class FactoryProblems {

    public static int currentProblemNum;
    public static Problem currentProblem;
    public static int problemNum;//问题编号
    public static int dimensionNumber = 0;
    public BaseIndividualRender[] onlyIndividualPanel;

    /**
     * 保存问题编号与名称
     */
    public static final HashMap<Integer, String> problemsIndex = new HashMap<>();

    public static void registerProblems() {
        problemsIndex.put(0, "max_xsin10PIx_2.0");
        problemsIndex.put(1, "De Jongs");
        problemsIndex.put(2, "De Jongs");
        problemsIndex.put(3, "De Jongs");
        problemsIndex.put(4, "Kowalik");
        problemsIndex.put(5, "Shekel’s Foxholes");
        problemsIndex.put(6, "Schaffers 1st Function");
        problemsIndex.put(7, "Schaffers 2nd Fuction");
        problemsIndex.put(8, "Goldstein-Prices Function");
        problemsIndex.put(9, "Shuberts Function");
        problemsIndex.put(10, "Six-hump Camel Back Function");
        problemsIndex.put(11, "Sphere");
        problemsIndex.put(12, "SumSquares");
        problemsIndex.put(13, "Schwefel 2.22");
        problemsIndex.put(14, "Schwefel 1.2");
        problemsIndex.put(15, "Schwefel 2.21");
        problemsIndex.put(16, "Exponential");
        problemsIndex.put(17, "Tablet");
        problemsIndex.put(18, "Zakharov");
        problemsIndex.put(19, "Step");
        problemsIndex.put(20, "Rosenbrock");
        problemsIndex.put(21, "Griewank");
        problemsIndex.put(22, "Schaffer 2");
        problemsIndex.put(23, "Schwefel 2.26");
        problemsIndex.put(24, "Himmelblau");
        problemsIndex.put(25, "Levy and Montalvo 1");
        problemsIndex.put(26, "Levy and Montalvo 2");
        problemsIndex.put(27, "Ackley");
        problemsIndex.put(28, "Rastrigin");
        problemsIndex.put(29, "Penalized 1");
        problemsIndex.put(30, "Penalized 2");
        problemsIndex.put(31, "Neumarier 3");
        problemsIndex.put(32, "Salomom");
        problemsIndex.put(33, "Alpine");
        problemsIndex.put(34, "Noisy Quaric");
        problemsIndex.put(35, "Branin");
        problemsIndex.put(36, "Hartman's family 3");
        problemsIndex.put(37, "Hartman's family 6");
        problemsIndex.put(38, "Shelel's family 5");
        problemsIndex.put(39, "Shelel's family 7");
        problemsIndex.put(40, "Shelel's family 10");
        problemsIndex.put(41, "Storn’s Chebyshev Polynomial ");
        problemsIndex.put(42, "Inverse Hilbert Matrix");
        problemsIndex.put(43, "Lennard-Jones Minimum Energy Cluster ");
        problemsIndex.put(44, "Weierstrass WithoutRotationAndShift");
        problemsIndex.put(45, "Modified Schwefel WithoutRotationAndShift");
        problemsIndex.put(46, "Expanded Schaffer WithoutRotationAndShift");
        problemsIndex.put(47, "Happy Cat WithoutRotationAndShift");
        problemsIndex.put(48, "y=x2+2x1+3");
        problemsIndex.put(900, "Face");
        problemsIndex.put(901, "AffineFace");
        problemsIndex.put(910, "Fashion ");
        problemsIndex.put(920, "Julia ");
        problemsIndex.put(930, "OneMaxColor ");
        problemsIndex.put(940, "HSVOneMax ");
        problemsIndex.put(951, "RandMidiMusic ");
        problemsIndex.put(952, "ScoreMusic");
        problemsIndex.put(953, "ChordMusic");
        problemsIndex.put(960, "Match");
    }

    private static GenecodeType getGenecodeType(int problemNum) {
        switch (problemNum) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
                return GenecodeType.DOUBLECODE;
            case 900:
            case 901:
            case 920:
                return GenecodeType.DOUBLECODE;
            case 910:
                return GenecodeType.STRINGCODE;
            case 930:
                return GenecodeType.BINARYCODE;
            case 940:
                return GenecodeType.DOUBLECODE;
            case 951:
            case 952:
            case 953:
                return GenecodeType.STRINGCODE;
            case 960: return GenecodeType.STRINGCODE;
        }
        return GenecodeType.DOUBLECODE;
    }

    public static void initProblem(int problemNum, GenecodeType genecodeType) {//设置problem对象
        try {
            currentProblem = ((Problem) Class.forName("core.problem.TGA.singleObjective.F" + problemNum)
                    .newInstance());
            currentProblem.setGenecodeType(null!=genecodeType?genecodeType:getGenecodeType(problemNum));
            currentProblem.init(dimensionNumber);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(FactoryProblems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void dealwithJulia(Population pop) {
        if (920 == problemNum) {//分形
            //进化后判断是否P，Q是可行解
            TreeSet<Individual> result = new TreeSet<>();
            for (Object indnew : pop.getIndividuals()) {
                int tryTime = 0;
                while (new FractualValidater((Individual) indnew).Validater() == true) {
                    if (tryTime++ > 1000) {
                        System.out.println("tryTime>10000 in AbstractAlgorithm");
                    }
                    indnew = (Individual) FactoryProblems.currentProblem.getIndividual(1).first();//生成一个新个体
                }
                result.add((Individual) indnew);
            }
            pop.setIndividuals(result);
        }
    }

    public static void initIndividualPanel(Population population, BaseIndividualRender[] onlyIndividualPanel) {
        if (problemNum == 920) {//分形
            //是图形界面的进化，初始化种群
            // System.out.println("初始化个体判断");
            dealwithJulia(population);
        }
        int i = 0;
        for (Object ind : population.getIndividuals()) {
            onlyIndividualPanel[i++] = new FactoryIGAGraph().getIndividualPanel((Individual) ind);
        }
    }

    public static void dealIEC(JPanel parentPane, Algorithm algorithm) {

        switch (problemNum) {
            case 900://Face
                parentPane.removeAll();
                //algorithm.registerProblems(this.problem);这个在PreFaceOptimization中处理
                parentPane.add(new PreFaceOptimization(algorithm));
                parentPane.validate();
                break;
            case 910://Fashion
            case 920://Julia
            case 930://OneMaxColor:
            case 940://HSVOneMax:
            case 951://RandMidiMusic:////////
                parentPane.removeAll();
                IECPanel iGAPanel = new IECPanel(parentPane);
                iGAPanel.createColleagues();
                parentPane.add(iGAPanel);
                parentPane.validate();
                break;
            case 952://ScoreMusic
                String s[];
                try {
                    File file = new File("resources/music/1.txt");
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        s = reader.readLine().split(",");
                        //ControlParameters.gsuCount = s.length / 2;
                    }
                } catch (IOException e1) {
                    System.out.println(e1);
                }
                parentPane.removeAll();
                iGAPanel = new IECPanel(parentPane);
                iGAPanel.createColleagues();
                parentPane.add(iGAPanel);
                parentPane.validate();
                break;
            case 953://ChordMusic
                parentPane.removeAll();
                iGAPanel = new IECPanel(parentPane);
                iGAPanel.createColleagues();
                parentPane.add(iGAPanel);
                parentPane.validate();
        }
    }

    public static String[] setPopulationSize() {
        String[] result = new String[2];
        if (currentProblem.isIECProblem()) {
            result[0] = "8";
            result[1] = "20";
        } else {
            result[0] = "50";
            result[1] = "10000";
        }
        return result;
    }

    public static String verifyStopEvaluation(int number) {
        if (problemNum < 900) {//传统函数优化
            if (number < 100 || number > 2000) {
                return "1000";
            } else {
                return String.valueOf(number);
            }
        } else if (problemNum >= 900 && problemNum < 1000) {
            if (number < 0 || number > 20) {
                return "160";
            }
        }
        return String.valueOf(number);
    }

    public static String setPopulationSize(int number) {
        if (problemNum < 900) {//传统函数优化
            if (number < 3 || number > 1000) {
                return "60";
            } else {
                return String.valueOf(number);
            }
        } else if (problemNum >= 20 && problemNum < 0) {
            if (number < 0 || number > 20) {
                return "20";
            }
        }
        return String.valueOf(number);
    }

    public static BaseIGAGraphIndividual getIndGraphPanel() {
        BaseIGAGraphIndividual result = null;
        switch (FactoryProblems.problemNum) {//详见FactoryProblems
            case 900:
                result = new Face();
                break;
            case 910:
                result = new FashionDesign();
                break;
            case 920:
                result = new BaseFractual();
                break;
            case 930:
                result = new OneMaxColor();
                break;
            case 940:
                result = new HSVOneMax();
                break;
            case 951:
                result = new RandomMidiMusic();
                break;
            case 952:
                result = new ScoreMusic();
                break;
            case 953:
                result = new ChordMusic();
                break;
            default:
        }
        return result;
    }

    public static String getName(int index, int dimension) {
        return "F" + index + problemsIndex.get(index) + "with dimension D_" + dimension;
    }
}
