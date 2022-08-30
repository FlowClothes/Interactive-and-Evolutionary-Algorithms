package core.problem.Decoder;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public abstract class BaseDecoder {

    private String[] virtualCodeStr;//genecodes of String type
    private double[] virtualCodeDouble;//genecodes of double type
    private int[] virtualCodeLocation;//
    private double[][] mutationScope;//mutation scope of each Gene-Sense-Unit(GSU)
    private double[] realCodes;//the real value of variable Xi

    //先setVirtualCode，再decode
    public void decode() {
    }

    public abstract double[] decode(String binaryCodeString, int[] variableSplit, double[][] scope);
    // 对二进制进行解码，第一个参数是二进制串，第二个参数是对二进制串进行分割的依据，第三个参数是种个基因编码的上限，下限，以及二进制对应的最大值

    public double[] decode(String binaryCodeString) {
        return realCodes;
    }

    public String[] getVirtualCodeStr() {
        return virtualCodeStr;
    }

    public void setVirtualCodeStr(String[] virtualCodeStr) {
        this.virtualCodeStr = new String[virtualCodeStr.length];
        System.arraycopy(virtualCodeStr, 0, this.virtualCodeStr, 0, virtualCodeStr.length);
    }

    public double[] getVirtualCodeDouble() {
        return virtualCodeDouble;
    }

    public void setVirtualCodeDouble(double[] virtualCodeDouble) {
        if (null != virtualCodeDouble && virtualCodeDouble.length > 0) {
            this.virtualCodeDouble = new double[virtualCodeDouble.length];
            System.arraycopy(virtualCodeDouble, 0, this.virtualCodeDouble, 0, virtualCodeDouble.length);
        }
    }

    public int[] getVirtualCodeLocation() {
        return virtualCodeLocation;
    }

    public void setVirtualCodeLocation(int[] virtualCodeLocation) {
        if (null != virtualCodeLocation && virtualCodeLocation.length > 0) {
            this.virtualCodeLocation = new int[virtualCodeLocation.length];
            System.arraycopy(virtualCodeLocation, 0, this.virtualCodeLocation, 0, virtualCodeLocation.length);
        }
    }

    public double[][] getMutationScope() {
        return mutationScope;
    }

    public void setMutationScope(double[][] mutationScope) {
        if (null != mutationScope && mutationScope.length > 0) {
            this.mutationScope = new double[mutationScope.length][mutationScope[0].length];
            for (int i = 0; i < mutationScope.length; i++) {
                System.arraycopy(mutationScope[i], 0, this.mutationScope[i], 0, mutationScope[i].length);
            }
        }
    }

    public double[] getRealCodes() {
        return realCodes;
    }

    public void setRealCodes(double[] realCodes) {
        if (realCodes != null & realCodes.length > 0) {
            this.realCodes = new double[realCodes.length];
            System.arraycopy(realCodes, 0, this.realCodes, 0, realCodes.length);
        }
    }
}
