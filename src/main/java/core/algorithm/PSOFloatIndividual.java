package core.algorithm;

import java.util.logging.Level;
import java.util.logging.Logger;
import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class PSOFloatIndividual extends DoubleDecisionVariable {

    private PSOFloatIndividual historyBest;
    private double[] maxVector;
    private double[] oldVariableVector;//保存变量每一维上原有的速度
    private double[] newVariableVector;//保存变量每一维上更新后的速度

    public PSOFloatIndividual(double[] genecodes) {
        super(genecodes);
    }

    public void updatePosition(Problem problem, PSOFloatIndividual bestIndividual) {
        //3.1更新速度

        //3.2更新位置
    }

    private double[] getMaxVector(Problem problem) {
        if (null == maxVector) {
            maxVector = new double[problem.getGsuCount()];
            for (int i = 0; i < maxVector.length; i++) {
                // maxVector[i] = problem.getVariableProperties()[i][2] * 5;
            }
        }
        return maxVector;
    }

    public PSOFloatIndividual getHistoryBest() {
        if (null == historyBest) {
            historyBest = (PSOFloatIndividual) this.clone();
        }
        return historyBest;
    }

    public void setHistoryBest(PSOFloatIndividual historyBest) {
            this.historyBest = (PSOFloatIndividual) historyBest.clone();
    }

    public void reInitiate() {//如果与其他个体重复，则重新生成

    }
}
