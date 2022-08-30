package core.tools.MyMath;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class XorOperator {

    public static int[] getXor(double[] from, double[] reference, double delter) {
        int[] temInt = new int[from.length];
        int xorCount = 0;
        for (int i = 0; i < from.length; i++) {
            if (Math.abs(from[i] - reference[i]) > delter) {
                temInt[xorCount++] = i;
            }
        }
        int[] result = new int[xorCount];
        System.arraycopy(temInt, 0, result, 0, xorCount);
        return result;
    }

    public static int[] getXor(String[] from, String[] reference) {
        int[] temInt = new int[from.length];
        int xorCount = 0;
        for (int i = 0; i < from.length; i++) {
            if (from[i].equals(reference[i])) {
            } else {
                temInt[xorCount++] = i;
            }
        }
        int[] result = new int[xorCount];
        System.arraycopy(temInt, 0, result, 0, xorCount);
        return result;
    }
}
