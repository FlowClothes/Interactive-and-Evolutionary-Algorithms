package exclusion.algorithm.operator.filter.offspringFilter;

import core.algorithm.AbstractAlgorithm;
import core.algorithm.accessory.HistoryIndividuals;
import exclusion.algorithm.operator.filter.Myfilter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import core.problem.DecisionVariables.DecisionVariable;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class OffspringFilter implements Myfilter {

    //作用于决策空间，但也依赖于目标空间
    @Override
    public void filter(Collection<Individual> operatedIndividuals) {
        filter1stStep((List<Individual>) operatedIndividuals);//这一步对个体进行筛查，重复的将被替换
        supplement((List<Individual>) operatedIndividuals);
    }

    private void supplement(List<Individual> operatedIndividuals) {
        //经过过滤，有些个体被过滤，要补充一下
        while (operatedIndividuals.size() < AbstractAlgorithm.populationSize) {
            Individual newIndividual;
            double rand = RandomGenerator.nextDouble();
            if (rand < 0.5) {///随机初始化新个体--Exploration
                newIndividual = (Individual) FactoryProblems.currentProblem.getIndividual(1).first();
            } else {//对最优解变异生成新个体--Exploitation
                newIndividual = HistoryIndividuals.getInstance().getHistory4Parent().last().getIndividual().mutate();
            }
            FactoryProblems.currentProblem.evaluate(newIndividual);
            operatedIndividuals.add(newIndividual);//从最优个体变异得到新个体 
        }
    }

    private void filter1stStep(List<Individual> operatedIndividuals) {
        //初始化保存要排斥的个体的列表
        List<Individual> excluded = new LinkedList<>();
        for (Iterator it = operatedIndividuals.iterator(); it.hasNext();) {
            //对每个解判断看是否落在了排斥半径内
            Individual ind = (Individual) it.next();
            if (getExcluded(ind)) {
                excluded.add(ind);
            }
        }
        //替换要排斥的解
        //这里的策略直接影响解的质量
        //1.与最优个体交叉
        //2.重新初始化
        excluded.forEach((ind) -> {
            if (RandomGenerator.nextDouble() > 0.3) {
                ind.setDecisionVariable(ind.mutate().getDecisionVariable());
            } else {
                ind.setDecisionVariable(((Individual) (FactoryProblems.currentProblem.getIndividual(1).last())).getDecisionVariable());
            }

        });
    }

    private boolean getExcluded(Individual ind) {
        //检查是否落在历史个体的排斥半径内
        //如果历史上存在这个个体，则直接求排斥半径，否则求其上界和下界，然后再看是否落在上或下界的排斥半径内
        boolean result;
        boolean existed = HistoryIndividuals.getInstance().getHistoryDecisonVariables().contains(ind.getDecisionVariable());
        if (existed) {
            result = true;
        } else {
            DecisionVariable neighbor = HistoryIndividuals.getInstance().getHistoryDecisonVariables().ceiling(ind.getDecisionVariable());
            if (null == neighbor) {//没有落在排斥半径内
                return false;
            }
            double distance = neighbor.getDistance(ind.getDecisionVariable());
            if (distance < HistoryIndividuals.getInstance().getDecisionVariableWithRadius().get(neighbor)) {
                //落在上界的排斥半径内，所以排斥
                result = true;
            } else {
                neighbor = HistoryIndividuals.getInstance().getHistoryDecisonVariables().floor(ind.getDecisionVariable());
                if (null == neighbor) {
                    return false;
                }
                distance = neighbor.getDistance(ind.getDecisionVariable());
                result = distance < HistoryIndividuals.getInstance().getDecisionVariableWithRadius().get(neighbor);
            }
        }
        return result;
    }
}
