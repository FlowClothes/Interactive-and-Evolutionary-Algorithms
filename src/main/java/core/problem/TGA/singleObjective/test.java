package core.problem.TGA.singleObjective;

import core.problem.DecisionVariables.BinarycodeDecisionVariable;
import java.util.logging.Level;
import java.util.logging.Logger;
import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.DecisionVariables.GenecodeType;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author hgs07
 */
public class test {

    public static void main(String[] args) {
        BinaryDecisionVariable();
    }

    public static void BinaryDecisionVariable() {
        int problemNum = 0;
        FactoryProblems.registerProblems();
        FactoryProblems.initProblem(problemNum, GenecodeType.BINARYCODE);
        BinarycodeDecisionVariable binarycodeDecisionVariable = new BinarycodeDecisionVariable();
        Individual ind1 = new Individual(binarycodeDecisionVariable);
        BinarycodeDecisionVariable binarycodeDecisionVariable2 = new BinarycodeDecisionVariable();
        Individual ind2 = new Individual(binarycodeDecisionVariable2);
        binarycodeDecisionVariable.setGenecodesString("10010101010110101101000011001");
        binarycodeDecisionVariable2.setGenecodesString("11010101000110101101000011011");
//       test t1 = new test();
        Problem f11 = FactoryProblems.currentProblem;
        f11.init(1);
        {
            int[] variableSplit = new int[2];
            variableSplit[0] = 0;
            variableSplit[1] = "11010101000110101101000011011".length();
            f11.setVariableSplit(variableSplit);
        }
        f11.evaluate(ind1);
        f11.evaluate(ind2);
        System.out.println(f11.getName());
        System.out.println(binarycodeDecisionVariable.getFitness()[0]);
        System.out.println(binarycodeDecisionVariable2.getFitness()[0]);
    }

    public static void DoubleDecisionVariable() {
        int problemNum = 48;
        FactoryProblems.registerProblems();
        FactoryProblems.initProblem(problemNum, GenecodeType.DOUBLECODE);
        DoubleDecisionVariable doubleD1 = new DoubleDecisionVariable();
        Individual ind1 = new Individual(doubleD1);
        DoubleDecisionVariable doubleDe2 = new DoubleDecisionVariable();
        Individual ind2 = new Individual(doubleDe2);
        doubleD1.setGeneCodes(new double[]{4.590985365571437, 2.5005980412142534, 4.020622673966482,});
        doubleDe2.setGeneCodes(new double[]{1, 2, 3});
        test t1 = new test();
        Problem f11 = FactoryProblems.currentProblem;
        try {
            f11 = (Problem) t1.getFunction(problemNum).newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        f11.init(3);
        f11.evaluate(ind1);
        f11.evaluate(ind2);
        System.out.println(f11.getName());
        System.out.println(doubleD1.getFitness()[0]);
        System.out.println(doubleDe2.getFitness()[0]);

    }

    public Class getFunction(int i) {
        Class dp = null;
        try {
            dp = Class.forName("core.problem.TGA.singleObjective.F" + i);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dp;
    }
}
