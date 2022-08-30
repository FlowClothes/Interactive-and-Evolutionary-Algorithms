package core.tools.MyMath;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class AverageAndStd {

    public static void main(String[] args) {
        List data = new LinkedList<>();
        data.add(0.0);data.add(1.0);data.add(0.0);data.add(1.0);
        AverageAndStd aa = new AverageAndStd();
        double [] result=aa.averageAndStd(data);
        for (int i = 0; i < 2; i++) {
            System.out.println(result[i]);
        }
    }

    public double[] averageAndStd(List<Double> data) {
        //求均值与标准差
        double[] result = new double[2];
        for (int i = 0; i < data.size(); i++) {
            result[0] += data.get(i);
        }
        result[0] = result[0] / data.size();//得到了均值
        for (int i = 0; i < data.size(); i++) {
            result[1] += Math.pow(data.get(i) - result[0], 2);
        }
        result[1] = result[1] / (data.size()-1);
        result[1] = Math.sqrt(result[1]);
        
        //这里求得的标准差是其无偏估计
        return result;
    }
}
