package core.problem.IGA.Graph.Validater;

import core.problem.Individual;

/**
 * @author 郝国生  HAO Guo-Sheng
 */

public class FractualValidater {

    double p;
    double q;
    int r, j, b;
    int rb, gb, bb;
    double reZ, imZ, ze0 = 0.0055;
    int x, y;

    public FractualValidater(Individual ind) {
        double p1 = ind.getDecisionVariable().getGeneCodes()[0];
        double q1 = ind.getDecisionVariable().getGeneCodes()[1];
        p = (double) p1;
        q = (double) q1;
        r = (int) ind.getDecisionVariable().getGeneCodes()[2];
        j = (int) ind.getDecisionVariable().getGeneCodes()[3];
        b = (int) ind.getDecisionVariable().getGeneCodes()[4];
        rb = (int) ind.getDecisionVariable().getGeneCodes()[5];
        gb = (int) ind.getDecisionVariable().getGeneCodes()[6];
        bb = (int) ind.getDecisionVariable().getGeneCodes()[7];
    }

    public int juliaZ(double x0, double y0) {
        double xk, yk;
        int i;
        for (i = 0; i < 47; i++) {
            xk = x0 * x0 - y0 * y0 + p;
            yk = 2 * x0 * y0 + q;
            if (xk * xk + yk * yk > 4) {
                return i;
            }
            x0 = xk;
            y0 = yk;
        }
        return i;
    }

    public long Paint(double p, double q) {
        long c = 0;
        imZ = -1.5;
        for (y = 0; y < 480; y++) {
            reZ = -1.5;
            for (x = 0; x < 640; x++) {
                if (juliaZ(reZ, imZ) == 47) {
                    c++;
                }
                reZ = reZ + ze0;
            }
            imZ = imZ + ze0;
        }
        return c;
    }

    //读ControlParameters.problemNum，根据不同的分形图，进行验证
    public boolean Validater() {
        long c = Paint(p, q);
        return c < 5000 || c > 70000 || (r == rb && j == gb && b == bb);
    }
}
