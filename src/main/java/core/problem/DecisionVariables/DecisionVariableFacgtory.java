package core.problem.DecisionVariables;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class DecisionVariableFacgtory {
    //fitness定义了目标的个数,binaryLength1定义了二进制编码的长度
    public static DecisionVariable initBinarycodeDecisionVariable(){
        return new BinarycodeDecisionVariable();
    }
    public static DecisionVariable initFloatDecisionVariable(){
        return new DoubleDecisionVariable();
    }
    public static DecisionVariable initStringDecisionVariable(){
        return new StringDecisionVariable();
    }
}
