package exclusion.algorithm.operator.filter.offspringFilter;

import core.problem.DecisionVariables.DecisionVariable;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class DecisionVariableWithExclusionRadius {
    private DecisionVariable individual;
    private double exclusionRadius;

    public double getExclusionRadius() {
        return exclusionRadius;
    }
    public void setExclusionRadius(double exclusionRadius) {
        this.exclusionRadius = exclusionRadius;
    }

    public DecisionVariable getIndividual() {
        return individual;
    }

    public void setIndividual(DecisionVariable individual) {
        this.individual = individual;
    }
}
