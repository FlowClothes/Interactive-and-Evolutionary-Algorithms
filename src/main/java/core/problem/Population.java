package core.problem;

import java.util.TreeSet;
import core.problem.DecisionVariables.AbstractDecisionVariable;
import core.problem.DecisionVariables.DecisionVariable;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 * @param <T>
 */
public class Population<T extends AbstractDecisionVariable> {

    protected TreeSet<Individual> individuals;//依据目标变量和决策变量在两个空间排序
    // protected TreeSet<DecisionVariable> LastPopulationDecisonVariables = new TreeSet<>();//上一代的种群，可以在进入下一代前，直接被当代替换
    protected double[] fitness4Selection;//用于保存为选择算子服务的临时适应值
    double volume;

    public TreeSet<Individual> getIndividuals() {
        return individuals;
    }

//    public TreeSet<DecisionVariable> getLastPopulationDecisionVariables() {
//        return this.LastPopulationDecisonVariables;
//    }
    public double[] getFitness4Selection() {
        return fitness4Selection;
    }

    public void setFitness4Selection(double[] temFitness) {
        fitness4Selection = temFitness;
    }

    public void setIndividuals(TreeSet listIndividuals) {
        this.individuals = listIndividuals;
    }

    public TreeSet<Individual> init(int indivdiualNumber) {
        //生成individuals
        //这里之所以不赋值给本population，是因为进化过程中，需要生成1或2个新的补充个体
        TreeSet<Individual> listIndividuals = new TreeSet<>();
        while (listIndividuals.size() < indivdiualNumber) {//因为使用TreeSet数据结构，所以不会有重复个体
            Individual ind = new Individual(FactoryProblems.currentProblem.getDecisionVariable().initVariable());
            listIndividuals.add(ind);
        }
        return listIndividuals;
    }

    protected TreeSet<DecisionVariable> decisionVariables = new TreeSet<>();

    public double getVolume() {
        decisionVariables.clear();
        individuals.forEach((ind) -> {
            decisionVariables.add(ind.getDecisionVariable());
        });
        volume = 1;
        switch (FactoryProblems.currentProblem.getDecisionVariable().getGenecodeType()) {
            case DOUBLECODE:
                for (int i = 0; i < decisionVariables.last().getGeneCodes().length; i++) {
                    volume *= Math.pow(decisionVariables.last().getGeneCodes()[i] - decisionVariables.first().getGeneCodes()[i],
                            1.0 / decisionVariables.last().getGeneCodes().length);
                }
                break;
            case STRINGCODE:
                for (int i = 0; i < decisionVariables.last().getGenecodesString().length(); i++) {
                    volume *= Math.pow(decisionVariables.last().getGenecodesString().charAt(i)
                            - decisionVariables.first().getGenecodesString().charAt(i), 1 / decisionVariables.last().getGenecodesString().length());
                }
                break;
            case AFFINEFACECODE:
                System.out.println("还不支持");
                break;
            case BINARYCODE:
                System.out.println("还不支持");
                break;
            case NORMALFACECODE:
                System.out.println("还不支持");
                break;
        }
        return volume;
    }

    public void outputPopulation() {
        individuals.forEach(indi -> {
        });
    }
}
