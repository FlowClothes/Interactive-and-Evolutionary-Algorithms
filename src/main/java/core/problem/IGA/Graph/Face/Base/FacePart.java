package core.problem.IGA.Graph.Face.Base;

import core.problem.IGA.AffineMatrixPhenoType;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class FacePart {

    private int[] vertNum;//存放人脸各部分的点的编号
    private int[][] xEqualPoints;//存放人脸上x坐标相同的点的编号
    private int[][] yEqualPoints;//存放人脸上y坐标相同的点的编号
    private int[][] zEqualNuPoints;//存放人脸上z坐标相同的点的编号
    private boolean xEqual = false;
    private boolean yEqual = false;
    private boolean zEqual = false;
    private final AffineMatrixPhenoType affineMatrix = new AffineMatrixPhenoType();

    public FacePart() {
    }

    public FacePart(int vertCount) {
        vertNum = new int[vertCount];
    }

    public int[] getVertNum() {
        return vertNum;
    }

    public void setVertNum(int[] vertNum) {
        if (null != vertNum && vertNum.length > 0) {
            this.vertNum = new int[vertNum.length];
            System.arraycopy(vertNum, 0, this.vertNum, 0, vertNum.length);
        }
    }

    public int[][] getXEqualPoints() {
        //在调用该方法前，应先检查x的值；
        return xEqualPoints;
    }

    public void setXEqualPoints(int[][] equalNumx) {
        xEqual = true;
        xEqualPoints=new int[equalNumx.length][equalNumx[0].length];
        for (int i = 0; i < equalNumx.length; i++) {
            for (int j = 0; j < equalNumx[i].length; j++) {
                xEqualPoints[i][j]=equalNumx[i][j];
            }
        }
    }

    public int[][] getYEqualPoints() {
        //在调用该方法前，应先检查y的值；
        return yEqualPoints;
    }

    public void setYEqualPoints(int[][] equalNumy) {
        yEqual = true;
        yEqualPoints=new int[equalNumy.length][equalNumy[0].length];
        for (int i=0;i<equalNumy.length;i++) {
            yEqualPoints[i]=new int[equalNumy[i].length];
            for (int j = 0; j < equalNumy[i].length; j++) {
                yEqualPoints[i][j]=equalNumy[i][j];
            }
        }

    }

    public int[][] getZEqualPoints() {
        //在调用该方法前，应先检查z的值；
        return zEqualNuPoints;
    }

    public void setZEqualPoints(int[][] equalNumz) {
        zEqual = true;
        zEqualNuPoints = new int[equalNumz.length][equalNumz[0].length];
        for (int i = 0; i < equalNumz.length; i++) {
            for (int j = 0; j < equalNumz[i].length; j++) {
                zEqualNuPoints[i][j]=equalNumz[i][j];
            }
        }
    }

    public boolean hasEqualX() {
        return xEqual;
    }

    public boolean hasEqualY() {
        return yEqual;
    }

    public boolean hasEqualZ() {
        return zEqual;
    }

    public AffineMatrixPhenoType getAffineMatrix() {
        return affineMatrix;
    }

}
