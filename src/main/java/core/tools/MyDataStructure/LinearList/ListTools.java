package core.tools.MyDataStructure.LinearList;

import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class ListTools<T> {

    private int mid = 0;//指向元素的位置

    public int getIndex() {
        return mid;
    }

    public static int indexOfIndvidual(Individual target, TreeSet<Individual> sources) {
        int result = 0;
        int i = 0;
        for (Individual individual : sources) {
            if (target.compareTo(individual) > 0) {
                i++;
            } else if (target.compareTo(individual) < 0) {//没找到，但找到了位置，即i

            } else {//==0，找到了

            }

        }
        return result;
    }

    public static Individual getIndividualAt(int index, TreeSet<Individual> individuals) {//返回第i个小的个体
        Individual result = null;
        int i = 0;
        for (Individual individual : individuals) {
            if (i < index) {
                i++;
            }else{//到了第i个
                result=individual;
                break;
            }
        }
        return result;
    }
}
