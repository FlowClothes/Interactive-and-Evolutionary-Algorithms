package core.problem.IGA.GraphPanel;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import core.problem.IGA.GraphProblem.FractralJuliaProblem;
import core.problem.Individual;

/**
 * @author 郝国生 HAO Guo-Sheng
 * @author 季君 JI Jun
 */
public class Julia extends JPanel {

    FractralJuliaProblem problem = new FractralJuliaProblem();
    double p, q;
    int r, j, b;
    double reZ, imZ, ze0 = 0.0055;
    int x, y;

    //  Zn+1 = Zn^2 + C, C = const
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

    //    绘制
    @Override
    public void paint(Graphics g) {
        this.commonPaint(p, q, r, j, b, g);
    }

    public void repaintme(Individual ind) {//在调用该方法前需要首先调用
        this.commonPaint(p, q, r, j, b, this.getGraphics());
    }

    public void commonPaint(double p, double q, int r, int j, int b, Graphics g) {
        setP(p);
        setQ(q);
        setR(r);
        setG(j);
        setB(b);
        imZ = -1.5;
        for (y = 0; y < 480; y++) {
            reZ = -1.8;
            for (x = 0; x < 640; x++) {
                if (juliaZ(reZ, imZ) == 47) {
                    g.setColor(new Color(r, j, b));
                    g.drawLine(x / 2, y / 2, x / 2, y / 2);//将分形图缩小
                }
                reZ = reZ + ze0;
            }
            imZ = imZ + ze0;
        }
    }

    public void setP(double p) {
        this.p = p;
    }

    public void setQ(double q) {
        this.q = q;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setG(int g) {
        this.j = g;
    }

    public void setB(int b) {
        this.b = b;
    }
}
