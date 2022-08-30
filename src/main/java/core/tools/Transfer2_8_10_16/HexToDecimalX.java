package core.tools.Transfer2_8_10_16;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class HexToDecimalX extends BaseTransfer{

    public HexToDecimalX(String hex,float xMax,float xMin, float maxCodeValue) {
        //lowValue指定真实值的下限，upValue指定真实值的上限,
        //maxCodeValue指定编码的最大值，当然了编码的最小值是0
        //hex是那个需要转换的16进制的数
        //System.out.println("传过来的参数hex是" + hex);
        int intLength = hex.length();
       // System.out.println("其长度是：" + intLength);
        int temInt1 = hexchar2dex(hex.charAt(0)), temInt2 = 0;
        for (int i = 1; i < intLength; i++) {
            temInt2 = hexchar2dex(hex.charAt(i));
            temInt1 = temInt1 * 16 + temInt2;
           // System.out.println("正在计算："+i+" 得到的值是 : "+temInt2+"，计算出的值是："+temInt1);
        }
        //System.out.println("maxCodeValue is:" + maxCodeValue + "and the current value is:" + temInt1+"  the xMin is "+xMin+"  the xMax is "+xMax);
            //System.out.println("当前的值====计算出的值是："+temInt1);
            
        float temfloat = xMin + (xMax - xMin) * (temInt1 * (1/maxCodeValue));
        this.setX((float)temfloat);
        //System.out.println("得到的值是："+temfloat);
    }
    public void calculateX(){
        
    }

    public int hexchar2dex(char hex) {
        int temInt2 = 0;
        switch (hex) {
            case '0':
                temInt2 = 0;
                break;
            case '1':
                temInt2 = 1;
                break;
            case '2':
                temInt2 = 2;
                break;
            case '3':
                temInt2 = 3;
                break;
            case '4':
                temInt2 = 4;
                break;
            case '5':
                temInt2 = 5;
                break;
            case '6':
                temInt2 = 6;
                break;
            case '7':
                temInt2 = 7;
                break;
            case '8':
                temInt2 = 8;
                break;
            case '9':
                temInt2 = 9;
                break;
            case 'a':
                temInt2 = 10;
                break;
            case 'b':
                temInt2 = 11;
                break;
            case 'c':
                temInt2 = 12;
                break;
            case 'd':
                temInt2 = 13;
                break;
            case 'e':
                temInt2 = 14;
                break;
            case 'f':
                temInt2 = 15;
                break;
        }
        //System.out.println("hex is:"+hex+"and the return value is:"+temInt2);
        return temInt2;
    }
}
