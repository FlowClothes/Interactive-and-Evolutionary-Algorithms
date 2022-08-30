package core.problem.DecisionVariables;

import core.problem.Decoder.BinaryDeCoder;
import core.problem.FactoryProblems;
import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class BinarycodeDecisionVariable extends StringDecisionVariable {

    public int[] getCodeLengh() {
        return codeLengh;
    }

    public void setCodeLengh(int[] codeLengh) {
        this.codeLengh = codeLengh;
    }
    private int codeLengh[];//决策变量中各变量对应的二进制串长度

    public void calculateX() {
        BinaryDeCoder bEDCode = new BinaryDeCoder();
        bEDCode.setVirtualCodeStr(new String[]{this.getGenecodesString()});
        this.setGeneCodes(bEDCode.decode(this.getGenecodesString(), FactoryProblems.currentProblem.getVariableSplit(), FactoryProblems.currentProblem.getVariableProperties()));
    }

    @Override
    public GenecodeType getGenecodeType() {
        return GenecodeType.BINARYCODE;//表示二进制编码
    }

    @Override
    public String getGeneralGenecodeString() {
        return this.getGenecodesString();
    }

    @Override
    public char flip(char ch, int mutateStep) {
//对于二进制的变异，其exploration和exploitation直接由变异位置来自动调节，而不依赖于mutateStep
//在高位变异，则是exploration;在低位变异，则是exploitation
        return (ch == '0') ? '1' : '0';
    }

    @Override
    public DecisionVariable initVariable() {
        BinarycodeDecisionVariable result = (BinarycodeDecisionVariable) this.clone();
        String temGenecode = "";
        for (int i = 0; i < FactoryProblems.currentProblem.getGsuCount(); i++) {
            for (int j = 0; j < getCodeLengh()[i]; j++) {
                if (RandomGenerator.nextDouble() > 0.5) {
                    temGenecode += "0";
                } else {
                    temGenecode += "1";
                }
            }
        }
        result.setGenecodesString(temGenecode);
        return result;
    }

    @Override
    public DecisionVariable clone() {
        BinarycodeDecisionVariable result = new BinarycodeDecisionVariable();
        result.setCodeLengh(this.getCodeLengh());
        result.setGeneCodes(geneCodes);
        result.setGenecodesString(genecodesString);
        result.setMutationScope(mutationScope);
        return result;
    }
}
