package core.algorithm.operator.Selection;

import java.util.List;
import core.problem.Individual;
import core.problem.Population;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public interface Selection {

//因为选择算子会有重复个体，所以不用TreeSet，而用List   
    public List<Individual> getSelectionResult(Population population) ;
}
