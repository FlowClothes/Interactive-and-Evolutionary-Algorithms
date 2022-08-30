package core.problem.DecisionVariables;

import core.algorithm.operator.Crossover.FactoryCrossover;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import core.problem.FactoryProblems;
import core.tools.MyMath.RandomGenerator;

/**
 * ControlParameters.genecodeType一定等于0
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class StringDecisionVariable extends AbstractDecisionVariable {
    
    @Override
    public GenecodeType getGenecodeType() {
        return GenecodeType.STRINGCODE;
    }

    @Override
    public String getX() {
        return getGenecodesString();
    }

    @Override
    public String getGeneralGenecodeString() {
        return this.getGenecodesString();
    }

    @Override
    public int getSolutionNumberDistance(DecisionVariable individual) {
        int result = 0;
        char[] str1 = this.getGenecodesString().toCharArray();
        char[] str2 = ((StringDecisionVariable) individual).getGenecodesString().toCharArray();
        for (int i = 0; i < str1.length; i++) {
            result += Math.abs(str1[i] - str2[i]);
        }
        return result;
    }

    public List<String> swapString(String str1, String str2) {
        List<String> result = new LinkedList<>();
        String temStr1;
        for (int j = 0; j < FactoryCrossover.crossoverPointNum; j++) {
            int temInt = RandomGenerator.getRandom(0, str1.length()); //Here we assum that length of str1 and str2 is equal
            temStr1 = str1.substring(temInt);
            // System.out.println("StrLength is:"+StrLength+"temInt is"+temInt);
            str1 = str1.substring(0, temInt) + str2.substring(temInt);
            str2 = str2.substring(0, temInt) + temStr1;
        }
        result.add(str1);
        result.add(str2);
        return result;
    }

    @Override
    public List<DecisionVariable> crossover(DecisionVariable individual) {
        List<DecisionVariable> result = new LinkedList<>();
        List<String> strings = swapString(this.getGenecodesString(),
                individual.getGenecodesString());
        DecisionVariable ind, ind1;
        ind = (DecisionVariable) this.clone();
        ind.setGenecodesString(strings.get(0));
        ind1 = (DecisionVariable) individual.clone();
        //之所以用clone，而不是引用，避免交叉后再被交叉
        ind1.setGenecodesString(strings.get(1));
        result.add(ind);
        result.add(ind1);
        return result;
    }

    @Override
    public char flip(char a, int mutateStep) {
        if (mutateStep > FactoryProblems.currentProblem.getCodeScope().length()) {
            mutateStep = FactoryProblems.currentProblem.getCodeScope().length() / 2;
        }
        int codeLocation = RandomGenerator.getRandom(0, FactoryProblems.currentProblem.getCodeScope().length());
        char b = FactoryProblems.currentProblem.getCodeScope().charAt(codeLocation);
        while (Math.abs(a - b) < mutateStep) {
            codeLocation = RandomGenerator.getRandom(0, FactoryProblems.currentProblem.getCodeScope().length());
            b = FactoryProblems.currentProblem.getCodeScope().charAt(codeLocation);
        }
        return b;
    }

    @Override
    public DecisionVariable initVariable() {
        DecisionVariable result = new StringDecisionVariable();
        String temGenecode = "";
        int temint = 0;
        for (int j = 0; j < FactoryProblems.currentProblem.getIndividualLength(); j++) {
            temint = RandomGenerator.getRandom(0, FactoryProblems.currentProblem.getCodeScope().length());
            temGenecode += FactoryProblems.currentProblem.getCodeScope().charAt(temint);
        }
        result.setGenecodesString(temGenecode);
        return result;
    }

    @Override
    public double getDistance(DecisionVariable another) {
        double result = 0;
        for (int i = 0; i < this.genecodesString.length(); i++) {
            result += Math.pow(this.genecodesString.charAt(i) - another.getGenecodesString().charAt(i), 2);
        }
        return Math.sqrt(result);
    }

    @Override
    public DecisionVariable clone() {
        StringDecisionVariable result = new StringDecisionVariable();
        result.setGenecodesString(genecodesString);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        char[] thisChars = this.getGenecodesString().toCharArray();
        char[] compared = ((StringDecisionVariable) o).getGenecodesString().toCharArray();
        for (int i = 0; i < thisChars.length; i++) {
            if (thisChars[i] > compared[i]) {
                return 1;
            } else if (thisChars[i] < compared[i]) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof StringDecisionVariable) && !(obj instanceof BinarycodeDecisionVariable)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        StringDecisionVariable rhs = (StringDecisionVariable) obj;
        return new EqualsBuilder().
                append(this.getGenecodesString(), rhs.getGenecodesString()).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 61). // Individual
                // if deriving: appendSuper(super.hashCode()).
                append(this.getGenecodesString()).
                toHashCode();
    }
}
