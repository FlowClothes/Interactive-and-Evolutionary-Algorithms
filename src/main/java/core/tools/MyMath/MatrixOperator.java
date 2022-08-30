package core.tools.MyMath;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class MatrixOperator {//计算给定两矩阵的乘积

    public double CC[][] = new double[4][4];
    int m, n, p;
    //a[i][j]*b[j][k]

    public MatrixOperator() {}
    public void initialize(int i, int j, int k){
    for (m = 0; m < i; m++) {
            for (n = 0; n < k; n++) {
                CC[m][n] = 0.f;
            }
        }
    }
    public void multiply(int i, int j, int k, double a[][], double b[][]) {
        //相乘运算
        for (m = 0; m < i; m++) {
            for (n = 0; n < k; n++) {
                for (p = 0; p < j; p++) {
                    CC[m][n] = CC[m][n] + a[m][p] * b[p][n];
                }
            }
        }
    }
}

