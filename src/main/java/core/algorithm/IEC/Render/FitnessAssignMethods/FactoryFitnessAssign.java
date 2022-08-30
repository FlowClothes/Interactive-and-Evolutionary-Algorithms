package core.algorithm.IEC.Render.FitnessAssignMethods;

import core.controllers.ControlParameters;
import javax.swing.JPanel;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class FactoryFitnessAssign {

    BaseFitnessAssignMethod bfa;

    public BaseFitnessAssignMethod getFitnessAssign(JPanel pa) {
        switch (ControlParameters.fitnessAssign) {
            //"模糊赋值方法", "精确赋值方法", "分等级赋值方法", "Eye track赋值方法", "区间赋值方法", "最值赋值方法"
            case 0://"模糊赋值方法"
                this.bfa = new FuzzyAssign(pa);
                break;
            case 1://精确赋值方法
                this.bfa = new TextAssign(pa);
                break;
            case 2://"分等级赋值方法"
                this.bfa = new RadioAssign(pa);
                break;
            case 3:
                this.bfa = new Eyetrack(pa);
                break;
            case 4://"区间赋值方法"
                this.bfa = new IntervalAssign(pa);
                break;
            case 5://"最值赋值方法"
                this.bfa = new OneAssign(pa);
                break;
        }
        return this.bfa;
    }
}
