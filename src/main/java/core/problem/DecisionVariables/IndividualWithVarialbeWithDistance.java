/*
*用来保存两个DecisionVariables及其之间的距离，这在聚类时会用到
 */
package core.problem.DecisionVariables;

import core.algorithm.accessory.IndividualWithAge;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class IndividualWithVarialbeWithDistance implements Comparable<IndividualWithVarialbeWithDistance> {

    private final IndividualWithAge dv1;
    private final IndividualWithAge dv2;
    private final double distance;

    public IndividualWithVarialbeWithDistance(IndividualWithAge dsv1, IndividualWithAge dsv2) {
        dv1 = dsv1;
        dv2 = dsv2;
        distance = dv1.getIndividual().getDecisionVariable().getDistance(dv2.getIndividual().getDecisionVariable());
    }
    

    @Override
    public int compareTo(IndividualWithVarialbeWithDistance o) {
        if (this.getDistance() > o.getDistance()) {
            return 1;
        }
        if (this.getDistance() < o.getDistance()) {
            return -1;
        }
        return 0;
    }
    public IndividualWithAge getFirstIndividual() {
        //dv1的fitness比dv2的小
        return dv1;
    }

    public double getDistance() {
        return distance;
    }

}
