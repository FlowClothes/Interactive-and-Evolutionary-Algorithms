package core.algorithm.operator.Selection.triangle;

import core.algorithm.FactoryAlgorithm;
import core.algorithm.operator.Selection.SelectionCommonFunction;
import java.util.List;
import core.problem.Individual;
import core.problem.Population;
import core.algorithm.operator.Selection.Selection;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class TanSelection implements Selection {

    @Override
    public List<Individual> getSelectionResult(Population population) {
        double fmax = ((Individual)population.getIndividuals().last()).getDecisionVariable().getFitness()[0], fmin = ((Individual)(population.getIndividuals().first())).getDecisionVariable().getFitness()[0];
        double[] virtualFitness=new double[population.getIndividuals().size()];
        int i=0;
        for (Object ind: population.getIndividuals()) {
            virtualFitness[i++]=Math.tan(
                Math.PI / 4 * ((((Individual)ind).getDecisionVariable().getFitness()[0] - fmin)
                / (fmax - fmin)));
        }
        population.setFitness4Selection(virtualFitness);

        return new SelectionCommonFunction().getSelectionResult(population,0);
    }
}
