package core.problem.IGA.Graph.Face.Base;

import core.controllers.ControlParameters;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class FaceControlParameters {

    public static String textureFilename = ControlParameters.resource+"img/512.jpg";//帖图名称
    public static int AllVertexCount = 65;//顶点个数
    public static int Candide3VertextCount = 51;//人脸上的代表顶点，假设人脸是对称的，只用了一半和中线的点
    public static int FloatCount = 77;//这个数是总结了人脸的51个特征点的坐标的相互关系后,从53*3=159个浮点数中得到的代表性的浮点数，即把相等的点也考虑后，剩下的浮点的个数
    public static int part = 0;//0-头型，1-脸颊，2-下巴，3-眼，4-鼻，5-眉毛，6-口，7-整个人脸
        //设置人脸各部分的相关内容

    public static float AffineKowledgeDelter=0.001f;
    public static int dimension=3;
    public static int fixed=0;
    public static String[] faceName=new String[]{"SourceFace", "baby","lyj","GeneralFace"};
    public static String whichFace=faceName[0];
}