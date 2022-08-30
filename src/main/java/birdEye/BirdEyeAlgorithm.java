package birdEye;

import core.algorithm.FactoryAlgorithm;
import core.algorithm.GeneticAlgorithm;
import core.problem.Individual;
import core.problem.Population;
import core.tools.MyMath.RandomGenerator;
import java.util.List;

/**
 *
 * @author hgs
 */
public class BirdEyeAlgorithm extends GeneticAlgorithm {

    @Override
    public List<Individual> operatePopulation(Population pop) {
        if (core.controllers.ControlParameters.birdEye) {//依据个体适应值设置个体的exploreration和exploitation
            int i = pop.getIndividuals().size();
            for (Object ind : pop.getIndividuals()) {
                i--;
                Individual temind = (Individual) ind;
                temind.setExplo_ra_itationFact(i / core.controllers.ControlParameters.birdLevelNumber);
            }
            //可以设置动态自适应的ControlParameters.birdLevelNumber，从而实现exploration和Exploitation的自适应调节
        }
        return super.operatePopulation(pop);
    }

    @Override
    public String getName() {
        return "BirdEye Genetic Algorithm";
    }
}
