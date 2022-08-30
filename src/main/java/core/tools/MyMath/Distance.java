package core.tools.MyMath;

/**
 *
 * @author hgs07
 */
public class Distance {

    private static double[] getEachDimensionDistance(double[] one, double[] two) {
        //计算两个向量各维上的差距
        double[] result = new double[one.length];
        for (int i = 0; i < one.length; i++) {
            result[i] = one[i] - two[i];
        }
        return result;
    }

    public double getpndNormal(double[] one, double[] two, int p) {
        //计算1范数，2范数，及多范数
        double result = 0.0;
        if (p == 1) {
            double tem;
            double[] eachDimensonDistance = getEachDimensionDistance(one, two);
            for (int i = 0; i < one.length; i++) {
                tem = Math.abs(eachDimensonDistance[i]);
                result = result < tem ? result : tem;
            }
            return result;
        }
        double[] eachDimensonDistance = getEachDimensionDistance(one, two);
        for (int i = 0; i < one.length; i++) {
            result += Math.pow(Math.abs(eachDimensonDistance[i]), p);
        }
        return Math.pow(result, 1.0 / p);
    }

    public static double mapLineTo(double from, double fromUp, double fromBottom, double toUp, double toBottom) {
        //计算线性的等比例映射，从一个尺度变换到另一个尺度
        return toBottom + (from - fromBottom) / (fromUp - fromBottom) * (toUp - toBottom);
    }

    public static double getDeprecatedRadius(double fitness, double topFitness, double bottomFitness, double precision, int lamda) {
        //计算一维的淘汰半径，投影到1和0之间，注意是从1到0，不是从0到1，所以叫Deprecated
        double result = mapLineTo(fitness, topFitness, bottomFitness, 0, 1);//最大的适应值，被淘汰半径是相对0；最小的适应值，被淘汰半径是相对1
        System.out.println("result:"+result);
        result = result * RandomGenerator.getRandom(lamda / 2, lamda) * precision;
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println(Distance.getDeprecatedRadius(0, 20, 0, 1.0, 5));
    }
}
