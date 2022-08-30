package core.algorithm.operator.Selection;

import java.util.LinkedList;
import java.util.List;
import core.problem.Individual;
import core.problem.Population;
import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生 HAO Guo-Sheng 轮盘赌
 */
public class SelectionCommonFunction {

    public List<Individual> getSelectionResult(Population population, double minFitness) {
        //不同的选择算子，最小值会不同
        List<Individual> result = new LinkedList<>();
        double[] proUpLowNo = normalizedData(population, minFitness);
        double temf;
        for (Object indi : population.getIndividuals()) {
            temf = RandomGenerator.nextDouble();//生成的随机数在0与所有个体的适应值之和之间
//            System.out.println("最大值为："+proUpLowNo[proUpLowNo.length-1]+"，产生的随机数为："+temf);
            int j = 0;
            for (Object indj : population.getIndividuals()) {//遍历，找到对应的个体
                if (temf >= proUpLowNo[j] && temf < proUpLowNo[j + 1]) {
                    result.add(((Individual) indj).clone());
                    break;
                }
                j++;
            }
        }
        if (result.size() < population.getIndividuals().size()) {
            System.out.println("bad in selection common function where the population size is less than the specified one");
        }
        // }
        return result;
    }

    private double[] normalizedData(Population pop, double minFitness) {
        //把个体的适应值规范化到0到1之间
        double[] proUpLowNo = null;
        if (null == pop.getFitness4Selection()) {
            //准备好了专门用于选择的适应值
            proUpLowNo = normalizeData2ndStep(pop.getFitness4Selection());
        } else {//利用种群适应值
            double[] fitness4Selection = new double[pop.getIndividuals().size()];
            int i = 0;
            for (Object ind : pop.getIndividuals()) {
                fitness4Selection[i] = ((Individual) ind).getDecisionVariable().getFitness()[0] - minFitness;
                i++;
            }
            proUpLowNo = normalizeData2ndStep(fitness4Selection);
        }
        return proUpLowNo;
    }

    private double[] normalizeData2ndStep(double[] fitness) {
        double[] result = new double[fitness.length + 1];
        double sum = 0;
        for (int i = 0; i < fitness.length; i++) {
            sum += fitness[i];
        }
        for (int i = 1; i <= fitness.length; i++) {
            result[i] = fitness[i - 1] / sum;
        }
        result[fitness.length]=1.0;
        return result;
    }

    public static void main(String[] args) {
        int size=4;
        SelectionCommonFunction scf=new SelectionCommonFunction();
        double[] data=new double[size];
        for (int i = 1; i <= size; i++) {
            data[i-1]=i;
        }
        double[] sele=scf.normalizeData2ndStep(data);
        for (int i = 0; i < sele.length; i++) {
            System.out.println(sele[i]);
        }
    }
}
