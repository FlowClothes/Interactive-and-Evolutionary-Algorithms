package core.algorithm.operator.Mutation;

import java.util.List;
import core.problem.Individual;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public interface Mutation {
    public List<Individual> getMutationResult(List<Individual> inputtedIndividuals);
}
