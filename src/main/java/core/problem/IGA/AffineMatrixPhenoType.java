package core.problem.IGA;

import core.problem.IGA.Graph.Face.Base.FaceControlParameters;
import core.tools.MyDataStructure.MyArray;
import core.tools.MyDataStructure.MyMatrix;
import core.tools.MyDataStructure.Node.NodeWithScopeElement;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class AffineMatrixPhenoType extends BaseIGAPhenoType {

    private int dimension = FaceControlParameters.dimension;
    //下面是仿射变换的几个元变换及各参数的变化范围
    //**Normal数组中记录了编码的元素的下标信息。
    // 如想知识Translation的被编码元素，可用translation[translationnormal[gusNum]]来指向，而其变化范围也可用translationScope[translationnormal[gusNum]]来指向
    //对每个参数的变化范围，都有一个知识链需要维护
    private double[] translation = new double[dimension];
    private NodeWithScopeElement[] translationScope = new NodeWithScopeElement[dimension];
    private double[] scale = new double[dimension];
    private NodeWithScopeElement[] scaleScope = new NodeWithScopeElement[dimension];
    private double[] shear = new double[dimension * (dimension - 1)];
    private NodeWithScopeElement[] shearScope = new NodeWithScopeElement[dimension * (dimension - 1)];
    private double[] rotation = new double[dimension];//是弧度
    private NodeWithScopeElement[] rotationScope = new NodeWithScopeElement[dimension];
    //下面是仿射变换的元矩阵
    private double[][] scaleDouble = new double[dimension][dimension];
    private double[][][] shearDouble = new double[dimension][dimension][dimension];
    private double[][][] rotateDouble
            = new double[dimension][dimension][dimension];
    //
    private double[][] affineMatrixValue = new double[dimension][dimension];
    private int[][] normalIndex = new int[15][2];//仿射变换矩阵的15个参数
    //存放Normal的具体指向哪一个参数，第1列为参数分类：1--translation,2-scale,3--shear,4--rotation;
    //第2列存放具体的指向,如Normal[gusNum]值为{3，2}，则指向shear[2]
    int index = 0;//对应于Controlparameters.gsucount?

    public double[] getPhenoValue(double[] x) {
        for (int i = 0; i < index; i++) {
            this.setParameters(i, x[i]);
        }
        return null;//因为在FacePanel中要通过调用其他的方法来实现坐标转换，所以不必返回任何值；
    }

    public AffineMatrixPhenoType() {
        MyArray.setZero(affineMatrixValue);
    }

    public void calAffineMatrixValue() {
        //第1列为参数分类：1--translation,2-scale,3--shear,4--rotation;
        //The multiply process of the affine.
        double temDouble[][] = MyMatrix.multiply(getScaleDouble(), getShearDouble()[0]);
        temDouble = MyMatrix.multiply(temDouble, getShearDouble()[1]);
        temDouble = MyMatrix.multiply(temDouble, getShearDouble()[2]);
        temDouble = MyMatrix.multiply(temDouble, getRotateDouble()[0]);
        temDouble = MyMatrix.multiply(temDouble, getRotateDouble()[1]);
        temDouble = MyMatrix.multiply(temDouble, getRotateDouble()[2]);
        this.setAffineMatrixValue(temDouble);
    }

    public void calNormalAndGSUCount() {//计算需要编码的元素的下标
        //第1列为参数分类：1--translation,2-scale,3--shear,4--rotation;
        //Translation

        for (int j = 0; j < this.translation.length; j++) {
            if (this.getTranslationScope()[j].scope[1] - this.getTranslationScope()[j].scope[0] < Math.pow(2, -10)) {
            } else {
                normalIndex[index][0] = 1;
                normalIndex[index][1] = j;
                index++;
            }
        }
        //scale
        for (int j = 0; j < this.scale.length; j++) {
            if (this.getScaleScope()[j].scope[1] - this.getScaleScope()[j].scope[0] < Math.pow(2, -10)) {
            } else {
                normalIndex[index][0] = 2;
                normalIndex[index][1] = j;
                index++;
            }
        }
        //shear
        for (int j = 0; j < this.shear.length; j++) {
            if (this.getShearScope()[j].scope[1] - this.getShearScope()[j].scope[0] < Math.pow(2, -10)) {
            } else {
                normalIndex[index][0] = 3;
                normalIndex[index][1] = j;
                index++;
            }
        }
        //rotation
        for (int j = 0; j < this.rotation.length; j++) {
            if (this.getRotationScope()[j].scope[1] - this.getRotationScope()[j].scope[0] < Math.pow(2, -10)) {
            } else {
                normalIndex[index][0] = 4;
                normalIndex[index][1] = j;
                index++;
            }
        }
        //这里index对应于Controlparameters.gsucount
    }

    /**
     * @return the translation
     */
    public double[] getTranslation() {
        return translation;
    }

    /**
     * @param translation the translation to set
     */
    public void setTranslation(double[] translation1) {
        MyArray.copy(this.translation, translation1);
    }

    public void setTranslation(int j, double a) {
        this.translation[j] = a;
    }

    /**
     * @return the scale
     */
    public double[] getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(double[] scale) {
        MyArray.copy(this.scale, scale);
    }

    public void setScale(int j, double a) {
        this.scale[j] = a;
    }

    /**
     * @return the shear
     */
    public double[] getShear() {
        return shear;
    }

    /**
     * @param shear the shear to set
     */
    public void setShear(double[] shear) {
        MyArray.copy(this.shear, shear);
    }

    public void setShear(int j, double a) {
        this.shear[j] = a;
    }

    public double[] getRotation() {
        return rotation;
    }

    public void setRotation(double[] rotation) {
        if (null != rotation && rotation.length > 0) {
            MyArray.copy(this.rotation, rotation);
        }
    }

    public void setRotation(int j, double a) {
        this.rotation[j] = a;
    }

    /**
     * @return the fixed
     */
    /**
     * @return the affineMatrixValue
     */
    public double[][] getAffineMatrixValue() {
        return affineMatrixValue;
    }

    /**
     * @param affineMatrixValue the affineMatrixValue to set
     */
    public void setAffineMatrixValue(double[][] affineMatrixValue) {
        if (null != affineMatrixValue && affineMatrixValue.length > 0) {
            MyArray.copy(affineMatrixValue, this.affineMatrixValue);
        }
    }

    /**
     * @param translationScope the translationScope to set
     */
    public void setTranslationScope(NodeWithScopeElement[] translationScope) {
        if (null != translationScope && translationScope.length > 0) {
            this.translationScope = new NodeWithScopeElement[translationScope.length];
            for (int i = 0; i < translationScope.length; i++) {
                this.translationScope[i] = translationScope[i].getClone();

            }
        }
    }

    public void setScaleScope(NodeWithScopeElement[] scaleScope) {
        if (null != scaleScope && scaleScope.length > 0) {
            this.scaleScope = new NodeWithScopeElement[scaleScope.length];
            for (int i = 0; i < scaleScope.length; i++) {
                this.scaleScope[i] = scaleScope[i].getClone();
            }
        }
    }

    public void setShearScope(NodeWithScopeElement[] shearScope) {
        if (null != shearScope && shearScope.length > 0) {
            this.shearScope = new NodeWithScopeElement[shearScope.length];
            for (int i = 0; i < shearScope.length; i++) {
                this.shearScope[i] = shearScope[i].getClone();
            }
        }
    }

    public void setRotationScope(NodeWithScopeElement[] rotationScope) {
        if (null != rotationScope && rotationScope.length > 0) {
            this.rotationScope = new NodeWithScopeElement[rotationScope.length];
            for (int i = 0; i < rotationScope.length; i++) {
                this.rotationScope[i] = rotationScope[i].getClone();

            }
        }
    }

    public double[] getScope(int gusNum) {
        //第1列为参数分类：1--translation,2-scale,3--shear,4--rotation;
        //第2列存放具体的指向,如Normal[gusNum]值为{2，2}，则指向shear[2]
        double[] temFloat = new double[2];
        switch (this.normalIndex[gusNum][0]) {
            case 1:
                temFloat = getTranslationScope()[normalIndex[gusNum][1]].scope;
                break;
            case 2:
                temFloat = getScaleScope()[normalIndex[gusNum][1]].scope;
                break;
            case 3:
                temFloat = getShearScope()[normalIndex[gusNum][1]].scope;
                break;
            case 4:
                temFloat = getRotationScope()[normalIndex[gusNum][1]].scope;
                break;
        }
        return temFloat;
    }

    public void setParameters(int gusNum, double value) {
        //第1列为参数分类：1--translation,2-scale,3--shear,4--rotation;
        //第2列存放具体的指向,如Normal[gusNum]值为{3，2}，则指向shear[2]
        switch (this.normalIndex[gusNum][0]) {
            case 1:
                translation[normalIndex[gusNum][1]] = value;
                break;
            case 2:
                scale[normalIndex[gusNum][1]] = value;
                scaleDouble[normalIndex[gusNum][1]][normalIndex[gusNum][1]] = value;
                break;
            case 3:
                shear[normalIndex[gusNum][1]] = value;
                switch (normalIndex[gusNum][1]) {
                    case 0: {
                        shearDouble[0][1][0] = value;
                    }
                    break;
                    case 1: {
                        shearDouble[0][2][0] = value;
                    }
                    break;
                    case 2: {
                        shearDouble[1][0][1] = value;
                    }
                    break;
                    case 3: {
                        shearDouble[1][2][1] = value;
                    }
                    break;
                    case 4: {
                        shearDouble[2][0][2] = value;
                    }
                    break;
                    case 5: {
                        shearDouble[2][1][2] = value;
                    }
                    break;
                }
                break;
            case 4:
                rotation[normalIndex[gusNum][1]] = value;
                switch (normalIndex[gusNum][1]) {
                    case 0://roatation x
                    {
                        double cos = Math.cos(value);
                        double sin = Math.sin(value);
                        rotateDouble[0][1][1] = cos;
                        rotateDouble[0][1][2] = sin;
                        rotateDouble[0][2][1] = -sin;
                        rotateDouble[0][2][2] = cos;
                    }
                    break;
                    case 1://roatation y
                    {
                        float cos = (float) Math.cos(value);
                        float sin = (float) Math.sin(value);
                        rotateDouble[1][0][0] = cos;
                        rotateDouble[1][0][2] = -sin;
                        rotateDouble[1][2][0] = sin;
                        rotateDouble[1][2][2] = cos;
                    }
                    break;
                    case 2://roatation z
                    {
                        float cos = (float) Math.cos(value);
                        float sin = (float) Math.sin(value);
                        rotateDouble[2][0][0] = cos;
                        rotateDouble[2][0][1] = sin;
                        rotateDouble[2][1][0] = -sin;
                        rotateDouble[2][1][1] = cos;
                    }
                    break;
                }
                break;
        }
    }

    /**
     * @return the normalIndex
     */
    public double[] getParameters() {
        //第1列为参数分类：1--translation,2-scale,3--shear,4--rotation;
        //第2列存放具体的指向,如Normal[gusNum]值为{2，2}，则指向scale[2]
        double[] tem = new double[index];//ControlParameters.gsuCount
        for (int i = index - 1; i >= 0; i--) {//ControlParameters.gsuCount
            switch (this.normalIndex[i][0]) {
                case 1:
                    tem[i] = translation[normalIndex[i][1]];
                    break;
                case 2:
                    tem[i] = scale[normalIndex[i][1]];
                    break;
                case 3:
                    tem[i] = shear[normalIndex[i][1]];
                    break;
                case 4:
                    tem[i] = rotation[normalIndex[i][1]];
                    break;
            }
        }

        return tem;
    }

    public AffineMatrixPhenoType getClone() throws CloneNotSupportedException {
        AffineMatrixPhenoType myaff = new AffineMatrixPhenoType();
        for (int i = 0; i < dimension; i++) {
            myaff.translation[i] = translation[i];
        }
        for (int i = 0; i < dimension; i++) {
            myaff.translationScope[i] = new NodeWithScopeElement(getTranslationScope()[i].scope);
        }
        for (int i = 0; i < dimension; i++) {
            myaff.scale[i] = scale[i];
        }
        for (int i = 0; i < dimension; i++) {
            myaff.scaleScope[i] = new NodeWithScopeElement(getScaleScope()[i].scope);
        }
        for (int i = 0; i < dimension * (dimension - 1); i++) {
            myaff.shear[i] = shear[i];
        }
        for (int i = 0; i < dimension * (dimension - 1); i++) {
            myaff.shearScope[i] = new NodeWithScopeElement(getShearScope()[i].scope);
        }
        for (int i = 0; i < dimension; i++) {
            myaff.rotation[i] = rotation[i];
        }
        for (int i = 0; i < dimension; i++) {
            myaff.rotationScope[i] = new NodeWithScopeElement(getRotationScope()[i].scope);
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                myaff.getScaleDouble()[i][j] = getScaleDouble()[i][j];
            }
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    myaff.shearDouble[i][j][k] = getShearDouble()[i][j][k];
                }
            }
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    myaff.rotateDouble[i][j][k] = getRotateDouble()[i][j][k];
                }
            }
        }
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                myaff.affineMatrixValue[i][j] = affineMatrixValue[i][j];
            }
        }
        for (int i = 0; i < index; i++) {
            for (int j = 0; j < 2; j++) {
                myaff.normalIndex[i][j] = normalIndex[i][j];
            }
        }
        return myaff;
    }

    public NodeWithScopeElement[] getTranslationScope() {
        return translationScope;
    }

    public NodeWithScopeElement[] getScaleScope() {
        return scaleScope;
    }

    public NodeWithScopeElement[] getShearScope() {
        return shearScope;
    }

    public NodeWithScopeElement[] getRotationScope() {
        return rotationScope;
    }

    public double[][] getScaleDouble() {
        return scaleDouble;
    }

    public void setScaleDouble(double[][] scaleDouble) {
        if (null != scaleDouble && scaleDouble.length > 0) {
            this.scaleDouble = new double[scaleDouble.length][scaleDouble[0].length];
            for (int i = 0; i < scaleDouble.length; i++) {
                System.arraycopy(scaleDouble[i], 0, this.scaleDouble[i], 0, scaleDouble[i].length);
            }

        }
    }

    public double[][][] getShearDouble() {
        return shearDouble;
    }

    public double[][][] getRotateDouble() {
        return rotateDouble;
    }
}
