package core.problem.Decoder;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class FaceAffineDecoder extends BaseDecoder{
    private boolean fixed = false;//对于需要进化的点是否需要随机生成，是false,否true;这主要是针对进化过程中，初始化种群时，第一个个体设其为测量值；
    private int[] verNum2Evolving;

    @Override
    public double[] decode(String binaryCodeString, int[] variableSplit, double[][] scope) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//    public void init() {//随机生成的进化点的坐标
//        this.setRealCodes(new float[FaceVertexData.faceCoordinate.length]);
//        System.arraycopy(FaceVertexData.faceCoordinate, 0, this.getRealCodes(),0, FaceVertexData.faceCoordinate.length);
//            try {
//                myAff = FaceVertexData.fp[FaceControlParameters.part].getAffineMatrix().getClone();
//            } catch (CloneNotSupportedException ex) {
//                Logger.getLogger(FaceAffineEnDecoder.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            myAff.initParameters();
//            myAff.calAffineMatrixValue();
//            this.setVirtualCodeFloat( myAff.getParameters());
//            this.setMutationScope(new float[ControlParameters.gsuCount][2]);
//            //设置mutationScope
//            for (int i = 0; i < ControlParameters.gsuCount; i++) {
//                System.arraycopy(myAff.getScope(i), 0, this.getMutationScope()[i], 0,myAff.getScope(i).length);
//            }
//            this.setAffineCoordValue();
//    }
//
//    private void setAffineCoordValue() {//根据this.virtualCodeLocation,this.localVertexValue设置坐标；
//        if (fixed) {
//        } else {
//            verNum2Evolving = FaceVertexData.fp[FaceControlParameters.part].getVertNum();
//            float[] temFloat = new float[3];
//            for (int i = 0; i < this.verNum2Evolving.length; i++) {
//                temFloat = MyMatrix.multiply(myAff.getAffineMatrixValue(), new float[]{this.getRealCodes()[verNum2Evolving[i] * 3], this.getRealCodes()[verNum2Evolving[i] * 3 + 1], this.getRealCodes()[verNum2Evolving[i] * 3 + 2]});
//                for (int j = 0; j < 3; j++) {
//                    this.getRealCodes()[verNum2Evolving[i] * 3+j] = temFloat[j] + myAff.getTranslation()[j];
//                }
//            }
//        }
//    }
//
//
//    @Override
//   public void decode() {//进化结果进行打包，以便于RenderFacePanel进行呈现
//            for (int i = 0; i < ControlParameters.gsuCount; i++) {
//                myAff.setParameters(i, this.getVirtualCodeFloat()[i]);
//            }
//            myAff.calAffineMatrixValue();
//            this.setAffineCoordValue();
//     }
//
//    public void setFixed(boolean fixed) {
//        this.fixed = fixed;
//    }
//    public void set(){
//
//    }
}
