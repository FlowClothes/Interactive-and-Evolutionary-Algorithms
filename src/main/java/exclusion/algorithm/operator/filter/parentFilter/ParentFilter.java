package exclusion.algorithm.operator.filter.parentFilter;

import core.algorithm.AbstractAlgorithm;
import core.algorithm.accessory.HistoryIndividuals;
import core.problem.Individual;
import core.problem.Population;
import exclusion.algorithm.operator.filter.Myfilter;
import java.util.Collection;
import java.util.TreeSet;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ParentFilter implements Myfilter {

    //是否要对双亲进行选择
    public static boolean parentFilte;
    //选择最近的几代
    public static int lifeAge;

    @Override
    public void filter(Collection<Individual> individuals) {
        if (parentFilte) {
//如果使用父代过滤，那么会加入历史个体，否则不加入；
//父代个体的选择，是一个策略，这里只选择前面两代
            TreeSet<Individual> candidateParent = HistoryIndividuals.getInstance().getParentAndUpdateHistory(individuals);
            if (!candidateParent.isEmpty()) {
                Population population = new Population();
                population.setIndividuals(candidateParent);
            }
            individuals.addAll(candidateParent);
            //当种群规模达到上限时
            while (individuals.size() > AbstractAlgorithm.upperBoundPopulationSize) {//强烈的收敛操作
                ((TreeSet<Individual>) individuals).pollFirst();//把最差的去除
            }
        }
    }
}
