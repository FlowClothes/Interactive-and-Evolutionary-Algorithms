package core.problem.IGA;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class BaseIGAPhenoType {

    protected float[] phenoValue;
    protected float[] genoValue;
    protected String[] genoStr;

    public float[] getPhenoValue(float[] x) {
        return phenoValue;
    }

    public void setPhenoValue(float[] phenoValue) {
        if (null != phenoValue && phenoValue.length > 0) {
            this.phenoValue = new float[phenoValue.length];
            System.arraycopy(phenoValue, 0, this.phenoValue, 0, phenoValue.length);
        }
    }

    public float[] getGenoValue() {
        return genoValue;
    }

    public void setGenoValue(float[] genoValue) {
        if (null != genoValue && genoValue.length > 0) {
            this.genoValue = new float[genoValue.length];
            System.arraycopy(genoValue, 0, this.genoValue, 0, genoValue.length);
        }
    }

    public String[] getGenoStr() {
        return genoStr;
    }

    public void setGenoStr(String[] genoStr) {
        this.genoStr = genoStr;
    }
}
