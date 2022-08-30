package core.problem.IGA.Graph.Face.Base;

import core.problem.IGA.Graph.Face.FaceTextureLadyPink;
import javax.media.j3d.*;
import javax.vecmath.TexCoord2f;
import core.tools.Graph.Framework.BezierSurface;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 * @author 姜艳 Jia Yan
 */
public class RenderFace extends Shape3D {
    private double[] faceVert;
    private int amount[][] = {
        {0, 11, 12, 15, 1, 13, 14, 15, 1, 14, 14, 15, 1, 14, 14, 15},//额头
        {1, 14, 14, 15, 1, 14, 14, 15, 19, 16, 16, 18, 19, 16, 16, 18},//眉毛以上
        {19, 16, 16, 18, 19, 16, 16, 18, 19, 17, 17, 18, 19, 17, 17, 18},//eyebow
        {1, 1, 19, 19, 1, 1, 19, 19, 2, 2, 27, 27, 2, 2, 27, 27},//眉毛左侧
        {19, 17, 17, 18, 19, 17, 17, 18, 27, 24, 22, 29, 27, 24, 22, 29},//眉毛与眼睛之间
        {27, 24, 22, 29, 27, 26, 25, 29, 27, 31, 30, 29, 27, 34, 32, 29},//eye
        {2, 3, 4, 5, 2, 3, 40, 5, 3, 40, 40, 41, 2, 36, 39, 41,},//nose
        {2, 36, 39, 41, 2, 2, 27, 27, 2, 2, 27, 27, 27, 27, 41, 41},//鼻子右侧与脸的相接
        {27, 34, 32, 29, 41, 37, 29, 28, 41, 42, 42, 45, 5, 41, 42, 45},//眼睛以下与脸的相接
        {5, 41, 42, 45, 5, 41, 42, 45, 6, 41, 46, 45, 6, 43, 44, 46},//鼻子与嘴巴之间
        {6, 43, 44, 46, 7, 48, 47, 46, 8, 49, 49, 46, 9, 49, 49, 46},//mouth
        {9, 49, 49, 46, 9, 49, 49, 46, 10, 50, 50, 45, 10, 50, 50, 45,},//下巴
        {18, 18, 15, 15, 29, 29, 28, 28, 37, 38, 38, 45, 46, 46, 45, 45},//脸的右侧，即眼睛右侧
    };

    private double[] getVertValue(int i[]) {//给P赋值，指定16个点，i[0]-i[15]
        double P[] = new double[16 * 3];//用以计算立体网格的数组
        int pp = 0;
        while (pp < P.length) {
            int n = pp / 3;
            P[pp++] =(double)faceVert[i[n]*3];
            P[pp++] =(double)faceVert[i[n]*3+1];
            P[pp++] =(double)faceVert[i[n]*3+2];
//                P[pp++] = FaceVertexData.getVert(i[n])[0];
//                P[pp++] = FaceVertexData.getVert(i[n])[1];
//                P[pp++] = FaceVertexData.getVert(i[n])[2];
        }
        return P;
    }

    private double[] getTVertValue(int i[]) {//给P赋值，指定16个点，i[0]-i[15]
        double T[] = new double[16 * 2];//用以计算贴图的数组
        int tt = 0;
        while (tt < T.length) {
            int n1 = tt / 2;
            T[tt++] = FaceTextureLadyPink.getVert(i[n1])[0];
            T[tt++] = FaceTextureLadyPink.getVert(i[n1])[1];
        }
        return T;
    }

    public RenderFace(double[] vertextAccord) {
        this.faceVert = vertextAccord;
        for (int i = 0; i < amount.length; i++) {
            //对数组里给出的人面部特征点的序号进行曲面生成，得到一系列新的点
            BezierSurface nf1 = new BezierSurface(this.getVertValue(amount[i]),
                    BezierSurface.DIMENSION3D);
            BezierSurface nf2 = new BezierSurface(this.getTVertValue(amount[i]), BezierSurface.DIMENSION2D);
            vert[i] = nf1.getVertAfterConvert();
            tvert[i] = nf2.getVertAfterConvert();
        }

        nvert = new double[vert[0].length * amount.length];
        for (int i = 0; i < amount.length; i++) {
            for (int t = 0; t < vert[i].length;) {
                nvert[l++] = vert[i][t++];
            }
        }
        l = 0;
        ntvert = new double[tvert[0].length * amount.length];
        for (int i = 0; i < amount.length; i++) {
            for (int t = 0; t < tvert[i].length;) {
                ntvert[l++] = tvert[i][t++];
            }
        }
        //人脸的对称性，算出另一半的坐标
        int l1 = nvert.length;
        n2vert = new double[2 * l1];
        for (int tt1 = 0; tt1 < 2 * l1; tt1++) {
            if (tt1 < l1) {
                n2vert[tt1] = nvert[tt1];
            } else if (tt1 % 3 == 0)//表示x坐标
            {
                n2vert[tt1] = -nvert[tt1 - l1];
            } else {
                n2vert[tt1] = nvert[tt1 - l1];
            }
        }
        int l2 = ntvert.length;
        n2tvert = new double[2 * l2];
        for (int i = 0; i < 2 * l2; i++) {
            if (i < l2) {
                n2tvert[i] = ntvert[i];
            } else if (i % 2 == 0)//x
            {
                n2tvert[i] = 1.0f - ntvert[i - l2];
            } else {
                n2tvert[i] = ntvert[i - l2];
            }
        }

        int verCount = n2vert.length / 3;
        //生成四角片，连接构成光滑曲面
        QuadArray quad = new QuadArray(verCount, GeometryArray.TEXTURE_COORDINATE_2 | GeometryArray.COORDINATES);
        quad.setCoordinates(0, n2vert);
        //贴图
        TexCoord2f q = new TexCoord2f(0f, 0f);
        for (int i = 0; i < verCount; i++) {
            q.set((float)n2tvert[i * 2], (float)n2tvert[i * 2 + 1]);
            quad.setTextureCoordinate(0, i, q);
        }

        PolygonAttributes polygona = new PolygonAttributes();
        polygona.setCullFace(PolygonAttributes.CULL_NONE);
        Appearance app = new Appearance();
        app.setPolygonAttributes(polygona);
        this.setAppearance(app);
        this.setGeometry(quad);
    }
        int l = 0;
    private double nvert[];
    private double ntvert[];
    private double n2vert[];
    private double n2tvert[];
    private double vert[][] = new double[amount.length][];
    private double tvert[][] = new double[amount.length][];

    private boolean temBool;
}
