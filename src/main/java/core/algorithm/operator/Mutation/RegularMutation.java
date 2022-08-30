package core.algorithm.operator.Mutation;

import java.util.*;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.DecisionVariables.GenecodeType;
import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class RegularMutation implements Mutation {

    @Override
    public List<Individual> getMutationResult(List<Individual> inputtedIndividuals) {
        List<Individual> result = new LinkedList<>();
        for (int i = 0; i < inputtedIndividuals.size(); i++) {
            Individual tem = (Individual) inputtedIndividuals.get(i);
            //变异前
            for (int j = 0; j < tem.getMutationNumber(); j++) {
                tem = this.mutate(tem);
            }
            result.add(tem);
        }
        return result;
    }

    protected Individual mutate(Individual passedind) {
        Individual result =(Individual) passedind.clone();
        if (passedind.getDecisionVariable().getGenecodeType() == GenecodeType.AFFINEFACECODE || passedind.getDecisionVariable().getGenecodeType() == GenecodeType.NORMALFACECODE) {
            double[] temDoubleArray = passedind.getDecisionVariable().getGeneCodes();
            int muteLocation = (int) RandomGenerator.getRandom(1, temDoubleArray.length - 1);
            //根据变异位置确定变异顶点的编号及其变异坐标，然后才能确定是什么样的变化范围
            //muteLocation其实就是FaceEncode_DecoderforIndividual中的rows
            double muteScope[] = FactoryProblems.currentProblem.getVariableProperties()[muteLocation];
            temDoubleArray[muteLocation] = RandomGenerator.getRandom(muteScope[0], muteScope[1]);
            result.getDecisionVariable().setGeneCodes(temDoubleArray);
        } else {
            result.setDecisionVariable(passedind.getDecisionVariable().mutate(passedind.getExplo_ra_itationFact(),passedind.getMutationNumber()));
        }
        return result;
    }
}
