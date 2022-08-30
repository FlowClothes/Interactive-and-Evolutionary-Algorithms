package core.algorithm;

import java.util.List;
import core.problem.Individual;
import core.problem.Population;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ParticleSwarmOptimization extends AbstractAlgorithm {
//粒子群算法只解决浮点类型的问题，不解决二进制问题
    //下面来自：https://blog.csdn.net/tuwenqi2013/article/details/79053651
//    粒子群算法中所涉及到的参数有：
//       种群数量：粒子群算法的最大特点就是速度快，因此初始种群取50-1000都是可以的，虽然初始种群越大收敛性会更好，不过太大了也会影响速度；
//       迭代次数：一般取100~4000，太少解不稳定，太多浪费时间。对于复杂问题，进化代数可以相应地提高；
//       惯性权重：该参数反映了个体历史成绩对现在的影响，一般取0.5~1；
//       学习因子：一般取0~4，此处要根据自变量的取值范围来定，并且学习因子分为个体和群体两种；
//       空间维数：粒子搜索的空间维数即为自变量的个数。
//       位置限制：限制粒子搜索的空间，即自变量的取值范围，对于无约束问题此处可以省略。
//       速度限制：如果粒子飞行速度过快，很可能直接飞过最优解位置，但是如果飞行速度过慢，会使得收敛速度变慢，因此设置合理的速度限制就很有必要了。

    @Override
    public void init() {
        //pop这个时候种群还没生成，因此，也无法从其获得populationsize
        //某维设定的初始最大速度等于该区间变量的精度的5倍，即一次将跳过5个变量，可以考虑让这个值自适应地变化
        //初始化粒子在每维的最大速率

    }

    @Override
    public List<Individual> operatePopulation(Population pop) {
        //1.更新全局最优和历史最优
        int bestIndex = 0;
        //2.更新每个particle
       return null;
    }

    @Override
    public List<Individual> operatePopulation(Population pop, int inputSelectionType, int inputCrossoverType, int inputCrossoverPointnumValue, int inputMutationType, int inputMutationPointNumValue, double inputMutationProbability) {
        return null;
    }

    @Override
    public String getName() {
        return "Particle Swarm Optimization";
    }

}
