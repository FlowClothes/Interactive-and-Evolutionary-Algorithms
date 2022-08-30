package core.problem.IGA.Graph.Face;

import core.tools.Graph.Framework.Framework3D;
import core.problem.IGA.Graph.Face.Base.FaceControlParameters;
import core.problem.IGA.Graph.Face.Base.RenderFace;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class FaceUnit extends Framework3D {

    private double[] vertCoord;

    public FaceUnit(double[] vertext) {
        this.setCoord(vertext);
        this.setTextureFilename(FaceControlParameters.textureFilename);
        this.setMyShape3d(new RenderFace(vertext));
    }

    public double[] getVertCoord() {
        return vertCoord;
    }

    public void setCoord(double[] vert) {
        this.vertCoord = new double[vert.length];
        System.arraycopy(vert, 0, this.vertCoord, 0, vert.length);
    }

    public void vertCoordNormalization() {//将点坐标进行变化，以便能在一屏上显示所有的人脸
        double a = 1f;
        switch (core.controllers.ControlParameters.fitnessAssign) {
            case 1:
            case 4:
            case 2:
                a = 0.2f;
                break;
            case 3:
                a = 0.3f;
                break;
            case 5:
                a = 0.05f;
                break;
            case 6:
                a = 0.001f;
                break;
            default:
                a = 1f;
        }
        for (int i = 0; i < this.vertCoord.length; i++) {
            this.vertCoord[i] = this.vertCoord[i] / (a + 0.5f);
        }
    }
}

