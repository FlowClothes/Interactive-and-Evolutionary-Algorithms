package core.problem.Decoder;

import core.problem.DecisionVariables.GenecodeType;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class FactoryDecoder {

    String[] virtualCodeStr;
    double[] virtualcodeDouble;
    double[] realcode;

    public FactoryDecoder(String[] virtualcodeStr) {
        this.virtualCodeStr = virtualcodeStr;
    }

    public FactoryDecoder(double[] virtualcodeDouble) {
        this.virtualcodeDouble = virtualcodeDouble;
    }

    public double[] getRealCodes(GenecodeType genecodeType) {
        BaseDecoder bed = null;
        switch (genecodeType) {
            case BINARYCODE://基因编码是字符串型
                bed = new BinaryDeCoder();
                bed.setVirtualCodeStr(virtualCodeStr);
                break;
            case DOUBLECODE:break;
            case NORMALFACECODE://只针对人脸的浮点数组基因编码
                bed = new FaceVertexDecoder();
                bed.setVirtualCodeDouble(virtualcodeDouble);
                break;
            case AFFINEFACECODE://表示采用的是以仿射变换参数为进化对象的浮点数组编码
                bed = new FaceAffineDecoder();
                bed.setVirtualCodeDouble(virtualcodeDouble);
                break;

        }
        bed.decode();
        return bed.getRealCodes();
    }
}
