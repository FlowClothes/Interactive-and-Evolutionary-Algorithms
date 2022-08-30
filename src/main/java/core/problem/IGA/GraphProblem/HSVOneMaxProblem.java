package core.problem.IGA.GraphProblem;

import core.problem.DecisionVariables.GenecodeType;
import core.problem.Individual;
import core.problem.Problem;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class HSVOneMaxProblem extends Problem {
 /*参考百度百科：https://baike.baidu.com/item/HSV/547122?fr=aladdin
1.色调H：用角度度量，取值范围为0°～360°，从红色开始按逆时针方向计算，红色为0°，绿色为120°,蓝色为240°。它们的补色是：黄色为60°，青色为180°,品红为300°；
2.饱和度S：饱和度S表示颜色接近光谱色的程度。一种颜色，可以看成是某种光谱色与白色混合的结果。其中光谱色所占的比例愈大，颜色接近光谱色的程度就愈高，颜色的饱和度也就愈高。饱和度高，颜色则深而艳。光谱色的白光成分为0，饱和度达到最高。通常取值范围为0%～100%，值越大，颜色越饱和。
3.明度V:明度表示颜色明亮的程度，对于光源色，明度值与发光体的光亮度有关；对于物体色，此值和物体的透射比或反射比有关。通常取值范围为0%（黑）到100%（白）。
RGB和CMY颜色模型都是面向硬件的，而HSV（Hue Saturation Value）颜色模型是面向用户的。
HSV模型的三维表示从RGB立方体演化而来。设想从RGB沿立方体对角线的白色顶点向黑色顶点观察，就可以看到立方体的六边形外形。六边形边界表示色彩，水平轴表示纯度，明度沿垂直轴测量。
*/
    @Override
    public HSVOneMaxProblem init(int dimension) {
        super.init(this.dimension);maxFitness = new double[]{0};
        stopFitness = new double[]{0};
        variableProperties = new double[3][3];
        //individualLength = 24;//类似于RBG共3个颜色，每位有8位二进制组成
        variableProperties[0][2] = 1f;
        variableProperties[0][1] = 0;
        variableProperties[0][0] = 360;
        variableProperties[1][2] = 0.001f;
        variableProperties[1][1] = 0;
        variableProperties[1][0] = 1;
        variableProperties[2][2] = 0.001f;
        variableProperties[2][1] = 0;
        variableProperties[2][0] = 1;
        this.dimension=3;
//        variableSplit = new int[]{0, 8, 13, 18};
        
        return this;
    }

    @Override
    public void evaluate(Individual inputedIndividual) {
    }

    @Override
    public boolean isIECProblem() {
        return true;
    }

    @Override
    public String getName() {
       return "HSVOneMax";
    }

    @Override
    public void generateBackground() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
