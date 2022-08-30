package core.algorithm.operator.Selection.triangle;

import core.algorithm.operator.Selection.SelectionCommonFunction;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import core.problem.Individual;
import core.problem.Population;
import core.tools.MyDataStructure.LinearList.ListTools;
import core.algorithm.operator.Selection.Selection;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class SinSelection implements Selection {

    @Override
    public List<Individual> getSelectionResult(Population population) {
        double fmax = ((Individual) population.getIndividuals().last()).getDecisionVariable().getFitness()[0], fmin = ((Individual) population.getIndividuals().first()).getDecisionVariable().getFitness()[0];
        //计算正弦适应值与适应值的和
        if (fmax == fmin) {//两者相等
            //不用选择了，交给交叉与变异去处理
            List<Individual> result = new LinkedList<>();
            result.addAll(population.getIndividuals());
            return result;
        }
        double diff = fmax - fmin;
        double[] temFitness = new double[population.getIndividuals().size()];
        boolean need2Normalized = false;
        int i = 0;
        for (Iterator it = population.getIndividuals().iterator(); it.hasNext();) {
            Individual ind = (Individual) it.next();
            temFitness[i] = (Math.sin(Math.PI / 2 * ((ind.getDecisionVariable().getFitness()[0] - fmin) / (diff))));
            if (temFitness[i] >= 1) {
                need2Normalized = true;
            }
            if (temFitness[i] < 0) {//可能TreeSet中Individual的fitness被改变了，现在尚未查出是什么时候改变的
                 System.out.println("bad in SinSelection==ind.getFitness[0]:" + ind.getDecisionVariable().getFitness()[0] + ",fmin:" + fmin + ",diff:" + diff);
                fmin = ind.getDecisionVariable().getFitness()[0];//重新设置最小值
                diff = fmax - fmin;
                for (int j = 0; j <= i; j++) {//重新计算选择适应值
                    temFitness[j] = (Math.sin(Math.PI / 2 * ((ListTools.getIndividualAt(j, population.getIndividuals()).getDecisionVariable().getFitness()[0] - fmin) / (diff))));
                }
                need2Normalized = true;
            }
            i++;
        }
        if (need2Normalized) {
            double sum = 0;
            i = 0;
            for (; i < temFitness.length; i++) {
                sum += temFitness[i];
            }
            i = 0;
            for (; i < temFitness.length; i++) {
                temFitness[i] = temFitness[i] / sum;
            }
        }
        population.setFitness4Selection(temFitness);
        //不能破坏individual的fitness信息，因为在后边还会用到，所以相对适应值用数组传递
//        for (int i = 0; i <  population.getIndividuals().size(); i++) {
//              population.getIndividuals().get(i).setFitness(new float[]{temFitness[i]});
//        }
        return new SelectionCommonFunction().getSelectionResult(population, 0);
    }

}
