package core.algorithm.operator.Crossover;

import java.util.List;
import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public interface Crossover {

    public List<Individual> getCrossoverResult(List<Individual> inputtedIndividual);

    public float getCrossoverProbability();

    public void setCrossoverProbability(float crossoverProbability);

    public int getCrossoverNumber();

    public void setCrossoverNumber(int crossoverNumber);

}
