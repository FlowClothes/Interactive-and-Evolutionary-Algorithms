package core.problem.IGA.Graph.Face.Base;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 * @author 姜艳 Jia Yan
 */
public class GraphPoint {

    private int pointNum;
    private double pointCoordinates[] = new double[3];//结点的坐标，分别存储了x,y和z三个坐标；这里主要存放进化过程中的坐标值，而其测量值则放在FaceVertexData.faceCoordinate中
    private GraphPoint prePoints[][] = new GraphPoint[3][10];//前驱结点，分别存储了x,y和z三个方向的前驱结点；撑死了有10个前驱
    private double[][] addScopeBasedonPrePoints = new double[3][2];////生成该点的坐标要访问其第一个前驱结点，在其第一个前驱结点的坐标基础上，在指定变化范围内，即mutationScope,生成该点坐标;然后检验其坐标是否小于其前驱，如小于，则在差值的基础上，再加一个0.0001的值；
    private GraphPoint nextPoints[][] = new GraphPoint[3][10];//后继结点；撑死了有10个后继；分别存储了x,y和z三个方向的后继结点；检验生成的点的坐标是否大于后继点坐标，如果大于，则在其差值基础上，再减去0.001
//生成该点的坐标要访问其第一个前驱结点，在其第一个前驱结点的坐标基础上，在指定变化范围内生成该点坐标
    public int xpre = 0,  ypre = 0,  zpre = 0,  xnext = 0,  ynext = 0,  znext = 0;

    public GraphPoint() {
    }

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public double[] getPointCoordinates() {
        return pointCoordinates;
    }

    public void setPointCoordinates(double[] pointCoordinates) {
        System.arraycopy(pointCoordinates, 0, this.pointCoordinates, 0, 3);
    }

    public void setPointCoordinates(int coord, double pointCoordinates) {
        switch (coord) {
            case 'x':
                this.pointCoordinates[0] = pointCoordinates;
                ;
                break;
            case 'y':
                this.pointCoordinates[1] = pointCoordinates;
                ;
                break;
            case 'z':
                this.pointCoordinates[2] = pointCoordinates;
                break;
        }
    }

    public GraphPoint[][] getPrePoints() {
        return prePoints;
    }

    public GraphPoint getfirstPrePoint(int coord) {
        GraphPoint tem = new GraphPoint();
        switch (coord) {
            case 'x':
                tem = prePoints[0][0];
                break;
            case 'y':
                tem = prePoints[1][0];
                break;
            case 'z':
                tem = prePoints[2][0];
                break;
        }
        return tem;
    }

    public GraphPoint[][] getNextPoints() {
        return nextPoints;
    }

    public void setNextPoints(int coord, GraphPoint[] nextPoints) {
        switch (coord) {
            case 'x':
                for (int i = 0; i < nextPoints.length; i++) {
                    this.nextPoints[0][this.xnext++] = nextPoints[i];
                }
                break;
            case 'y':
                for (int i = 0; i < nextPoints.length; i++) {
                    this.nextPoints[1][this.ynext++] = nextPoints[i];
                }
                break;
            case 'z':
                for (int i = 0; i < nextPoints.length; i++) {
                    this.nextPoints[2][this.znext++] = nextPoints[i];
                }
                break;
        }
    }

    public double[][] getAddScopeBasedonPrePoints() {
        return addScopeBasedonPrePoints;
    }

    public void setAddScopeBasedonPrePoints(int coord, double[] scope) {
        switch (coord) {
            case 'x':
                for (int i = 0; i < 2; i++) {
                    addScopeBasedonPrePoints[0][i] = scope[i];
                }
                break;
            case 'y':
                for (int i = 0; i < 2; i++) {
                    addScopeBasedonPrePoints[1][i] = scope[i];
                }
                break;
            case 'z':
                for (int i = 0; i < 2; i++) {
                    addScopeBasedonPrePoints[2][i] = scope[i];
                }
                break;
        }
    }

    public void setPrePointsAndAddScopeBasedonPrePoints(int coord, GraphPoint[] prepoints, double[] addScopeBasedonPrePoints) {
        switch (coord) {
            case 'x':
                for (int i = 0; i < prepoints.length; i++) {
                    this.prePoints[0][this.xpre++] = prepoints[i];
                }
                this.addScopeBasedonPrePoints[0] = addScopeBasedonPrePoints;
                break;
            case 'y':
                for (int i = 0; i < prepoints.length; i++) {
                    this.prePoints[1][this.ypre++] = prepoints[i];
                }
                this.addScopeBasedonPrePoints[1] = addScopeBasedonPrePoints;
                break;
            case 'z':
                for (int i = 0; i < prepoints.length; i++) {
                    this.prePoints[2][this.zpre++] = prepoints[i];
                }
                this.addScopeBasedonPrePoints[2] = addScopeBasedonPrePoints;
                break;
        }
    }

    public double getcoordValue(int coord) {
        double temf = 0;
        switch (coord) {
            case 'x':
                temf = this.pointCoordinates[0];
                break;
            case 'y':
                temf = this.pointCoordinates[1];
                break;
            case 'z':
                temf = this.pointCoordinates[2];
                break;
        }
        return temf;
    }

    public double[] getcoordMutationScope(int coord) {
        double[] temf = new double[2];
        switch (coord) {
            case 'x':
                temf = this.addScopeBasedonPrePoints[0];
                break;
            case 'y':
                temf = this.addScopeBasedonPrePoints[1];
                break;
            case 'z':
                temf = this.addScopeBasedonPrePoints[2];
                break;
        }
        return temf;
    }

    public int getpreCount(int coord) {
        int temi = 0;
        switch (coord) {
            case 'x':
                temi = this.xpre;
                break;
            case 'y':
                temi = this.ypre;
                break;
            case 'z':
                temi = this.zpre;
                break;
        }
        return temi;
    }

    public int getnextCount(int coord) {
        int temi = 0;
        switch (coord) {
            case 'x':
                temi = this.xnext;
                break;
            case 'y':
                temi = this.ynext;
                break;
            case 'z':
                temi = this.znext;
                break;
        }
        return temi;
    }
}
