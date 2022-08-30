package core.problem.Decoder;

import core.tools.Transfer2_8_10_16.TransferCEO;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class BinaryDeCoder extends BaseDecoder {

    @Override
    //首先对基因编码进行分段，依据是ControlParameters.variableSplit[]，得到各变量对应的基因编码gi(i=1,2,....,ControlParameters.gsuCount)，然后对gi进行解码，得到表现型数组，保存到Individual.phenoValue[]
    public  double[] decode(String binaryCodeString, int[] variableSplit, double[][] scope) {//virtualCodeStr的元素个数是1
        //1.对基因编码进行分段，依据是ControlParameters.variableSplit[]，得到各变量对应的基因编码gi(i=1,2,....,ControlParameters.gsuCount)
        double[] result=new double[scope.length];
        for (int i = 0; i < scope.length; i++) {//
            String tem;
            tem = this.getVirtualCodeStr()[0].substring(variableSplit[i], variableSplit[i + 1]);
            TransferCEO tsf = new TransferCEO(tem, scope[i][0], scope[i][1], scope[i][2],"01");
            result[i] = tsf.getX();
        }
        return result;
    }
    
}
