package core.problem.TGA.singleObjective;

import core.problem.DecisionVariables.BinarycodeDecisionVariable;
import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;
import java.util.LinkedList;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
//较大的一个值是:3.8502737665964863
public class F0 extends Problem<DoubleDecisionVariable> {
//当搜索空间是关于0对称时，这是一个简单的多模（2模）问题。

    @Override
    public F0 init(int dimension) {
        this.dimension = 1;
        stopFitness = new double[]{3.85027};
        variableProperties = new double[][]{{2, -2, 0.001}};
        switch (getGenecodeType()) {
            case DOUBLECODE:
                this.setDecisionVariable(new DoubleDecisionVariable());
                break;
            case BINARYCODE:
                BinarycodeDecisionVariable binarycodeDecisionVariable = new BinarycodeDecisionVariable();
                int[] codeLength = new int[]{Integer.toBinaryString((int) ((variableProperties[0][0] - variableProperties[0][1]) / variableProperties[0][2])).length()};
                binarycodeDecisionVariable.setCodeLengh(codeLength);
                this.setDecisionVariable(binarycodeDecisionVariable);
                this.setVariableSplit(new int[]{0, codeLength[0]});
                break;
        }
        if (xListData.isEmpty()) {
            xListData.add(new LinkedList<>());
            yListData.add(new LinkedList<>());
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {//传过来的是FloatIndividual类型，即决策变量是浮点型
        //浮点编码格式
        switch (getGenecodeType()) {
            case BINARYCODE:
                ((BinarycodeDecisionVariable) inputedIndividual.getDecisionVariable()).calculateX();
                inputedIndividual.getDecisionVariable().setFitness(new double[]{inputedIndividual.getDecisionVariable().getGeneCodes()[0] * Math.sin(10 * Math.PI * inputedIndividual.getDecisionVariable().getGeneCodes()[0]) + 2.0f});
                break;
            case DOUBLECODE:
                inputedIndividual.getDecisionVariable().setFitness(new double[]{inputedIndividual.getDecisionVariable().getGeneCodes()[0] * Math.sin(10 * Math.PI * inputedIndividual.getDecisionVariable().getGeneCodes()[0]) + 2.0f});
                break;
        }
    }

    public static void main(String[] args) {
        System.out.println(Double.MAX_VALUE);
    }

    @Override
    public void generateBackground() {
        yListData.get(0).clear();
        xListData.get(0).clear();
        double step = (this.getVariableProperties()[0][0] - this.getVariableProperties()[0][1]) / 300;
        for (double i = this.getVariableProperties()[0][1]; i < this.getVariableProperties()[0][0]; i = i + step) {//300个数据显示，应该足够了
            xListData.get(0).add(i);
            //其他决策变量的取值
            Individual individual = new Individual(new DoubleDecisionVariable(new double[]{i}));
            this.evaluate(individual);
            yListData.get(0).add(individual.getDecisionVariable().getFitness()[0]);
        }
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(0, dimension);
    }
}
