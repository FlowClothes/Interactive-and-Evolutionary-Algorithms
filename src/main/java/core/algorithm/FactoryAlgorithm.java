package core.algorithm;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class FactoryAlgorithm {
    public static Algorithm currentAlgorithm;
    public static int algorithmNumber;//算法对应的编号 
    public static double crossMutationCompeteProbability ;//60%交叉,40%变异
      //执行exploration的比率，如在进化初期用0.9或在进化后期用0.2，又如当算法在0.1下长期运行没有进展时，切换到较高值

    public static Algorithm getAlgorithm(int type) {
        switch (type) {
            case 0:
                return new GeneticAlgorithm();
            case 1:
                return new ParticleSwarmOptimization();
            default:
                return null;
        }
    }
}
