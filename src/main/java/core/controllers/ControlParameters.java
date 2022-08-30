package core.controllers;

import java.util.List;
import core.problem.Individual;
import core.tools.Files.FileProperties;

/**
 *
 * @author 郝国生 HAO Guo-Sheng 这个类中的内容起到一个作用时，希望在运行时能节约内存空间
 */
public class ControlParameters {

    //public static boolean FaceAffine = false;
    //下面这些参数的意义，可以参照本类中的方法setDefaultParameters查看相关意义
    public static boolean needVisualization = false;
    public static int experimentRunningTime;
    //public static String codeScope;//编码，如果编码为01
    //public static float competeProbability;//竞争概率，是指选择算子和交叉算子并行而非串行执行时，两者的竞争概率，随机数小于该概率值，则执行选择，否则执行交叉
    public static int commNum = 0;//求同数，主要用于交互式进化计算，在提取基因意义单元知识时用
    public static int constrainType;//约束类型
    public static double deltValue = 0;//正态分布时，两侧取值
    public static int fitnessAssign = 0;//适应值分配方式，对于交互式进化计算，有区间赋值，有评价（或称为离散赋值），有模糊赋值等
    // public static int populationSize = 100;//种群规模,默认值，在分割搜索空间时，需要这个值

    public static double[] rankProba;//随机概率值
    public static String resource = "resources/";//图片等资源存放所在
    public static int stopEvaluationNumber;//终止进化的代数设置，当大于该值时，进化停止
    public static int thresholdNumber2Split = 20;//这个值可以被替换成种群规模的1/k，即当一个grid中占有资源太多时，则分割；当然也可以设置成种群规模*进化代数*1/k；也可以在运行时修改这个值
    //public static List<Integer> populationsizeList = new LinkedList<>();//保存实验中采用的种群大小list，如10,20,30,40,50,60等，在PSO中是swarm的大小
    public static String setupFileName = "resources/Controlparameters.conf";
    public static String TableName = "";//Exclusive操作算子对应的表名，不同的实验表明会不一样

    public static String dynamic = " Evoluation algoirthms dynamicis";
    public static final int bestSolutionSize = 10;
    //================================现场恢复===========================
    //现场中断后，需要恢复的参数类
    //public static int currentPopulationsize;//当前进行到的种群规模
    public static int currentExperimentNumber;//当前运行到的实验次数

    public static final String scenceFileName = "resources/spotScenceFile.conf";
    public static List<Individual> currentIndividuals;
    public static FileProperties fp = new FileProperties();
    public static boolean willContinue = false;
    public static boolean birdEye;
    public static int birdLevelNumber=3;
}
