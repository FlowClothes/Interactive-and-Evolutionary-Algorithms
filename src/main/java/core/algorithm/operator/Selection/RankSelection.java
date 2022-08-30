package core.algorithm.operator.Selection;

import core.controllers.ControlParameters;
import java.util.List;
import core.problem.Individual;
import core.problem.Population;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class RankSelection implements Selection {

    @Override
    public List<Individual> getSelectionResult(Population population) {
        double[] probability;
        if (ControlParameters.rankProba == null) {//如果没有给出rank的比例，则在这里给出
            probability = new double[population.getIndividuals().size()];
            double divisor=((double) (population.getIndividuals().size()-1) * (double) population.getIndividuals().size());
            for (int i = 0; i < probability.length; i++) {
                probability[i] = (2.0 * i) /divisor; 
            }
        } else {
            probability = ControlParameters.rankProba;
        }
        population.setFitness4Selection(probability);
        return new SelectionCommonFunction().getSelectionResult(population,((Individual)(population.getIndividuals().first())).getDecisionVariable().getFitness()[0]);//只适用于单目标
    }

}
