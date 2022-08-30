package core.tools.MyMath;

import java.util.Random;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class RandomGenerator {

    static Random rand = new Random();
    
    public static double nextDouble(){
        return rand.nextDouble();
    }

    public static double nextGaussian() {
        return  rand.nextGaussian();
    }

    public static double getRandom(double upValue, double lowValue) {
        if (lowValue > upValue) {
            double tem = lowValue;
            lowValue = upValue;
            upValue = tem;
        }
        return lowValue + rand.nextDouble()* (upValue - lowValue);
        //return 0d;
    }

    public static int getRandom(int upValue, int lowValue) {
        if (lowValue >= upValue) {
            if (lowValue > upValue) {
                int tem = lowValue;
                lowValue = upValue;
                upValue = tem;
            } else if (lowValue == upValue) {//等于的情况
                lowValue = 0;
                upValue = 2;//这样设置,使得产生的随机数是0或1
            }
        }
        return lowValue + rand.nextInt(upValue - lowValue);
    }
    public static int getRandom(int seed){
        return rand.nextInt(seed);
    }
}
