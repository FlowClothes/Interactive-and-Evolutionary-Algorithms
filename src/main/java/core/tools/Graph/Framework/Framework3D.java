package core.tools.Graph.Framework;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class Framework3D extends JPanel {

    protected Canvas3D c;
    protected SimpleUniverse u;
    protected BranchGroup objRoot;
    protected String textureFilename = null;
    protected Shape3D shape3d = null;
    protected Appearance app;
    protected TransformGroup TGA;
    protected BoundingSphere bounds;

    public void createSceneGraph() {
        this.createOtherScene();
//====================================BEGIN=======What you have to do is just these codes between the double lines===================
        if (this.textureFilename == null) {
        } else {
            TextureLoader mytexLoader = new TextureLoader(textureFilename, this);
            Texture2D myTex = (Texture2D) mytexLoader.getTexture();
            app.setTexture(myTex);
        }
        //===================================================THE END what you have to change===============================

        this.behavior();
        objRoot.addChild(TGA);
        objRoot.compile();
    }

    public void repaintme() {
        this.u.cleanup();
        this.u.removeAllLocales();
        createSceneGraph();
        this.u = new SimpleUniverse(c);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(objRoot);
    }
//paintme之前，甭忘了调用setMyShape3d(Shape3D sh) 

    public void paintme() {
        setLayout(new BorderLayout());
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();
        this.c = new Canvas3D(config);
        this.add("Center", c);
        createSceneGraph();
        this.u = new SimpleUniverse(c);
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(objRoot);
    }

    public void setTextureFilename(String textureFilename) {
        this.textureFilename = textureFilename;
    }

    public void setMyShape3d(Shape3D sh) {
        this.shape3d = sh;
    }

    private void createOtherScene() {//与外部程序无关紧要的一些内容
        objRoot = new BranchGroup();
        bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f bgColor = new Color3f(1.0f, 1.0f, 1.0f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        objRoot.addChild(bg);

        Color3f DirectionalColor = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f vec = new Vector3f(0.f, 0.f, 1.0f);
        DirectionalLight directionalLight = new DirectionalLight(DirectionalColor, vec);
        directionalLight.setInfluencingBounds(bounds);
        objRoot.addChild(directionalLight);


        app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(1.0f, 1.0f, 1.0f));
        app.setMaterial(mat);

        Transform3D transA = new Transform3D();
        transA.setTranslation(new Vector3d(0, 0, 0));

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureTransform(transA);
        app.setTextureAttributes(texAttr);

        TGA = new TransformGroup(transA);
        TGA.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TGA.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        PolygonAttributes polygona = new PolygonAttributes();
        polygona.setCullFace(PolygonAttributes.CULL_NONE);
        app.setPolygonAttributes(polygona);
        shape3d.setAppearance(app);
        TGA.addChild(shape3d);

    }

    private void behavior() {//响应鼠标的转动
        MouseRotate behavior = new MouseRotate();
        behavior.setTransformGroup(TGA);
        objRoot.addChild(behavior);
        behavior.setSchedulingBounds(bounds);

        MouseZoom behavior2 = new MouseZoom();
        behavior2.setTransformGroup(TGA);
        objRoot.addChild(behavior2);
        behavior2.setSchedulingBounds(bounds);

        MouseTranslate behavior3 = new MouseTranslate();
        behavior3.setTransformGroup(TGA);
        objRoot.addChild(behavior3);
        behavior3.setSchedulingBounds(bounds);
    }
}
