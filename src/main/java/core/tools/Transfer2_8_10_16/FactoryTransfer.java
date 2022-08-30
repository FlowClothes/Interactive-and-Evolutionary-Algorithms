package core.tools.Transfer2_8_10_16;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class FactoryTransfer {
//见https://zhuanlan.zhihu.com/p/76806432
    public static BinaryToDecimalX getBinaryToDecimalX(){
        return new BinaryToDecimalX();
    }

    public double geneToPheno(String geneCode, double xmax, double xmin, double maxCodeValue,String codescope) {
        BaseTransfer bgp = null;
        switch (codescope.length()) {
            case 2:
                bgp = new BinaryToDecimalX();
                break;
            //接下来可以写8，16进制的转换函数
            default:
                bgp = new BinaryToDecimalX();
        }
        bgp.calculateX(geneCode, xmax, xmin, maxCodeValue);
        return bgp.getX();
    }
}