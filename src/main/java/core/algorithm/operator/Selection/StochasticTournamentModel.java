package core.algorithm.operator.Selection;

import java.util.LinkedList;
import java.util.List;
import core.problem.Individual;
import core.problem.Population;
import core.tools.MyDataStructure.LinearList.ListTools;
import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class StochasticTournamentModel implements Selection {

    @Override
    public List<Individual> getSelectionResult(Population population) {
        List<Individual> result = new LinkedList<>();
        int k, j;
        int size = population.getIndividuals().size();
        for (Object individual : population.getIndividuals()) {
            k = RandomGenerator.getRandom(0, size);
            j = RandomGenerator.getRandom(0, size);
            Individual temk = ListTools.getIndividualAt(k, population.getIndividuals()),
                    temj = ListTools.getIndividualAt(j, population.getIndividuals());
            result.add((Individual) (temk.compareTo(temj) >= 0 ? temk.clone() : temj.clone()));
        }
        return result;
    }

}
