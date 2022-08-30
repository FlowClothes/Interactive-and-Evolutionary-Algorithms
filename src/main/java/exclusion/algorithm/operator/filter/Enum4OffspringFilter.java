package exclusion.algorithm.operator.filter;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public enum Enum4OffspringFilter {
    //SimpleGA,非最优个体可以再现，同一种群内个体可以重复
    //需要说明的是，这种方法在引入TreeSet数据结构后，不再存在。而TreeSet数据结构的引入，使得最简单的GA变成了NoRepeatOnSmaePopulationKnowledge
    //SimpleNoRepeatOnSmaePopulation,//同一种群内个体不重复，新个体从最优解变异得来---Exploitation
    //SimpleNoReappearOnThisAndLastPopulation,//在种群层次上非最优个体不再出现，新个体从最优解变异得来
   // SimpleNoReappearOnSearchSpace,//在搜索空间层次上历史个体不再出现，新个体从最优解变异得来
    //下面是要在上面的这几个简单操作上加入不同的新个体生成方式，主要包括以下3种：
    //利用梯度信息--Knolwedge guess--try and error
    //随机初始化--Exploration
    //远离种群聚类中心--Exploration
    //再同基于最优解的变异结合起来，进行随机选择
    //HashMap
    NoRepeatOnSmaePopulationKnowledge,//加入TreeSet数据结构表示Population中的Indivdiaus后，这是默认的方法
    NoReappearOnThisAndLastPopulationKnowledge,//在SimpleNoReappearOnThisAndLastPopulation加上梯度
    NoReappearOnSearchSpaceKnowledge,//NoReappearOnSearchSpace加上 
    //NoReappearOnSearchSpaceHashMapAndPopulation1//用HashMap存储历史数据，以提高查找速度，用当前种群生成个体，而不利用历史数据
    //后面可以考虑的，包括利用当前种群+交叉+变异生成新个体
}
