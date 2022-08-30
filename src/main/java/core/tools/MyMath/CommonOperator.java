package core.tools.MyMath;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class CommonOperator {

    public static int[] getCommon(double[] a, double[] b, double delter) {
        int[] temInt=new int[a.length];
        int commonCount=0;
        for(int i=0;i<a.length;i++){
            if(Math.abs(a[i]-b[i])<delter){
                temInt[commonCount++]=i;
            }
        }
        int[] result=new int[commonCount];
        System.arraycopy(temInt, 0, result, 0, commonCount);
        return result;
    }
    public static int[] getCommon(String[] a, String[] b) {
        int[] temInt=new int[a.length];
        int commonCount=0;
        for(int i=0;i<a.length;i++){
            if(a[i].equals(b[i])){
                temInt[commonCount++]=i;
            }
        }
        int[] result=new int[commonCount];
        System.arraycopy(temInt, 0, result, 0, commonCount);
        return result;
    }
}
