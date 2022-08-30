package core.problem.IGA.Graph.Face.Base;

import core.problem.IGA.Graph.Face.FaceTextureLadyPink;
import javax.media.j3d.*;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 * @author 姜艳 Jia Yan
 */
public class VertexCreator extends Shape3D {

    private double[] vert;
    private int vertCount = 0;
    private int textCount = 0;
    private double VertTextStrip[][];
    private double vertCoordinateBack[];
    private double TextArray[];

    public VertexCreator( double[] vertCoord) {
        FaceTextureLadyPink ftlp = new FaceTextureLadyPink();
        this.TextArray =FaceTextureLadyPink.TextArray ;//贴图坐标
        this.vertCoordinateBack =FaceVertexData.vertCoordinateBack;
        int[][] StripVerCoordinate = FaceVertexData.faceVertRelation;
        int StripCount = StripVerCoordinate.length;//三角片的个数
        //
        int intCandide = vertCoord.length;
        int intBack = vertCoordinateBack.length;
        double AllVertCoordinate[] = new double[intCandide + intBack];
        for (int i = 0; i < intCandide; i++) {
            AllVertCoordinate[i] = vertCoord[i];
        }
        for (int i = 0; i < intBack; i++) {
            AllVertCoordinate[i + intCandide] = vertCoordinateBack[i];
        }

        vert = new double[StripCount * 18];//三角片三个顶点，乘以3；每个顶点有x,y,z三个坐标，乘以3；对称性，乘以2；

        VertTextStrip = new double[StripCount * 6][2];
        for (int i = 0; i < StripCount; i++) {
            for (int j = 0; j < 3; j++) {
                int tem = StripVerCoordinate[i][j];
                for (int k = 0; k < 3; k++) {
                    vert[vertCount++] = AllVertCoordinate[tem * 3 + k];//三角片上的每一个点按顺序录入
                }
                VertTextStrip[textCount][0] = TextArray[2 * tem];
                VertTextStrip[textCount][1] = TextArray[2 * tem + 1];
                textCount++;
            }
        }
        for (int i = 0; i < StripCount; i++) {
            for (int j = 0; j < 3; j++) {
                int tem = StripVerCoordinate[i][j];
                for (int k = 0; k < 3; k++) {
                    if (k == 0) {
                        vert[vertCount++] = -AllVertCoordinate[tem * 3 + k];
                    } else {
                        vert[vertCount++] = AllVertCoordinate[tem * 3 + k];
                    }
                }

                VertTextStrip[textCount][0] = 1-TextArray[2 * tem] ;
                VertTextStrip[textCount][1] = TextArray[2 * tem + 1];
                textCount++;

            }
        }
    }

    public double[] getVert() {
        return vert;
    }

    public int getVertCount() {
        return vertCount;
    }

    public double[] getTextArray() {
        return TextArray;
    }

    public void setTextArray(double[] TextArray) {
        System.arraycopy(TextArray, 0, this.TextArray, 0, TextArray.length);        
    }

    public double[][] getVertTextStrip() {
        return VertTextStrip;
    }

    public void setVertTextStrip(double[][] VertTextStrip) {
        for(int i=0;i<VertTextStrip.length;i++){
            System.arraycopy(VertTextStrip[i], 0, this.VertTextStrip[i], 0, VertTextStrip[i].length);
        }

    }
}
  

    