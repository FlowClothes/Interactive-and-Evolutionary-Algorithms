package core.algorithm.accessory;

import exclusion.algorithm.operator.filter.parentFilter.ParentFilter;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import core.problem.DecisionVariables.DecisionVariable;
import core.problem.DecisionVariables.IndividualWithVarialbeWithDistance;
import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class HistoryIndividuals {
//只保存少量的最近的进化代的个体
    //更多的历史个体，为了避免内存溢出，放在了数据库中

    private TreeSet<IndividualWithAge> history4Parent; //历史上的最优以及可能在多目标中的不可比的占优，因为要被用来生成新的个体，所以采用排序的数据结构,扮演ParentRepository角色
    private TreeSet<DecisionVariable> history4Offspring; //存储个体的同时，还存储了他们的决策变量，目的是检索时加快速度，相当于用空间换时间
    private HashMap<DecisionVariable, Double> decisionVariableWithRadius;//与historyDecisonVariables对应的排斥半径
    private Individual worstSofar, bestSorfar;//历史上最差的个体,作为参照标准0海平面的
    private int age = 0,//记录本地的各个体的代数，目的是在更新时，可以区别应该删除age较小的那些个体，即比较早的个体
            eldestAge = 0;//记录repository中最老的个体的年龄，以便删除时使用该索引
    public static int sizeOfParentRepository, sizeOfOffspringRepository;
    public boolean bestChanged = true, worstChanged = true;//记录最优与最差的适应值是否发生改变，如果没有改变，那么排斥半径不用重新计算
    private int lifeAge = 1;//保存的种群的代数
    private int unImproved = 0;//记录算法最优解没有改进的代数，

    private HistoryIndividuals() {
        //history4parentfilter = new HashSet<>();
        history4Parent = new TreeSet<>(); //历史上的最优以及可能在多目标中的不可比的占优，因为要被用来生成新的个体，所以采用排序的数据结构
        history4Offspring = new TreeSet<>(); //历史上的最差
        //historyIndividualsHashSet = new HashSet<>();//历史个体
        worstSofar = null;//历史上最差的个体,作为参照标准0海平面的
        bestSorfar = null;
        decisionVariableWithRadius = new HashMap<>();
    }
    private static HistoryIndividuals instance;

    public static HistoryIndividuals getInstance() {
        if (null == instance) {
            instance = new HistoryIndividuals();
        }
        return instance;
    }

    public void init() {
        history4Parent = new TreeSet<>(); //历史上的最优以及可能在多目标中的不可比的占优，因为要被用来生成新的个体，所以采用排序的数据结构
        history4Offspring = new TreeSet<>(); //历史上的最差
        //historyIndividualsHashSet = new HashSet<>();//历史个体
        worstSofar = null;//历史上最差的个体,作为参照标准0海平面的
        bestSorfar = null;
        decisionVariableWithRadius = new HashMap<>();
        unImproved = 0;
    }

    private void updateWorstSoFar(Individual individual) {
        if (getWorstSofar() == null) {
            worstSofar = individual.clone();
            worstChanged = true;
        } else if (getWorstSofar().compareTo(individual) > 0) {
            worstSofar = individual.clone();
            worstChanged = true;
        } else {
            worstChanged = false;
        }
    }

        private void updateBestSoFar(Individual individual) {
        if (getBestSorfar()== null) {
            bestSorfar = individual.clone();
            bestChanged = true;
            unImproved=0;
        } else if (getBestSorfar().compareTo(individual) < 0) {
            bestSorfar = individual.clone();
            bestChanged = true;
             unImproved=0;
        } else {
            bestChanged = false;
             unImproved++;
        }
    }
    private void updateHistory4Parent(TreeSet<Individual> individuals) {
        updateBestSoFar(individuals.last());
        updateWorstSoFar(individuals.first());
        for (Individual ind:individuals) {
            history4Parent.add(new IndividualWithAge(ind, age));
        }
        //假如超过上限
        if (history4Parent.size() > sizeOfParentRepository) {//大于规定的容量
            history4Parent.removeAll(updateParent4Remove());
        }
        if (history4Parent.size() > sizeOfParentRepository) {//大于规定的容量
            int thresholdAge = age - ParentFilter.lifeAge;
            HashSet<IndividualWithAge> overAgeSet = new HashSet<>();
            history4Parent.forEach((ind) -> {
                if (ind.getAge() < thresholdAge) {
                    overAgeSet.add(ind);
                }
            });
            history4Parent.removeAll(overAgeSet);
        }
    }
    private final List<IndividualWithAge> parentRemove = new LinkedList<>();//专门用来为删除parentRepository服务的，与parentRepository共存亡

    private List<IndividualWithAge> updateParent4Remove() {
        parentRemove.clear();
        IndividualWithAge first = null, second;
        int i = 0;
        double exclusionRadius = AdaptiveTricks.getInstance().getCurrentParentExclusionRadius();
        for (IndividualWithAge ind : history4Parent) {
            if (i == 0) {//第1个不用处理
                first = ind;
            } else {
                second = ind;
                IndividualWithVarialbeWithDistance tem = new IndividualWithVarialbeWithDistance(first, second);
                if (tem.getDistance() <= exclusionRadius) {
                    //因为first是在前边的，所以它被后边的个体占优，所以可以移除
                    parentRemove.add(first);
                }
                first = ind;
            }
            i++;
        }
        return parentRemove;
    }
    List<IndividualWithAge> removed = new LinkedList<>();

    //返回保存在内存中的指定最近代数双亲
    public TreeSet<Individual> getParentAndUpdateHistory(Collection<Individual> population) {
        //分两步走，第一步拿到结果；第二步更新history
        TreeSet<Individual> result = new TreeSet<>();
        removed.clear();
        for (IndividualWithAge ind : history4Parent) {
            if (ind.getAge() <= getLifeAge()) {//只加载满足条件的
                result.add(ind.getIndividual());
            } else {//删除不满足条件的
                removed.add(ind);
            }
        }
        for (IndividualWithAge individualWithAge : removed) {
            history4Parent.remove(individualWithAge);
        }
        //第2步：更新
        //第1步：更新history4Parent与最差个体
        updateHistory4Parent((TreeSet<Individual>) population);
        //因为最优与最差发生了改变，所以需要重新计算排斥半径
        calculateRadius();
        age++;
        //返回结果 
        return result;
    }

    //返回保存在内存中的指定最近代数双亲
//    public List<Individual> getRecentHistory(int lifeAge) {
//        int tem = lifeAge == -1 ? -1 : age - lifeAge;
//        List<Individual> result = new LinkedList<>();
//        history4Parent.forEach((individualWithAge) -> {
//            if (individualWithAge.age >= tem) {
//                result.add(individualWithAge.individual.clone());
//            }
//        });
//        return result;
//    }
    public Individual getWorstSofar() {
        return worstSofar;
    }

    public Individual getBestSorfar() {
        return bestSorfar;
    }

    public TreeSet<IndividualWithAge> getHistory4Parent() {
        return history4Parent;
    }

    private void calculateRadius() {
        if (bestChanged && worstChanged) {
            decisionVariableWithRadius.clear();
            //求排斥半径
            //根据历史最差适应值与当前最优适应值求每个历史个体的半径
            double quotient = HistoryIndividuals.getInstance().getHistory4Parent().last().getIndividual().getDistanceWithFitness(
                    HistoryIndividuals.getInstance().getWorstSofar());
            DecisionVariable bestOne = HistoryIndividuals.getInstance().getHistory4Parent().last().getIndividual().getDecisionVariable();
            for (DecisionVariable ind : history4Offspring) {
                decisionVariableWithRadius.put(ind, ind.getDistanceWithFitness(bestOne) / quotient);
            }
        }
    }

    public HashMap<DecisionVariable, Double> getDecisionVariableWithRadius() {
        return decisionVariableWithRadius;
    }

    public TreeSet<DecisionVariable> getHistoryDecisonVariables() {
        return history4Offspring;
    }

    public int getLifeAge() {
        return lifeAge;
    }

    public void setLifeAge(int lifeAge) {
        this.lifeAge = lifeAge;
    }

    public int getUnImproved() {
        return unImproved;
    }
}
