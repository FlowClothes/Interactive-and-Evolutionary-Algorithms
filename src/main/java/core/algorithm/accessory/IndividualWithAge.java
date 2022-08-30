package core.algorithm.accessory;

import core.problem.Individual;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class IndividualWithAge implements Comparable<IndividualWithAge> {

    private final Individual individual;
    private int age;

    public IndividualWithAge(Individual individual, int age) {
        this.individual = individual.clone();
        this.age = age;
    }

    @Override
    public int compareTo(IndividualWithAge o) {
        return this.getIndividual().compareTo(o.getIndividual());
    }

    public Individual getIndividual() {
        return individual;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }
}
