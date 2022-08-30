package core.tools.Graph.Framework;

import javax.media.j3d.Appearance;
import javax.media.j3d.LineArray;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;

/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class CoordinateAxis extends Shape3D {

    private float[] axis = {
        0, 0, 0, 1, 0, 0,
        0, 0, 0, 0, 1, 0,
        0, 0, 0, 0, 0, 1,
        0, 1, 0, 0, 1, 1,
        0, 1, 0, 1, 1, 0,
        1, 1, 1, 1, 1, 0,
        1, 1, 1, 0, 1, 1,
        1, 1, 0, 1, 0, 0,
        1, 1, 1, 1, 0, 1,
        1, 0, 0, 1, 0, 1,
        1, 0, 1, 0, 0, 1,
        0, 0, 1, 0, 1, 1,};
    private float[] color = {
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1,};

    public CoordinateAxis() {
        LineArray axisLine = new LineArray(24, LineArray.COORDINATES | LineArray.COLOR_3);
        axisLine.setCoordinates(0, axis);
        axisLine.setColors(0, color);
        LineAttributes lineAtt = new LineAttributes();
        lineAtt.setLineWidth(10);
        lineAtt.setLineAntialiasingEnable(true);
        PolygonAttributes polygona = new PolygonAttributes();
        polygona.setCullFace(PolygonAttributes.CULL_NONE);
        Appearance app = new Appearance();
        app.setPolygonAttributes(polygona);
        app.setLineAttributes(lineAtt);
        this.setAppearance(app);
        this.setGeometry(axisLine);

    }
}
