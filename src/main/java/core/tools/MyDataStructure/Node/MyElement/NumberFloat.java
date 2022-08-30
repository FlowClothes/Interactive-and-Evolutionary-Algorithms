package core.tools.MyDataStructure.Node.MyElement;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class NumberFloat implements Comparable<NumberFloat> {//带编号的Float
    private float number , value;
    public int compareTo(NumberFloat m){
        if(this.value<m.value){
            return -1;
        }else{
            if(this.value==m.value){
                return 0;
            }else{
                return 1;
            }
        }
    }

    /**
     * @return the number
     */
    public float getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(float number) {
        this.number = number;
    }

    /**
     * @return the value
     */
    public float getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(float value) {
        this.value = value;
    }
}
