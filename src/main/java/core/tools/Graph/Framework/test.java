package core.tools.Graph.Framework;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.GraphicsConfiguration;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class test extends javax.swing.JFrame {

    private javax.swing.JPanel drawingPanel;
    private SimpleUniverse univ = null;
    private BranchGroup scene = null;
    private BranchGroup objRoot;
    private TransformGroup objTrans;
    private BoundingSphere bounds;

    public BranchGroup createSceneGraph() {

        objRoot = new BranchGroup();
        objTrans = new TransformGroup();
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(objTrans);
        Color3f bgColor = new Color3f(1.0f, 1.0f, 1.0f);
        Background bg = new Background(bgColor);
        bg.setApplicationBounds(bounds);
        objRoot.addChild(bg);
        Color3f DirectionalColor = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f vec = new Vector3f(0.f, 0.f, 1.0f);
        DirectionalLight directionalLight = new DirectionalLight(DirectionalColor, vec);
        directionalLight.setInfluencingBounds(bounds);
        objRoot.addChild(directionalLight);
        Appearance app = new Appearance();
        Material mat = new Material();
        mat.setDiffuseColor(new Color3f(1.0f, 1.0f, 1.0f));
        app.setMaterial(mat);
//===================================================================
        objTrans.addChild(new CoordinateAxis());
//===========================================================
        bounds =new BoundingSphere(new Point3d(0, 0, 0.0), 100.0);
        objRoot.setBounds(bounds);
        this.behavior();
        objRoot.compile();
        return objRoot;
    }

    private Canvas3D createUniverse() {
        // Get the preferred graphics configuration for the default screen
        GraphicsConfiguration config =
                SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(config);
        univ = new SimpleUniverse(c);
        univ.getViewingPlatform().setNominalViewingTransform();
        univ.getViewer().getView().setMinimumFrameCycleTime(5);
        return c;
    }

    public test() {
        initComponents();
        Canvas3D c = createUniverse();
        drawingPanel.add(c, java.awt.BorderLayout.CENTER);
        scene = createSceneGraph();
        univ.addBranchGraph(scene);
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
    private void initComponents() {
        drawingPanel = new javax.swing.JPanel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("test");
        drawingPanel.setLayout(new java.awt.BorderLayout());
        drawingPanel.setPreferredSize(new java.awt.Dimension(250, 250));
        getContentPane().add(drawingPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>
//本来是注掉的@#￥%……&*（）！@#￥%
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new test().setVisible(true);
            }
        });
    }
 //本来是注掉的@#￥%……&*（）！@#￥%
    private void behavior() {//响应鼠标的转动
        MouseRotate behavior = new MouseRotate();
        behavior.setTransformGroup(objTrans);
        objRoot.addChild(behavior);
        behavior.setSchedulingBounds(bounds);

        MouseZoom behavior2 = new MouseZoom();
        behavior2.setTransformGroup(objTrans);
        objRoot.addChild(behavior2);
        behavior2.setSchedulingBounds(bounds);

        MouseTranslate behavior3 = new MouseTranslate();
        behavior3.setTransformGroup(objTrans);
        objRoot.addChild(behavior3);
        behavior3.setSchedulingBounds(bounds);
    }
}
