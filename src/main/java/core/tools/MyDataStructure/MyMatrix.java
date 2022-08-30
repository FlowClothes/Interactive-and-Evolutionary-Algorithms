package core.tools.MyDataStructure;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class MyMatrix {
    public static double[][] multiply(double[][] a, double[][] b) {
        double[][] tem = new double[a.length][b.length];
        MyArray.setZero(tem);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                for (int k = 0; k < a.length; k++) {
                    tem[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return tem;
    }
    public static double[] multiply(double[][] a, double[] b) {
        double[] tem = new double[b.length];
        MyArray.setZero(tem);
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                    tem[i] += a[i][j] * b[j];
            }
        }
        return tem;
    }


}
