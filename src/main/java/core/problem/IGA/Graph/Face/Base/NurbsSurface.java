package core.problem.IGA.Graph.Face.Base;

import core.problem.IGA.Graph.Face.FaceTextureLadyPink;
import core.tools.MyMath.MatrixOperator;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 * @author 严玉若 YAN Yu-Ruo,YAN Yu Ruo,YAN YuRuo
 */
public class NurbsSurface {

    private double[] nvert;
    private double[] tvert;
    private double P[] = new double[16 * 3];//用以计算立体网格的数组
    private double T[] = new double[16 * 2];//用以计算贴图的数组
    int pp = 0;
    int tt = 0;

    private void setVertValue(int i[]) {//给P赋值，指定16个点，i[0]-i[15]
        while (pp < P.length) {
            int n = pp / 3;
            P[pp++] = FaceVertexData.getVert(i[n])[0];
            P[pp++] = FaceVertexData.getVert(i[n])[1];
            P[pp++] = FaceVertexData.getVert(i[n])[2];
        }
        while (tt < T.length) {
            int n1 = tt / 2;
            T[tt++] = FaceTextureLadyPink.getVert(i[n1])[0];
            T[tt++] = FaceTextureLadyPink.getVert(i[n1])[1];
        }
    }

    public NurbsSurface(int m[]) {
        this.setVertValue(m);
        //C=UMPM'V'
        int i, j, k;
        int n;//定义对参数u、v在[0,1]区间的等分点数
        double division;//定义参数u、v在[0,1]区间的等分线段长度

        n = 20;
        division = 1.f / n;

        //分别定义存放控制顶点齐次坐标4个分量的数组
        double[][] PX = new double[4][4];
        double[][] PY = new double[4][4];
        double[][] PZ = new double[4][4];
        double[][] P4 = new double[4][4];//为什么要四维的坐标？

        double[][] TX = new double[4][4];
        double[][] TY = new double[4][4];
        //定义系数矩阵及其转置
        double[][] M1 = {
            {1.f, 0.f, 0.f, 0.f},
            {-3.f, 3.f, 0.f, 0.f},
            {3.f, -6.f, 3.f, 0.f},
            {-1.f, 3.f, -3.f, 1.f},};
        double[][] M2 = {
            {1.f, -3.f, 3.f, -1.f},
            {0.f, 3.f, -6.f, 3.f},
            {0.f, 0.f, 3.f, -3.f},
            {0.f, 0.f, 0.f, 1.f},};
        //定义曲面u,v参数区间分割点的坐标数组
        double[][][] UV = new double[n + 1][n + 1][2];
        //定义U,V矩阵数组
        double[][] UU = new double[1][4];
        double[][] VV = new double[4][1];
        //定义存放曲面上点的坐标的数组
        double[][][] SurfaceXYZ = new double[n + 1][n + 1][4];
        double[][][] TextureXY = new double[n + 1][n + 1][2];

        for (i = 0; i < n + 1; i++) {
            for (j = 0; j < n + 1; j++) {
                UV[i][j][0] = i * division;
                UV[i][j][1] = j * division;
            }
        }
        int l = 0;
        //16个控制结点的赋值
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                PX[i][j] = P[l++];
                PY[i][j] = P[l++];
                PZ[i][j] = P[l++];
                P4[i][j] = 1.0f;
                if (l >= P.length) {
                    break;
                }
            }
        }
        //贴图
        int lt=0;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                TX[i][j] = T[lt++];
                TY[i][j] = T[lt++];
                if (lt >= T.length) {
                    break;
                }
            }
        }

        //参数矩阵U、V的定义
        for (i = 0; i < n + 1; i++) {
            for (j = 0; j < n + 1; j++) {
                UU[0][0] = 1.f;
                UU[0][1] = UV[i][j][0];
                UU[0][2] = UV[i][j][0] * UV[i][j][0];
                UU[0][3] = UV[i][j][0] * UV[i][j][0] * UV[i][j][0];
                //UU=(1,u,u*u,u*u*u)VV同理
                VV[0][0] = 1.f;
                VV[1][0] = UV[i][j][1];
                VV[2][0] = UV[i][j][1] * UV[i][j][1];
                VV[3][0] = UV[i][j][1] * UV[i][j][1] * UV[i][j][1];//此处问题，UV可不可以用同一个参数

                //计算曲面上点的坐标值

                MatrixOperator UM1 = new MatrixOperator();
                MatrixOperator M2V = new MatrixOperator();

                UM1.initialize(1, 4, 4);
                UM1.multiply(1, 4, 4, UU, M1);
                M2V.initialize(4, 4, 1);
                M2V.multiply(4, 4, 1, M2, VV);
                //x坐标
                MatrixOperator UM1Px = new MatrixOperator();
                MatrixOperator valuex = new MatrixOperator();
                UM1Px.initialize(1, 4, 4);
                UM1Px.multiply(1, 4, 4, UM1.CC, PX);
                valuex.initialize(1, 4, 1);
                valuex.multiply(1, 4, 1, UM1Px.CC, M2V.CC);

                SurfaceXYZ[i][j][0] = valuex.CC[0][0];
                //y坐标
                MatrixOperator UM1Py = new MatrixOperator();
                MatrixOperator valuey = new MatrixOperator();
                UM1Py.initialize(1, 4, 4);
                UM1Py.multiply(1, 4, 4, UM1.CC, PY);
                valuey.initialize(1, 4, 1);
                valuey.multiply(1, 4, 1, UM1Py.CC, M2V.CC);

                SurfaceXYZ[i][j][1] = valuey.CC[0][0];
                //z坐标
                MatrixOperator UM1Pz = new MatrixOperator();
                MatrixOperator valuez = new MatrixOperator();
                UM1Pz.initialize(1, 4, 4);
                UM1Pz.multiply(1, 4, 4, UM1.CC, PZ);
                valuez.initialize(1, 4, 1);
                valuez.multiply(1, 4, 1, UM1Pz.CC, M2V.CC);

                SurfaceXYZ[i][j][2] = valuez.CC[0][0];
                //第4维坐标
                MatrixOperator UM1P4 = new MatrixOperator();
                MatrixOperator value4 = new MatrixOperator();
                UM1P4.initialize(1, 4, 4);
                UM1P4.multiply(1, 4, 4, UM1.CC, P4);
                value4.initialize(1, 4, 1);
                value4.multiply(1, 4, 1, UM1P4.CC, M2V.CC);

                SurfaceXYZ[i][j][3] = value4.CC[0][0];
                //将齐次坐标转换为三维坐标
                SurfaceXYZ[i][j][0] = SurfaceXYZ[i][j][0] / SurfaceXYZ[i][j][3];
                SurfaceXYZ[i][j][1] = SurfaceXYZ[i][j][1] / SurfaceXYZ[i][j][3];
                SurfaceXYZ[i][j][2] = SurfaceXYZ[i][j][2] / SurfaceXYZ[i][j][3];

                //贴图坐标
                MatrixOperator UM1Tx = new MatrixOperator();
                MatrixOperator valuetx = new MatrixOperator();
                UM1Tx.initialize(1, 4, 4);
                UM1Tx.multiply(1, 4, 4, UM1.CC, TX);
                valuetx.initialize(1, 4, 1);
                valuetx.multiply(1, 4, 1, UM1Tx.CC, M2V.CC);

                TextureXY[i][j][0] = valuetx.CC[0][0];

                MatrixOperator UM1Ty = new MatrixOperator();
                MatrixOperator valuety = new MatrixOperator();
                UM1Ty.initialize(1, 4, 4);
                UM1Ty.multiply(1, 4, 4, UM1.CC, TY);
                valuety.initialize(1, 4, 1);
                valuety.multiply(1, 4, 1, UM1Ty.CC, M2V.CC);

                TextureXY[i][j][1] = valuety.CC[0][0];
            }
        }
        nvert = new double[(n + 1) * (n + 1) * 12];//四边形四个顶点，每个顶点三个坐标
        tvert = new double[(n + 1) * (n + 1) * 8];//四个顶点，每个点两个坐标
        int s = 0;
        int s1 = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                nvert[s++] = SurfaceXYZ[i][j][0];
                nvert[s++] = SurfaceXYZ[i][j][1];
                nvert[s++] = SurfaceXYZ[i][j][2];
                nvert[s++] = SurfaceXYZ[i][j + 1][0];
                nvert[s++] = SurfaceXYZ[i][j + 1][1];
                nvert[s++] = SurfaceXYZ[i][j + 1][2];
                nvert[s++] = SurfaceXYZ[i + 1][j + 1][0];
                nvert[s++] = SurfaceXYZ[i + 1][j + 1][1];
                nvert[s++] = SurfaceXYZ[i + 1][j + 1][2];
                nvert[s++] = SurfaceXYZ[i + 1][j][0];
                nvert[s++] = SurfaceXYZ[i + 1][j][1];
                nvert[s++] = SurfaceXYZ[i + 1][j][2];

                tvert[s1++] = TextureXY[i][j][0];
                tvert[s1++] = TextureXY[i][j][1];
                tvert[s1++] = TextureXY[i][j + 1][0];
                tvert[s1++] = TextureXY[i][j + 1][1];
                tvert[s1++] = TextureXY[i + 1][j + 1][0];
                tvert[s1++] = TextureXY[i + 1][j + 1][1];
                tvert[s1++] = TextureXY[i + 1][j][0];
                tvert[s1++] = TextureXY[i + 1][j][1];
            }
        }
    }

    public double[] getvert() {//传回三维点的坐标
        return nvert;
    }
    public double[] gettvert(){//传回贴图的坐标
        return tvert;
    }
}
