package core.problem.IGA.Graph.Face.Base;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class miniGraphPointforMutationScope {
    //该类中只存放单一的坐标信息；
    private int pointNum;
    private int coord;//坐标代码，'x','y','z'等
    private double[] mutationScope=new double[2];

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public int getCoord() {
        return coord;
    }

    public void setCoord(int coord) {
        this.coord = coord;
    }

    public double[] getMutationScope() {
        return mutationScope;
    }

    public void setMutationScope(double[] mutationScope) {
        System.arraycopy(mutationScope, 0, this.mutationScope, 0, mutationScope.length);
    }
    }
