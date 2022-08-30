package bigPouWithWRS_RS;

import core.algorithm.AbstractAlgorithm;
import core.algorithm.GeneticAlgorithm;
import core.algorithm.exploerOrExploit;
import static core.controllers.ControlParameters.rankProba;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Population;
import core.tools.MyMath.RandomGenerator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hgs
 */
public class BigPouAlgorithm extends GeneticAlgorithm {

    @Override
    public String getName() {
        return "BigPou Genetic Algorithm";
    }

    List<Individual> newSolutions = new LinkedList<>();
    List<Individual> tem = new LinkedList<>();
    boolean satisfied = false;//种群规模是否达到要求的值

    @Override
    public void ecapsule(List<Individual> operatedIndividuals, Population population) {
        satisfied = false;
        //获取过滤后个体的适应值
        newSolutions.clear();
        for (Individual ind : operatedIndividuals) {
            newSolutions.add(ind);
        }
        satisfied = (newSolutions.size() >= populationSize);
        while (!satisfied) {//如果种群规模未达到规定值，则反复调用进化操作生成新个体
            operatedIndividuals = operatePopulation(population);
            for (Individual ind1 : operatedIndividuals) {
                newSolutions.add(ind1);
                if (newSolutions.size() >= populationSize) {
                    satisfied = true;
                    break;//跳出最里边的循环
                }
            }
        }

        newSolutions.forEach((ind) -> {
            FactoryProblems.currentProblem.evaluate((Individual) ind);
        });
        //把List<Individual>加入到TreeSet中
        population.getIndividuals().clear();
        for (Individual ind : newSolutions) {
            population.getIndividuals().add(ind);
        }
        //System.out.println("种群规模=" + population.getIndividuals().size());
        if (population.getIndividuals().size() > populationSize) {
            population.getIndividuals().remove(population.getIndividuals().first());
        }
    }

    //Rank Selection的动态概率设置==========开始========================
    private final double[] suppower = {0.01, 0.05, 0.10, 0.2, 0.3,
        0.4, 0.5, 0.6, 0.7, 0.8,
        0.9, 1.0, 1.1, 1.2, 1.3,
        1.4, 1.5, 1.6, 1.7, 1.8,
        1.9, 2.0, 2.1, 2.2, 2.3,
        2.4, 2.5, 2.6, 2.7, 2.8,
        2.9, 3.0};
    private final double[][] probability = new double[suppower.length][AbstractAlgorithm.populationSize * 2];

    public void setRankProbability(exploerOrExploit myexplor) {
        int i = 0;
        switch (myexplor) {//i取较小值,较差的个体被选择的概率增大
            case EXPLORER:
                i = RandomGenerator.getRandom(suppower.length / 2);
                //System.out.println("i=" + i);
                break;
            case EXPLOIT://i取较大值,较差的个体被 选择的概率变小
                i = suppower.length / 2 + RandomGenerator.getRandom(suppower.length);
                if (i >= suppower.length) {
                    i = suppower.length - 2;
                }
                //System.out.println("i=" + i);
                break;
            case MEDIA://不确定
                i = RandomGenerator.getRandom(suppower.length);
                //System.out.println("i=" + i);
                break;
        }
        rankProba = probability[i];
    }

    public void setProbability() {
        for (int i = 0; i < probability.length; i++) {
            for (int j = 0; j < probability[i].length; j++) {
                probability[i][j] = Math.pow(i, j);
            }
        }
    }
}
