package core.problem.IGA.Graph.Face;

import core.problem.IGA.GraphPanel.BaseIGAGraphIndividual;
import core.problem.IGA.Graph.Face.Base.FaceControlParameters;
import core.problem.IGA.Graph.Face.Base.FaceVertexData;
import core.problem.IGA.Graph.Face.Base.RenderFace;
import core.problem.IGA.AffineMatrixPhenoType;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import core.problem.FactoryProblems;
import core.problem.IGA.GraphProblem.F900;
import core.problem.Individual;
import core.tools.MyDataStructure.MyMatrix;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 * @author 姜艳 Jia Yan
 */
public class Face extends BaseIGAGraphIndividual {

    private FaceUnit faceunit;
    private AffineMatrixPhenoType myAff;


    private void faceVertRecreate(Individual ind) {
        if(FactoryProblems.currentProblem instanceof  F900){//不进行仿射变换
            faceunit.setCoord(FaceVertexData.faceCoordinate);
            double[] tem = ind.getDecisionVariable().getGeneCodes();//ind中保存要优化部分的所有
            for (int i = 0; i < FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length; i++) {
                for (int j = 0; j < 3; j++) {
                    //修改相应的坐标值为Individual的坐标值
                    this.faceunit.getVertCoord()[FaceVertexData.facePart[FaceControlParameters.part].getVertNum()[i] * 3 + j] = tem[i * 3 + j];
                }
            }
            faceunit.vertCoordNormalization();
            faceunit.setMyShape3d(new RenderFace(faceunit.getVertCoord()));
        } else {//仿射变换，ind中包含仿射变换矩阵中normal的值
            myAff.getPhenoValue(ind.getDecisionVariable().getGeneCodes());
            myAff.calAffineMatrixValue();
            this.setAffineCoordValue();
        }

    }

    private void setAffineCoordValue() {//根据this.virtualCodeLocation,this.localVertexValue设置坐标；
        faceunit.setCoord(FaceVertexData.faceCoordinate);
        double[] tem = new double[3];
        for (int i = 0; i < FaceVertexData.facePart[FaceControlParameters.part].getVertNum().length; i++) {
            tem = MyMatrix.multiply(myAff.getAffineMatrixValue(), new double[]{this.faceunit.getVertCoord()[FaceVertexData.facePart[FaceControlParameters.part].getVertNum()[i] * 3], this.faceunit.getVertCoord()[FaceVertexData.facePart[FaceControlParameters.part].getVertNum()[i] * 3 + 1], this.faceunit.getVertCoord()[FaceVertexData.facePart[FaceControlParameters.part].getVertNum()[i] * 3 + 2]});
            for (int j = 0; j < 3; j++) {
                this.faceunit.getVertCoord()[FaceVertexData.facePart[FaceControlParameters.part].getVertNum()[i] * 3 + j] = tem[j] + myAff.getTranslation()[j];
            }
        }
        this.faceunit.vertCoordNormalization();
        this.faceunit.setMyShape3d(new RenderFace(faceunit.getVertCoord()));
    }

    @Override
    public void paintme() {
        faceunit = new FaceUnit(FaceVertexData.faceCoordinate);//面部节点的三维坐标
        this.faceVertRecreate(this.getInd());
        this.setLayout(new BorderLayout());
        this.faceunit.paintme();
        this.setComboxvalue();
        this.add(this.faceunit, BorderLayout.CENTER);
        this.add(box, BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createLineBorder(Color.red));
        this.validate();

    }

    @Override
    public void repaintme(Individual ind) {
        this.setInd(ind);
        this.faceVertRecreate(this.getInd());
        this.setComboxvalue();
        faceunit.repaintme();
        this.validate();
    }
}
