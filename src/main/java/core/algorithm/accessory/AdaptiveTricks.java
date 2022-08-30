package core.algorithm.accessory;

import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class AdaptiveTricks {

    //本类中的方法的特点是：方法触发条件，方法影响参数
    //调节排斥距离
    //排斥距离的最大值与最小值
    public static double upParentExclusionRadius, lowParentExclusionRadius;
    private double currentParentExclusionRadius = -1;
    //向up靠近？
    private boolean forward = true;
    private final int thresholdNoimproved = 5;
    private final int stepChange = 5;
    private static AdaptiveTricks instance;
    
    private AdaptiveTricks(){
        
    }
    public static AdaptiveTricks getInstance(){
        if(null==instance){
            instance=new AdaptiveTricks();
        }
        return instance;
    }

    public double getCurrentParentExclusionRadius() {
        if (currentParentExclusionRadius < 0) {//还没初始化，就先初始化
            currentParentExclusionRadius = RandomGenerator.getRandom(upParentExclusionRadius, lowParentExclusionRadius);
        } else {//已经初始化完成，根据运行状态，调整该参数
           currentParentExclusionRadius= getParentExclusionRadius();
        }
        return currentParentExclusionRadius;
    }

    private double getParentExclusionRadius() {
        //unImprovedIteration无提高或无改进
        if (currentParentExclusionRadius > upParentExclusionRadius) {
            forward = false;
            currentParentExclusionRadius = upParentExclusionRadius;
            return currentParentExclusionRadius;
        }
        if (currentParentExclusionRadius < lowParentExclusionRadius) {
            forward = true;
            currentParentExclusionRadius = lowParentExclusionRadius;
            return currentParentExclusionRadius;
        }
        if (forward) {//往增大的方向走
            if (HistoryIndividuals.getInstance().getUnImproved() > thresholdNoimproved) {//thresholdNoimproved代无改进
                //当前分割hisotry4Parent的最小间隔小于10倍于种群规模对搜索空间的分割，那么提高分割间距
                currentParentExclusionRadius = currentParentExclusionRadius * stepChange;
            }
        } else {//往减小的方向走
            if (HistoryIndividuals.getInstance().getUnImproved() > thresholdNoimproved) {//thresholdNoimproved代无改进
                //当前分割hisotry4Parent的最小间隔小于10倍于种群规模对搜索空间的分割，那么提高分割间距
                currentParentExclusionRadius = currentParentExclusionRadius / stepChange;
            }
        }
        return currentParentExclusionRadius;
    }

}
