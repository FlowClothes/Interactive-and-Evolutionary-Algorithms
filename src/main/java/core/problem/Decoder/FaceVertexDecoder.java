package core.problem.Decoder;

import core.problem.IGA.Graph.Face.Base.FaceControlParameters;
import core.problem.IGA.Graph.Face.Base.FaceVertexData;
import core.problem.IGA.Graph.Face.Base.GraphPoint;
import core.problem.IGA.Graph.Face.Base.miniGraphPointforMutationScope;
import core.tools.MyDataStructure.MyArray;
import core.tools.MyMath.RandomGenerator;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class FaceVertexDecoder extends BaseDecoder {

    private miniGraphPointforMutationScope[] graphPointMutationScope;
    private boolean fixed = false;//对于需要进化的点是否需要随机生成，是true,否false;这主要是针对进化过程中，初始化种群时，第一个个体设其为测量值；

    public void init() {//随机生成的进化点的坐标
        this.setRealCodes(new double[FaceVertexData.faceCoordinate.length]);
        System.arraycopy(FaceVertexData.faceCoordinate, 0, this.getRealCodes(), 0, FaceVertexData.faceCoordinate.length);
        int temint = FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length * 3;
        graphPointMutationScope = new miniGraphPointforMutationScope[temint];
        for (int i = 0; i < FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length; i++) {
            GraphPoint temGP1 = FaceVertexData.gp[FaceVertexData.facePart[FaceControlParameters.part].getVertNum()[i]];
            //先生成x坐标；
            switch (temGP1.getPointNum()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    break;//由于这些点的x坐标是0，所以不变动；
                default: {
                    this.generateLocalVert('x', temGP1);
                }
            }
            //求y坐标
            switch (temGP1.getPointNum()) {
                case 3:
                    break;//由于这些点的y坐标是0，所以不变动；

                default: {
                    this.generateLocalVert('y', temGP1);
                }
            }
            //求z坐标
            switch (temGP1.getPointNum()) {
                case 3:
                    break;//由于这些点的z坐标是0，所以不变动；

                default: {
                    this.generateLocalVert('z', temGP1);
                }
            }
        }
        if (fixed) {
        } else {
            this.updateVertCoordinates();

        }
    }

    private void generateLocalVert(int coord, GraphPoint gp1) {
        double temf = 0, prevalue = 0, nextValue = 0;
        int temi = 0, temLoc = 0;
        switch (coord) {
            case 'x':
                temi = 0;
                break;
            case 'y':
                temi = 1;
                break;
            case 'z':
                temi = 2;
                break;
        }

        //有些结点无前驱，但一定有变化范围，有些节点无后继；所有的非特殊结点（即可编码）都有变化范围
        temLoc = gp1.getPointNum() * 3 + temi;
        this.graphPointMutationScope[temLoc] = new miniGraphPointforMutationScope();
        this.graphPointMutationScope[temLoc].setPointNum(gp1.getPointNum());//ControlParameters.gsuCount,temLoc可能是FaceVertexData.fp[FaceControlParameters.part].getVertNum().length * 3;，下面后面备注的可能也是这值
        this.graphPointMutationScope[temLoc].setCoord(coord);//ControlParameters.gsuCount
        double[] delt = gp1.getcoordMutationScope(coord);//所有点都有变化范围

        if (gp1.getpreCount(coord) > 0) {//有前驱，则取其前驱值

            prevalue = gp1.getfirstPrePoint(coord).getcoordValue(coord);
            if (gp1.getnextCount(coord) > 0) {//有后继，则取其后继值

                double[] nextTem = new double[gp1.getnextCount(coord)];
                for (int i = 0; i < gp1.getnextCount(coord); i++) {
                    nextTem[i] = gp1.getNextPoints()[temi][i].getcoordValue(coord);
                }
                nextValue = MyArray.getMin(nextTem);
                if (delt[0] < 1.0e-15) {//delt[0]=0

                    this.graphPointMutationScope[temLoc].setMutationScope(new double[]{prevalue + delt[0], MyArray.getMin(new double[]{prevalue + delt[1], nextValue})});//ControlParameters.gsuCount
                }
            } else {//没有后继

                this.graphPointMutationScope[temLoc].setMutationScope(new double[]{prevalue + delt[0], prevalue + delt[1]});//ControlParameters.gsuCount
            }
        } else {//没有前驱,则一定有特定的变化范围
            this.graphPointMutationScope[temLoc].setMutationScope(new double[]{delt[0], delt[1]});//ControlParameters.gsuCount
        }
        if (fixed) {
            temf = FaceVertexData.faceCoordinate[temLoc];
        } else {
            temf = RandomGenerator.getRandom(this.graphPointMutationScope[FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length * 3].getMutationScope()[0], this.graphPointMutationScope[FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length * 3].getMutationScope()[1]);//ControlParameters.gsuCount
        }
        this.getVirtualCodeDouble()[FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length * 3] = temf;//ControlParameters.gsuCount
        this.getVirtualCodeLocation()[FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length * 3] = temLoc;//ControlParameters.gsuCount
    }

    public void setMutationScope() {//目的是利用新的坐标生成重新确定新的mutationscope，以便于变异操作进行处理
        //利用某row的this.graphPointMutationScope.getMutationScope()[0]及[1]和this.localVertexValue及 this.localVertLocation联合处理
        for (int i = 0; i < FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length * 3; i++) {//ControlParameters.gsuCount
            this.generateLocalVert(this.graphPointMutationScope[i].getCoord(), FaceVertexData.gp[this.graphPointMutationScope[i].getPointNum()]);
        }
    }

    @Override
    public void decode() {//进化结果进行打包，以便于RenderFacePanel进行呈现
        this.updateVertCoordinates();
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    private void updateVertCoordinates() {
        for(int i=0;i<FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length * 3;i++){//ControlParameters.gsuCount
            this.getRealCodes()[this.getVirtualCodeLocation()[i]]=this.getVirtualCodeDouble()[i];
        }
    }

    @Override
    public double[] decode(String binaryCodeString, int[] variableSplit, double[][] scope) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
