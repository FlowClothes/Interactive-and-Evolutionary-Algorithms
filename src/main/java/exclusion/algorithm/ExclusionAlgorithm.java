package exclusion.algorithm;

import core.algorithm.GeneticAlgorithm;
import core.algorithm.accessory.HistoryIndividuals;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Population;
import exclusion.algorithm.operator.filter.Enum4OffspringFilter;
import exclusion.algorithm.operator.filter.offspringFilter.OffspringFilter;
import exclusion.algorithm.operator.filter.parentFilter.ParentFilter;
import java.util.List;

/**
 *
 * @author hgs
 */
public class ExclusionAlgorithm extends GeneticAlgorithm {

    Enum4OffspringFilter knowledge;
    double rationOfExploration;
    private OffspringFilter offspringFilter;
    private ParentFilter parentFilter;

    public Enum4OffspringFilter getKnowledge() {
        return this.knowledge;
    }

    public void setKnowledge(Enum4OffspringFilter knowledge) {
        this.knowledge = knowledge;
    }

   //这个是想重载父类中的方法的，但是父类中指定了是final。
    //如何既保留着final，又能够重载？答案是重载operats中的方法
    public  void operats1(Population population) {//final不允许子类对该方法进行修改，这里组装了必要的步骤，属于模板模式
        parentFilter.filter(population.getIndividuals());
        // population.outputPopulation();
        List<Individual> operatedIndividuals = this.operatePopulation(population);//进化过程,不同的算法有不同的方法，会不一样，因此，这个方法要求各个算法自己实现
        offspringFilter.filter(operatedIndividuals);
        ecapsule(operatedIndividuals, population);
        FactoryProblems.dealwithJulia(population);
    }
        @Override
    public void init() {
        //重新初始化历史个体，因为开始了一个新的实验
        HistoryIndividuals.getInstance().init();
        this.offspringFilter = new OffspringFilter();
        parentFilter=new ParentFilter();
    }
    
    public OffspringFilter getPopulationFilter() {
        return offspringFilter;
    }
}
