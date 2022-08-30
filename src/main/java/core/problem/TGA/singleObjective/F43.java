package core.problem.TGA.singleObjective;

import core.problem.DecisionVariables.DoubleDecisionVariable;
import core.problem.FactoryProblems;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class F43 extends Problem<DoubleDecisionVariable> {
//参考原来的C语言代码
    @Override
    public F43 init(int dimension) {
        super.init(dimension);stopFitness = new double[]{110};
        dimension = 18;
        this.dimension = dimension;
        variableProperties = new double[dimension][3];
        for (int i = 0; i < dimension; i++) {
            variableProperties[i][0] = 4;
            variableProperties[i][1] = -4;
            variableProperties[i][2] = 0.00001;
        }
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
	int i, j, k, a, b;
	 double xd, yd, zd, ed, ud, sum = 0;

	
	 double minima[] = { -1.,-3.,-6.,-9.103852,-12.712062,-16.505384,-19.821489,-24.113360,
		-28.422532,-32.765970,-37.967600,-44.326801,-47.845157,-52.322627,-56.815742,-61.317995,
		-66.530949,-72.659782,-77.1777043,-81.684571,-86.809782,-02.844472,-97.348815,-102.372663 };

	k = dimension/ 3;
	if (k < 2){  // default if k<2
		k = 2;
		dimension = 6;
	}

	for (i = 0; i < k - 1; i++)
	{
		for (j = i + 1; j < k; j++)
		{
			a = 3 * i;
			b = 3 * j;
			xd = inputedIndividual.getDecisionVariable().getGeneCodes()[a] - inputedIndividual.getDecisionVariable().getGeneCodes()[b];
			yd = inputedIndividual.getDecisionVariable().getGeneCodes()[a + 1] - inputedIndividual.getDecisionVariable().getGeneCodes()[b + 1];
			zd = inputedIndividual.getDecisionVariable().getGeneCodes()[a + 2] - inputedIndividual.getDecisionVariable().getGeneCodes()[b + 2];
			ed = xd*xd + yd*yd + zd*zd;
			ud = ed*ed*ed;
			if (ud > 1.0e-10) sum += (1.0 / ud - 2.0) / ud;
			else sum += 1.0e20; 
		}
	}
	
	sum+= 12.7120622568;
        inputedIndividual.getDecisionVariable().setFitness(new double[]{-sum});
    }

    @Override
    public boolean isIECProblem() {
        return false;
    }

    @Override
    public String getName() {
        return FactoryProblems.getName(43, dimension);
    }

    @Override
    public void generateBackground() {
    }
    
}
