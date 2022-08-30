/*
 * 各种适应值赋值方法:精确赋值,模糊赋值,区间赋值,EyeTrack赋值,分等级赋值,最值赋值等都要继承该类；
 * 
 */

package core.algorithm.IEC.Render.FitnessAssignMethods;

import javax.swing.JPanel;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class BaseFitnessAssignMethod extends JPanel {
    //BaseFace bf=new BaseFace();
    protected float fitness = 0;
    protected JPanel parentPane;
    

    public BaseFitnessAssignMethod(JPanel parent) {
        super();
        this.parentPane = parent;
   }

    public float getFitness() {
        calFitness();
        return fitness;
    }

    protected void calFitness() {

    }

    public void repaintme() {

    }
}
