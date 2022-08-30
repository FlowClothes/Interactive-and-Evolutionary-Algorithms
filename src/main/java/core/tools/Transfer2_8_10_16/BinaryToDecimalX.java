package core.tools.Transfer2_8_10_16;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class BinaryToDecimalX extends BaseTransfer {

    @Override
    public void calculateX(String binary, double xMax, double xMin, double maxCodeValue) {
        if(binary.length()>32){
            System.err.print("The binary lenth should be less than 32");
        }
        //由精度来控制编码长度
        this.setX( (xMin + (xMax - xMin) * (Integer.parseUnsignedInt(binary, 2) * (1 / maxCodeValue))));
    }
}
