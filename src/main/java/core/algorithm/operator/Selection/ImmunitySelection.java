package core.algorithm.operator.Selection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import core.problem.Individual;
import core.problem.Population;
import core.tools.MyDataStructure.LinearList.ListTools;
import core.tools.MyMath.RandomGenerator;

/**
 * @author 郝国生 HAO Guo-Sheng 轮盘赌
 */
public class ImmunitySelection implements Selection {

    public List<Individual> getSelectionResult(Population population) {
        //不同的选择算子，最小值会不同
//        List<Individual> result = new LinkedList<>();


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
        for (Iterator it = population.getIndividuals().iterator(); it.hasNext(); ) {
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


//        double[] proUpLowNo = normalizedData(population, minFitness);
        double[] fitness = population.getFitness4Selection();
        fitness = introductionConcentration(fitness);
        population.setFitness4Selection(fitness);

        return new SelectionCommonFunction().getSelectionResult(population, 0);
        
        //return new SelectionCommonFunction().getSelectionResult(population,((Individual)(population.getIndividuals().first())).getDecisionVariable().getFitness()[0]);//只适用于单目标

    }


    public double[] introductionConcentration(double[] fitness) {
        //不再只关注适应度fitness，加入浓度计算，选择的标准p等于适应度概率pf加抗体浓度概率pd
        //1、求pd、pf和亲和度 2、求p 3、最后赋值fitness = p
        int N = fitness.length;
        double all = 0;
        for (double v : fitness) {
            all = all + v;
        }
        double[] pf = new double[N];
        for (int i = 0; i < N; i++) {//求出每个抗体的适应度概率pf
            pf[i] = fitness[i] / all;
        }
        double[][] affinity = new double[N][N];
        for (int i = 0; i < N; i++) {        //计算两个抗体之间的亲和度
            for (int j = 0; j < N; j++) {
                affinity[i][j] = 1 / (1 + Math.abs(pf[i] - pf[j]));
            }
        }
        //根据亲和度找到抗体浓度最大的群体
        int[] temp_num = new int[N];
        int[][] temp_index = new int[N][];
        HashSet<Integer> temp = new HashSet<Integer>();
        for (int i = 0; i < N; i++) {//两个数，如果大的数乘以0.9小于小的数，那么这两个数相似
            temp_num[i] = 0;
            for (int j = 0; j < N - 1; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (affinity[i][j] > affinity[i][k]) {
                        if (affinity[i][j] * 0.9 <= affinity[i][k]) {
                            temp.add(k);
                        }
                    } else if (affinity[i][j] <= affinity[i][k]) {
                        if (affinity[i][k] * 0.9 <= affinity[i][j]) {
                            temp.add(k);
                        }
                    }
                }
            }
            temp_num[i] = temp.size();
            temp_index[i] = new int[temp_num.length];
            for (int s : temp) {
                int a = 0;
                temp_index[i][a] = s;
                a++;
            }
        }
        int max_concentration_num = 0;
        for (int i = 0; i < N; i++) {
            if (temp_num[i] > max_concentration_num) {
                max_concentration_num = i;
            }
        }
        int[] max_concentration_list = temp_index[max_concentration_num];
        double[] pd = new double[N];
        for (int i = 0; i < N; i++) {
            int j = 0;
            for (j = 0; j < max_concentration_list.length; j++) {
                if (i == max_concentration_list[j]) {
                    pd[i] = (1 - max_concentration_num / N) / N;
                    break;
                }
            }
            if (j >= max_concentration_list.length) {
                pd[i] = (1 + max_concentration_num * max_concentration_num / (N * N - N * max_concentration_num)) / N;
            }
        }
        double[] p = new double[N];
        for (int i = 0; i < N; i++) {
            p[i] = pf[i] * 0.4 + pd[i] * 0.6;
        }
        fitness = p;
        return fitness;
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
        result[fitness.length] = 1.0;
        return result;
    }

    public static void main(String[] args) {
        int size = 4;
        ImmunitySelection scf = new ImmunitySelection();
        double[] data = new double[size];
        for (int i = 1; i <= size; i++) {
            data[i - 1] = i;
        }
        double[] sele = scf.normalizeData2ndStep(data);
        for (int i = 0; i < sele.length; i++) {
            System.out.println(sele[i]);
        }
    }
}
