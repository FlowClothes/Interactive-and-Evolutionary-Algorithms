package core.algorithm.operator.Selection;

import java.util.List;

import core.problem.Individual;
import core.problem.Population;

/**
 * @author 郝国生 HAO Guo-Sheng
 */
public class RouletteWheelSelection implements Selection {

    @Override
    public List<Individual> getSelectionResult(Population population) {
        return new SelectionCommonFunction().getSelectionResult(population, ((Individual) population.getIndividuals().first()).getDecisionVariable().getFitness()[0]);
    }
}
