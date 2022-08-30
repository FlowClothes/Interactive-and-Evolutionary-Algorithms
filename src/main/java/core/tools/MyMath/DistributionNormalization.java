package core.tools.MyMath;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class DistributionNormalization {

    /**
     *
     * @param hgs
     */
    public static void normalize(double[] data) {
        double sum = 0, avgSd[] = new double[2];
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
        }
        avgSd[0] = sum / data.length;
        sum = 0;
        for (int i = 0; i < data.length; i++) {
            sum += Math.pow(data[i] - avgSd[0], 2);
        }
        avgSd[1] = Math.sqrt(sum / (data.length - 1));
        if (avgSd[1] < Math.pow(2, -15)) {//data中的值完全相等，所以，方差为0
            for (int i = 0; i < data.length; i++) {
                data[i] =data[0]/data.length;
            }
        } else {
            for (int i = 0; i < data.length; i++) {
                data[i] = (data[i] - avgSd[0]) / avgSd[1];
            }
        }
    }
}
