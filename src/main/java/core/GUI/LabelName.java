/*
 *
 * 该类只访问setup.conf文件中language属性内容,任何试图影响本类的尝试,请直接修改setup.conf文件即可
 */
package core.GUI;

import core.tools.Files.FileIO;
import core.tools.Files.FileProperties;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class LabelName {

    public static int languageIndex = 0;
    public static FileIO fp = new FileProperties();
    public static int exitIndex = 1002, ChineseIndex = 1001, EnglishIndex = 1000;
    public static String
            English = "English", Chinese = "简体中文",
            geneCodeType[],
            partOfFace[],
            SetParametesGUI_SelectionTypeValue[],
            SetParametersGUI_CrossoverTypeValue[],
            SetParametersGUI_MutationTypeValue[],
            fitnessGradation[],
            SetParametersGUI_fitnessAssingMethods[],
            SetParametersGUI_knowledge[],
            title, exit, file, languageMenu, run, help,
            tga, iga, igaAidTools, igaAidMusic,
            language,
            resourcePath,
            delterValueInPreFaceOptimization,
            affineCheckInPreFaceOptimization,
            userPreferenceInPreFaceOptimization,
            beginButtonInPreFaceOptimization,
            goonEvolvingInIGAPanel,
            SatisfactionInIGAPanel,
            StatusTextInIGAPanel,
            noSupported,
            MyOK,
            Yes, Not,
            SetParametersGUI_Tabbed_Operaters,
            SetParametersGUI_Tabbed_Running,
            SetParametersGUI_Tabbed_KnowledgeBased,
            SetParametersGUI_LoadDefault,
            SetParametersGUI_SelectionType,
            SetParametersGUI_Instruction,
            SetParametersGUI_CrossoverTypeLabel,
            SetParametersGUI_CrossoverPointnumLabel,
            SetParametersGUI_CrossoverProbabilityLabel,
            SetParametersGUI_MutationTypeLabel,
            SetParametersGUI_MutationPointNumLabel,
            SetParametersGUI_MutationProbabilityLabel,
            SetParametersGUI_BorderSelection,
            SetParametersGUI_BorderCrossover,
            SetParametersGUI_BorderMutation,
            SetParametersGUI_BorderOthers,
            SetParametersGUI_KnowledgeIDLabel,
            SetParametersGUI_DelterValueLabel,
            SetParametersGUI_CommonNumLabel,
            SetParametersGUI_ReappearingLabel,
            SetParametersGUI_PopulationSize,
            SetParametersGUI_StopEvaluation,
            SetParametersGUI_FitnessAssign,
            SetParametersGUI_FitnessAssignLabel,
            SetParametersGUI_GenecodeType,
            SetParametersGUI_ExperimentRunningTimes,
            SetParametersGUI_Tabbed_ExperimentBased,
            HowSatisfactory;

    @SuppressWarnings("static-access")
    private void setLabelName(int languageindex) {
        this.languageIndex = languageindex;
        switch (languageindex) {
            case 0://English
                language = "English?简体中文";
                this.fitnessGradation = new String[]{"Most Satisfied", "More Satisfied", "Moderate Satisfied", "Satisfied", "Dissatisfied", "Moderate Dissatisfied", "More Dissatisfied", "Most Dissatisfied"};
                title = "Desktop Genetic Algorithms";
                exit = "Exit";
                file = "File";
                languageMenu = "语言/Language";
                run = "Run";
                help = "Help";
                tga = "Traditional Genetic Algorithm";
                iga = "Interactive Genetic Algorithm";
                igaAidTools = "Interactive Genetic Algoirthm Aid tools";
                igaAidMusic = "MIDI Music Optimization";
                partOfFace = new String[]{"Head outline", "Cheek outline", "Chin outline", "Eye", "Nose", "Eyebrow", "Mouth", "Whole face"};
                SetParametersGUI_GenecodeType = "Set parameter for genecode type";
                geneCodeType = new String[]{"Binary code type", "Real value type", "String type", "Default type"};
                delterValueInPreFaceOptimization = "For example,the 0.5 is selected, and the result is showed as in the up figure.";
                affineCheckInPreFaceOptimization = "Adopt Affine Transformation";
                userPreferenceInPreFaceOptimization = "Adopt the Methods to Abstract user preference";
                beginButtonInPreFaceOptimization = "Begin Evolving";
                goonEvolvingInIGAPanel = "Go on Evolving";
                SatisfactionInIGAPanel = "I am satisfied";
                StatusTextInIGAPanel = "The first image is the best one from the previous generation.\n The generation is: ";
                Yes = "Yes";
                Not = "No";
                MyOK = "OK";
                noSupported = "Still not supported";
                SetParametersGUI_Tabbed_Operaters = "EC Operators Parameters";
                SetParametersGUI_Tabbed_Running = "Running Parameters";
                SetParametersGUI_Tabbed_KnowledgeBased = "Knowledge-Based Parameters";
                SetParametersGUI_LoadDefault = "Loda Defalut Parameters";
                SetParametersGUI_SelectionType = "Selection Opertators Type";
                SetParametesGUI_SelectionTypeValue = new String[]{
                        "Sine Selection",// "Cosine Selection",
                        "Tangent Selection", //"Cotangent Selection",
                        "Rank Selection",
                        "Stochastic Toumament Selection",
                        "Roulette Wheel Selection",
                        "Immunity Selection"};
                SetParametersGUI_Instruction = "Here the parameters about evolution operators, running time can be set.\n In order to improve the performance of evolution, the parameters of knowledge to be abstracted and made use of can also be set.\n\n\n\nIn addition, different population size is also permitted to set, such as 10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160 in one running of the experiment to test the sensitivity of algorithm.\n The experiments results is stored in Excel files which can be found in the project fold.";
                SetParametersGUI_BorderSelection = "Selection operators";
                SetParametersGUI_BorderCrossover = "Crossover operators";
                SetParametersGUI_BorderMutation = "Mutation operators";
                SetParametersGUI_BorderOthers = "Other evolutionary parameters";
                SetParametersGUI_CrossoverTypeLabel = "CrossoverType:";
                SetParametersGUI_CrossoverTypeValue = new String[]{"One point crossover", "Multi-point crossover"};
                SetParametersGUI_CrossoverPointnumLabel = "Crossover point number is:";
                SetParametersGUI_CrossoverProbabilityLabel = "Crossover probability is:";
                SetParametersGUI_MutationTypeLabel = "Mutation type:";
                SetParametersGUI_MutationTypeValue = new String[]{"One point mutation", "Multi-Point Mutation"};
                SetParametersGUI_MutationPointNumLabel = "Mutation point number is:";
                SetParametersGUI_MutationProbabilityLabel = "Mutation probability is:";
                SetParametersGUI_KnowledgeIDLabel = "Which kind of knowledge do you want to make use of in the evloution?";
                SetParametersGUI_DelterValueLabel = "The delter value is:";
                SetParametersGUI_CommonNumLabel = "How many GSU should be executed common operators:";
                SetParametersGUI_ReappearingLabel = "Allow the same individual re-appear in the same population or not?";
                SetParametersGUI_FitnessAssignLabel = "Which fitness assign method do you like?";
                SetParametersGUI_PopulationSize = "Population size: ";
                SetParametersGUI_StopEvaluation = "Evaluation time for evolution stop";
                SetParametersGUI_FitnessAssign = "Fitness assign methods:";
                SetParametersGUI_fitnessAssingMethods = new String[]{"Fuzzy fitness assignment", "Accurate fitness assignment", "Grade fitness assignment", "Eye track assignment", "Inteval fitness assignment", "Most safisfied fitness assignment"};
                SetParametersGUI_knowledge = new String[]{"Individuals in the same population should not reappear", "Individauls in the same and last population should not reappear", "Histtory individuals should not be visited again"};
                SetParametersGUI_ExperimentRunningTimes = "Running Times for This Experiment";
                SetParametersGUI_Tabbed_ExperimentBased = "Experiments' Parameters";
                HowSatisfactory = "How Satisfactory?";
                break;
            case 1://汉语
                language = "简体中文?English";
                this.fitnessGradation = new String[]{"最满意", "很满意", "比较满意", "满意", "不满意", "比较不满意", "很不满意", "最不满意"};

                geneCodeType = new String[]{"二进制编码", "实数编码", "字符串编码", "默认编码"};
                title = "遗传算法通用系统桌面版";
                exit = "退出(X)";
                file = "文件(F)";
                languageMenu = "语言/language(T)";
                run = "运行(R)";
                help = "帮助(H)";
                tga = "传统的遗传算法(T)";
                iga = "交互式遗传算法(I)";
                igaAidTools = "交互式遗传算法的几个曾经用到的辅助工具(A)";
                igaAidMusic = "音乐进化";
                partOfFace = new String[]{"头部轮廓", "脸颊轮廓", "下巴轮廓", "眼睛", "鼻子", "眉毛", "嘴巴", "整个人脸",};
                delterValueInPreFaceOptimization = "如上图所示,选择了0.5作为Delt的值.";
                affineCheckInPreFaceOptimization = "采用仿射变换";
                userPreferenceInPreFaceOptimization = "采用提取用户偏好知识的方法";
                beginButtonInPreFaceOptimization = "开始进化";
                goonEvolvingInIGAPanel = "继续进化";
                SatisfactionInIGAPanel = "我得到满意解了";
                StatusTextInIGAPanel = "第1个图像是上一代的最满意图像，当前进化代数是第: ";
                MyOK = "确定";
                noSupported = "目前尚不支持";
                Yes = "是";
                Not = "否";
                SetParametersGUI_Tabbed_Operaters = "进化算子参数设置";
                SetParametersGUI_Tabbed_Running = "运行时参数设置";
                SetParametersGUI_Tabbed_KnowledgeBased = "基于知识的参数设置";
                SetParametersGUI_LoadDefault = "使用默认参数";
                SetParametersGUI_SelectionType = "选择算子类型";
                SetParametesGUI_SelectionTypeValue = new String[]{
                        "正弦选择", //"余弦选择",
                        "正切选择", //"余切选择",
                        "排序选择",
                        "随机锦标赛选择",
                        "轮盘赌选择",
                        "免疫选择"};
                SetParametersGUI_Instruction = "这里,包括进化算子的参数,运行过程的参数都可以进行设置.\n另外,某些问题可以通过设置相关的知识提取与利用参数来提高算法性能.\n。另外可以通过设置不同的种群规模，如10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160等一次性进行实验，检测算法对种群规模的敏感性。\n 实验运行的结果保存在Excel文件中，该文件位于本项目的目录下。";
                SetParametersGUI_BorderSelection = "选择算子";
                SetParametersGUI_BorderCrossover = "交叉算子";
                SetParametersGUI_BorderMutation = "变异算子";
                SetParametersGUI_BorderOthers = "其它进化算子相关参数:";
                SetParametersGUI_CrossoverTypeLabel = "交叉类型:";
                SetParametersGUI_CrossoverTypeValue = new String[]{"单点交叉", "多点交叉"};
                SetParametersGUI_CrossoverProbabilityLabel = "交叉概率:";
                SetParametersGUI_CrossoverPointnumLabel = "交叉点数目:";
                SetParametersGUI_MutationTypeLabel = "变异类型:";
                SetParametersGUI_MutationTypeValue = new String[]{"单点变异", "混沌变异"};
                SetParametersGUI_MutationPointNumLabel = "变异点数目:";
                SetParametersGUI_MutationProbabilityLabel = "变异概率:";
                SetParametersGUI_KnowledgeIDLabel = "在进化过程中,采用哪种知识?";
                SetParametersGUI_DelterValueLabel = "设置delter值为：";
                SetParametersGUI_CommonNumLabel = "求同数目为：";
                SetParametersGUI_ReappearingLabel = "是否允许种群内出现重复个体";
                SetParametersGUI_FitnessAssignLabel = "赋予适应值的方法：";
                SetParametersGUI_PopulationSize = "种群规模:";
                SetParametersGUI_StopEvaluation = "进化终止评价次数";
                SetParametersGUI_FitnessAssign = "适应值赋值方法";
                SetParametersGUI_fitnessAssingMethods = new String[]{"模糊赋值方法", "精确赋值方法", "分等级赋值方法", "Eye track赋值方法", "区间赋值方法", "最值赋值方法"};
                SetParametersGUI_knowledge = new String[]{"同一种群不出现重复个体", "上一和同一种群个体不再重复出现", "历史非最优个体不再出现"};
                SetParametersGUI_GenecodeType = "决策变量编码格式";
                SetParametersGUI_ExperimentRunningTimes = "实验执行趟数";
                SetParametersGUI_Tabbed_ExperimentBased = "实验参数设置";
                HowSatisfactory = "满意程度";
                break;
            case 2:
                break;

            default:
        }
    }

    public void init() {
        resourcePath = "resources/";
        //setup.conf只处理是英文或中文一个配置
        File filePro = new File(resourcePath + "setup.conf");
        try {
            if (!filePro.exists()) {
                filePro.createNewFile();
                fp.setValue("language", "1");
                fp.writeToFile(resourcePath + "setup.conf");
            } else {
                //从外部文件中读语言类型
                fp.readFromFile(resourcePath + "setup.conf");
                languageIndex = Integer.parseInt(fp.getValue("language").trim());
            }
        } catch (IOException ex) {
            Logger.getLogger(LabelName.class.getName()).log(Level.SEVERE, null, ex);
        }
        //设置相关内容
        this.setLabelName(languageIndex);
    }
}
