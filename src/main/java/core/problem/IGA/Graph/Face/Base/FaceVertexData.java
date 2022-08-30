package core.problem.IGA.Graph.Face.Base;

import core.controllers.ControlParameters;
import core.problem.FactoryProblems;
import core.tools.MyDataStructure.MyArray;
import core.tools.MyDataStructure.Node.NodeWithScopeElement;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 * @author 姜艳 Jia Yan
 */
public class FaceVertexData {

    public static GraphPoint[] gp = new GraphPoint[FaceControlParameters.Candide3VertextCount];
    public static FacePart[] facePart = new FacePart[8];//人脸主要由7个部分组成,再加上轮廓共8个
    public static double faceCoordinate[] = {
        0, 0.52, -0.32,//0
        0, 0.24, -0.04,
        0, 0.1102, -0.0235,
        0, 0, 0,
        0, -0.1510, 0.1029,
        0, -0.1755, -0.0117,
        //   0, -0.2568, 0,
        0, -0.255, 0,
        0, -0.2776, -0.0147,
        0, -0.3102, 0.0088,
        0, -0.3469, -0.0294,
        0, -0.48, -0.07,//10
        0.11, 0.51, -0.44,
        0.24, 0.44, -0.41,
        0.09, 0.39, -0.14,
        0.22, 0.29, -0.21,
        0.33, 0.24, -0.41,
        0.20, 0.16, -0.06,
        0.20, 0.125, -0.07,
        0.27, 0.1136, -0.22,
        0.07, 0.1136, -0.02,
        0.163, 0.0852, -0.1235,//20
        0.0122, 0.07386, -0.0676,
        0.204, 0.063, -0.17,
        0.163, 0.063, -0.1235,
        0.114, 0.063, -0.132,
        0.204, 0.0612, -0.182,
        0.1143, 0.0612, -0.132,
        0.069, 0.0449, -0.17,
        0.33, 0.04, -0.41,
        0.249, 0.0449, -0.2147,
        0.204, 0.0286, -0.17,//30
        0.114, 0.0286, -0.13,
        0.204, 0.0245, -0.182,
        0.163, 0.0245, -0.1236,
        0.1143, 0.0245, -0.13,
        0.1633, 0.016, -0.17,
        0.0327, -0.0204, -0.0765,
        0.20, -0.04, -0.16,
        0.28, -0.12, -0.3,
        0.0612, -0.1143, -0.0912,
        0.0245, -0.13878, 0.0824,//40
        0.0939, -0.1633, -0.0941,
        0.19, -0.23, -0.22,
        0.0204, -0.2450, 0.0088,
        0.0653, -0.2531, -0.0706,
        0.28, -0.28, -0.41,
        0.1347, -0.2776, -0.1471,
        0.1020, -0.2776, -0.15,
        0.0531, -0.2776, -0.0824,
        0.0653, -0.3, -0.07059,
        0.10, -0.46, -0.11,//50，从0开始共51个点
    };//存放脸上特征点的坐标

    public static double[] getVert(int i) {
        return new double[]{faceCoordinate[i * 3], faceCoordinate[i * 3 + 1], faceCoordinate[i * 3 + 2]};
    }
    public static int faceEqualVertNum[][] = {//下面的数组faceVertMuteScope对这些坐标相等的点的变化范围进行了指定，而commonCoordinatesOfFacePointRepresentative[]则负责指定在GA中仅对相应的代表点进行编码
        //什么时候被用到，如何用？
        // 存放脸上坐标轴相等的点的编号，而这些点的编号实质上是对应于faceCoordinate的地址索引;

        //下面首先是特征点x轴的聚类集合
        {21},//0
        {40, 43},
        {36},
        {48},
        {39},
        {44, 49},
        {19, 27},
        {13},
        {41, 50},
        {47},
        {11},//10
        {24, 26, 31, 34},
        {46},
        {20, 23, 33, 35},
        {42},
        {16, 17, 22, 25, 30, 32, 37},
        {14},
        {12},
        {29},
        {18, 38, 45},
        {28},//20
        {15}, //特征点y轴的聚类集合///y正方向的
        {35},
        {32, 34},
        {30, 31, 33},
        {27, 28, 29},
        {25, 26},
        {22, 23, 24},
        {21},
        {20},
        {2, 18, 19},//30
        {17},
        {16},
        {1, 15},
        {14},
        {13},
        {12},
        {11},
        {0}, //y负方向的
        {36},
        {37},//40
        {38, 39},
        {40},
        {4},
        {41},
        {5},
        {42},
        {43},
        {6, 44},
        {7, 45, 46, 47, 48},
        {49},//50
        {8},
        {9},
        {50},
        {10}, //特征点z轴的聚类集合///z正方向的
        {8, 43},
        {40},
        {4}, //z负方向的
        {7, 5},
        {2, 9, 19},
        {1},//60
        {16, 17, 21},
        {10, 44, 49},
        {36, 48},
        {39, 41},
        {50},
        {13, 20, 23, 33, 35},
        {24, 26, 31, 34},
        {27, 46, 47},
        {37},
        {22, 30},//70
        {25, 32},
        {14, 18, 29, 42},
        {38},
        {0},
        {12, 15, 28, 45},
        {11}};//76，从0开始，共77个坐标
    public static double faceVertMuteScope[][] = {//与faceEqualVertNum和commonCoordinatesOfFacePointRepresentative数组一一对应
        //存放脸上坐标轴相等的点的编号对应点的坐标的变化范围，如果是一个值，则意指在标准坐标值上直接加该值；如果是两个值，则是指定了其变化范围的上限和下限
        {0.055},//0
        {0.109},
        {0.036},
        {0.091},
        {0.036},
        {0.036},
        {0.036},
        {0.091},
        {0.027},
        {0.055},
        {0.036},//10
        {0.036},
        {0.073},
        {0.182},
        {0.155},
        {0.100},
        {0.127},
        {0.064},
        {0.036},
        {0.182},
        {0.255},//20
        {0.073},
        {0.053},
        {0.027},
        {0.016},
        {0.027},
        {0.053},
        {0.021},
        {0.021},
        {0.016},
        {0.106},//30
        {0.085},
        {0.106},
        {0.532, 0.426},
        {0.053},
        {0.798, 0.691},
        {0.106},
        {0.106},
        {0.053},
        {-0.084},
        {-0.056, -0.1685},//40
        {-0.056},
        {-0.090},
        {-0.022},
        {-0.056},
        {-0.034},
        {-0.225},
        {-0.067},
        {-0.084},
        {-0.112},
        {-0.079},//50
        {-0.034},
        {-0.674, -0.787},
        {-0.899, -0.955},
        {0.112},
        {0.130},
        {0.370},
        {-0.130},
        {-0.044},
        {-0.073},
        {-0.058},//60
        {-0.022},
        {-0.022},
        {-0.022},
        {-0.058},
        {-0.073},
        {-0.029},
        {-0.073},
        {-0.073},
        {-0.073},
        {-0.036},//70
        {-0.073},
        {-0.175},
        {-0.657, -0.8},
        {-0.073},
        {-0.949, -1.09},
        {-0.073}//76,从0开始，共77个点
    };
    public static int faceVertRelation[][] = {//人脸上的三角片关系
        {0, 1, 13},
        {0, 11, 13},
        {0, 11, 51},
        {0, 51, 52},
        {1, 2, 19},//15个
        {1, 13, 14},
        {2, 3, 21},
        {1, 14, 16},
        {1, 16, 19},
        {2, 19, 21},
        {3, 4, 40},
        {3, 21, 36},
        {3, 36, 40},
        {4, 5, 40},
        {5, 6, 43},
        {5, 40, 41},
        {5, 41, 43},
        {6, 7, 48},
        {6, 43, 44},
        {6, 44, 48},
        {7, 8, 48},
        {8, 9, 49},
        {8, 48, 49},
        {9, 10, 50},
        {9, 49, 50},
        {10, 45, 50},
        {10, 50, 63},
        {11, 12, 13},
        {11, 12, 51},
        {12, 13, 14},
        {12, 14, 15},
        {12, 15, 51},
        {14, 15, 18},
        {14, 16, 18},
        {15, 18, 28},
        {15, 28, 56},
        {15, 51, 53},
        {15, 28, 56},
        {15, 53, 56},
        {16, 17, 18},
        {16, 17, 19},
        {17, 18, 20},
        {17, 19, 20},
        {18, 20, 22},
        {18, 22, 29},
        {18, 28, 29},
        {19, 20, 24},
        {19, 21, 27},
        {19, 24, 27},
        {20, 22, 23},
        {20, 23, 24},
        {21, 27, 36},
        {22, 23, 25},
        {22, 25, 29},
        {23, 24, 26},
        {23, 25, 30},
        {23, 26, 31},
        {23, 30, 33},
        {23, 31, 33},
        {24, 26, 27},
        {25, 29, 30},
        {26, 27, 31},
        {27, 31, 34},
        {27, 34, 39},
        {27, 36, 39},
        {28, 29, 37},
        {28, 37, 38},
        {28, 38, 45},
        {28, 45, 61},
        {28, 55, 58},
        {28, 56, 61},
        {29, 30, 32},
        {29, 32, 37},
        {30, 32, 33},
        {31, 33, 34},
        {32, 33, 35},
        {32, 35, 37},
        {33, 34, 35},
        {34, 35, 39},
        {35, 37, 41},
        {35, 39, 41},
        {36, 39, 40},
        {37, 38, 42},
        {37, 41, 42},
        {38, 42, 45},
        {39, 40, 41},
        {41, 42, 46},
        {41, 43, 44},
        {41, 44, 46},
        {42, 45, 46},
        {44, 46, 47},
        {44, 47, 48},
        {45, 46, 50},
        {45, 50, 64},
        {45, 60, 61},
        {45, 60, 64},
        {46, 47, 49},
        {46, 49, 50},
        {47, 48, 49},
        {50, 63, 64},
        {51, 52, 54},
        {51, 53, 54},
        {53, 54, 55},
        {53, 55, 56},
        {54, 55, 57},
        {55, 57, 58},
        {55, 56, 61},
        {55, 58, 61},
        {57, 58, 60},
        {57, 59, 60},
        {58, 60, 61},
        {59, 60, 62},
        {60, 62, 64},
        {62, 63, 64},};
    public static double vertCoordinateBack[] = {//非Candide-3标准的人头后脑勺的点的坐标值
        0.075, 0.4, -0.6,//0
        0, 0.4, -0.65,
        0.1875, 0.317499985, -0.6,
        0, 0.2775, -0.75,
        0.16749999, 0.19750001, -0.7,
        0.225, 0.15, -0.65,
        0, 0.12, -0.775,
        0.13, 0.0675, -0.75,
        0, 0.05, -0.75,
        0.105, -0.205, -0.65,
        0.225, -0.0575, -0.6,//10
        0, -0.205, -0.65,
        0, -0.342500025, -0.5,
        0.075, -0.3275, -0.5,};//13，从0开始，共14个点
    public static double commonCoordinatesOfFacePointRepresentative[] = {
        // 与faceEqualVertNum和faceVertMuteScope数组一一对应
        //常规基因编码数组，如果只对人脸的部分进行优化，则可以直接从本数组中取出不改变的部分，而只对相应的其它值进行优化。
        //当人脸的某一部分优化结束后，优化的结果也可以保存在这里，从而在优化其他部分时，可以直接在此基础上进行。
        0.015,//0
        0.04,
        0.055,
        0.085,
        0.1,
        0.105,
        0.115,
        0.15,
        0.15,
        0.17,
        0.185,//10
        0.185,
        0.21,
        0.26,
        0.31,
        0.335,
        0.375,
        0.39,
        0.405,
        0.45,
        0.56,
        0.545,//21
        0.035,
        0.05,
        0.06,
        0.08,
        0.115,
        0.13,
        0.14,
        0.15,
        0.2,//30
        0.245,
        0.295,
        0.455,
        0.53,
        0.7,
        0.8,
        0.925,//37
        0.95,
        0.065,
        -0.075,
        -0.215,//40
        -0.26,
        -0.275,
        -0.3,
        -0.32,
        -0.42,
        -0.45,
        -0.465,
        -0.51,
        -0.555,
        -0.57,//50
        -0.63,
        -0.855,
        -0.885,
        0.015,
        0.185,
        0.215,
        -0.02,
        -0.04,
        -0.07,
        -0.11,//60
        -0.12,
        -0.13,
        -0.155,
        -0.185,
        -0.23,
        -0.19,
        -0.375,
        -0.275,
        -0.19,//70
        -0.2,
        -0.37,
        -0.51,
        -0.75,
        -0.7,
        -0.76,};//76，从0开始，共77个

    public static void init() {
        for (int i = 0; i < facePart.length; i++) {
            facePart[i] = new FacePart();
        }
        //设置人脸各部分的相关内容，共51个点
        facePart[0].setVertNum(new int[]{0, 1, 11, 12, 13, 14, 15});//头型
        facePart[1].setVertNum(new int[]{28, 37, 38, 42});//脸颊
        facePart[2].setVertNum(new int[]{10, 45, 50});//下巴
        facePart[3].setVertNum(new int[]{20, 22, 23, 24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35});//眼
        facePart[4].setVertNum(new int[]{2, 3, 4, 5, 21, 36, 39, 40, 41});//鼻
        facePart[5].setVertNum(new int[]{16, 17, 18, 19});//眉毛
        facePart[6].setVertNum(new int[]{6, 7, 8, 9, 43, 44, 46, 47, 48, 49});//口
        facePart[7].setVertNum(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50});
        if (FactoryProblems.problemNum !=901) {//详见FactoryProblems
            //首先设置各结点的编号及坐标
            for (int i = 0; i < gp.length; i++) {
                gp[i] = new GraphPoint();
                gp[i].setPointNum(i);
                gp[i].setPointCoordinates(new double[]{faceCoordinate[i * 3], faceCoordinate[i * 3 + 1], faceCoordinate[i * 3 + 2]});
            }
            //下面设置相应人脸部分具有相同坐标值的点的编号（是否有再设置一次的必要？因为前面已经在数组faceEqualVertNum中已经给出了！虽然前面没有按照人脸各部分进行设置）
            facePart[0].setZEqualPoints(new int[][]{{12, 15}});
            facePart[3].setXEqualPoints(new int[][]{{24, 26, 31, 34}, {20, 23, 33, 35}, {22, 25, 30, 32}});
            facePart[3].setYEqualPoints(new int[][]{{32, 34}, {30, 31}, {25, 26}, {22, 24}});
            facePart[3].setZEqualPoints(new int[][]{{22, 25, 30, 32}, {24, 26, 31, 34}, {23, 33}});
            facePart[4].setXEqualPoints(new int[][]{{2, 3, 4, 5}});
            facePart[5].setXEqualPoints(new int[][]{{16, 17}});
            facePart[6].setXEqualPoints(new int[][]{{6, 7, 8, 9}});
            facePart[6].setYEqualPoints(new int[][]{{7, 46, 47, 48}, {6, 44}});
            facePart[7].setZEqualPoints(new int[][]{{12, 15}});
            facePart[7].setXEqualPoints(new int[][]{{2, 3, 4, 5}, {16, 17}, {6, 7, 8, 9}, {24, 26, 31, 34}, {20, 23, 33, 35}, {22, 25, 30, 32}, {22, 25, 30, 32}, {24, 26, 31, 34}, {23, 33}});
            facePart[7].setYEqualPoints(new int[][]{{32, 34}, {30, 31}, {25, 26}, {22, 24}, {7, 46, 47, 48}, {6, 44}});

            //设置各结点的前驱及后继及变化区间,有些节点没有前驱，有些节点没有后继；
            {
                double base, up;
                //鼻子x
                gp[2].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[2]}, new double[]{0, 0});
                //这句话的意思是：设置第2个点的前驱结点是其本身，即没有前驱结点，变异的范围的是在(前驱结点的坐标值+0，前驱结点的坐标值+0.0204)
                gp[3].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[2]}, new double[]{0, 0});
                gp[4].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[2]}, new double[]{0, 0});
                gp[5].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[2]}, new double[]{0, 0});
                gp[21].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[2]}, new double[]{0, 0.0204});
                gp[40].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[2]}, new double[]{0, 0.0286});
                gp[36].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[2]}, new double[]{0, 0.0490});
                gp[39].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[21], gp[40], gp[36]}, new double[]{0, 0.0612});
                gp[41].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[39]}, new double[]{0, 0.0449});
                base = gp[2].getcoordValue('x');
                up = gp[36].getcoordValue('x');
                gp[40].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[2]}, new double[]{(up - base) / 3, (up - base) * 9 / 10});
                //鼻子y
                gp[5].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[43]}, new double[]{0.0608, 0.0816});
                gp[41].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[43]}, new double[]{0.0408, 0.0824});
                gp[4].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[5]}, new double[]{0, 0.0408});
                gp[39].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[5], gp[41]}, new double[]{0, 0.0612});
                gp[40].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[41]}, new double[]{0, 0.0408});
                gp[3].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[4], gp[39]}, new double[]{0, 0.1633});
                gp[36].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[39], gp[40]}, new double[]{0, 0.1224});
                gp[21].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[3], gp[36]}, new double[]{0, 0.0816});
                gp[2].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[21]}, new double[]{0, 0.0408});
                //鼻子z
                gp[41].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[29]}, new double[]{0, 0.147058824});
                gp[21].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[41]}, new double[]{0, 0.0294});
                gp[36].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[41]}, new double[]{0, 0.0176});
                gp[39].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[41]}, new double[]{0, 0.0059});
                gp[2].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[21], gp[36]}, new double[]{0, 0.0441});
                gp[5].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[39], gp[36]}, new double[]{0, 0.05});
                gp[3].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[2], gp[5]}, new double[]{0, 0.0059});
                gp[40].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[3]}, new double[]{0, 0.0882});
                gp[4].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[40]}, new double[]{0, 0.0147});
                //脸颊x
                gp[38].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[42], gp[37]}, new double[]{0, 0.10});
                gp[28].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[38]}, new double[]{0.085, 0.10});
                base = gp[41].getcoordValue('x');
                up = gp[38].getcoordValue('x');
                gp[37].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[41]}, new double[]{((up - base) * 3) / 5, ((up - base) * 4) / 5});
                gp[42].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[41]}, new double[]{((up - base) * 3) / 5, ((up - base) * 4) / 5});

                //脸颊y
                gp[42].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[43]}, new double[]{0.04, 0.0816});
                gp[38].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[42]}, new double[]{0, 0.12});
                gp[37].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[38]}, new double[]{0, 0.18});
                gp[28].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[37]}, new double[]{0, 0.18});
                //脸颊z
                gp[38].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[28]}, new double[]{0, 0.12});
                gp[42].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[38]}, new double[]{0, 0.09});
                gp[37].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[42]}, new double[]{0, 0.04});
                gp[28].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[11]}, new double[]{0, 0.03});
                //眉毛x
                gp[19].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[21]}, new double[]{0.0204, 0.0612});
                gp[16].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[19]}, new double[]{0.1, 0.18});
                gp[17].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[19]}, new double[]{0.1, 0.18});
                gp[18].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[16], gp[17]}, new double[]{0.01, 0.09});
                //眉毛y
                gp[18].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[20]}, new double[]{0.015, 0.041});
                gp[19].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[20]}, new double[]{0.015, 0.041});
                gp[17].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[18], gp[19]}, new double[]{0.01, 0.04});
                gp[16].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[17]}, new double[]{0.01, 0.04});
                //眉毛z
                gp[18].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[28]}, new double[]{0.191176471, 0.220588235});
                gp[16].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[18]}, new double[]{0, 0.15});
                gp[17].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[18]}, new double[]{0, 0.15});
                gp[19].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[16], gp[17]}, new double[]{0, 0.04});
                //头型x
                gp[0].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[0], gp[0]}, new double[]{0, 0});//因为在中轴线上，其x值其实一直为0
                gp[1].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[1], gp[1]}, new double[]{0, 0});//因为在中轴线上，其x值其实一直为0
                gp[11].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[0], gp[1]}, new double[]{0.16, 0.21});///{gp[0], gp[1]}, new float[]{0, 0.12});
                gp[13].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[0], gp[1]}, new double[]{0, 0.10});
                gp[12].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[11]}, new double[]{0.14, 0.2});///{gp[11], gp[13]}, new float[]{0, 0.14});
                gp[14].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[11], gp[13]}, new double[]{0, 0.13});
                gp[15].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[12]}, new double[]{0.08, 0.14});///gp[12], gp[14]}, new float[]{0, 0.12});

                //头型y
                base = gp[2].getcoordValue('y');
                up = gp[0].getcoordValue('y');
                gp[1].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[2]}, new double[]{(up - base) / 3, ((up - base) * 2) / 3});
                gp[13].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[1], gp[15], gp[14]}, new double[]{0, 0.16});
                //       gp[12].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[1], gp[15], gp[14]}, new float[]{0, 0.20});
                // gp[11].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[13], gp[12]}, new float[]{0, 0.12});
                gp[11].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[0]}, new double[]{-0.03, -0.025});
                gp[12].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[11]}, new double[]{-0.135, -0.12});
                gp[0].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[11]}, new double[]{0, 0.03});
                gp[14].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[16]}, new double[]{(up - base) / 3, ((up - base) * 2) / 3});
                //         gp[15].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[16]}, new float[]{((up - base) * 2) / 5, base + ((up - base) * 4) / 5});
                gp[15].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[16]}, new double[]{0.005, 0.01});
                //头型z
                gp[12].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[11]}, new double[]{0, 0.03});
                gp[15].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[11]}, new double[]{0, 0.03});
                gp[0].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[12], gp[15]}, new double[]{0, 0.09});
                gp[14].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[0]}, new double[]{0, 0.12});
                gp[13].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[14]}, new double[]{0, 0.09});
                gp[1].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[13]}, new double[]{0, 0.09});
                base = gp[12].getcoordValue('z');
                up = gp[0].getcoordValue('z');
                gp[11].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[12]}, new double[]{((up - base) * 3) / 5, ((up - base) * 4) / 5});
                //下巴x
                gp[10].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[10]}, new double[]{0, 0});
                gp[50].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[10]}, new double[]{0.15, 0.25});
                //  gp[45].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[50]}, new double[]{0.105, 0.20});
                gp[45].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[50]}, new double[]{0.20, 0.24});
                //下巴y
                gp[50].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[10]}, new double[]{0.02, 0.05});
                //  gp[50].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[10]}, new double[]{0.01, 0.10});
                gp[45].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[50]}, new double[]{0.24, 0.30});
                //  gp[50].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[49]}, new double[]{-0.3,-0.2});
                gp[10].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[50]}, new double[]{-0.02, 0});

                //下巴z
                gp[50].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[45]}, new double[]{0.2, 0.31});
                gp[10].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[50]}, new double[]{0.02, 0.12});
                gp[45].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[11]}, new double[]{0, 0.03});
                //眼睛x
                gp[24].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[27]}, new double[]{0, 0.0898});
                gp[26].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[27]}, new double[]{0, 0.0898});
                gp[27].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[21]}, new double[]{0.025, 0.0612});
                gp[31].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[27]}, new double[]{0, 0.0898});
                gp[34].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[27]}, new double[]{0, 0.0898});
                gp[20].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[24], gp[26], gp[31], gp[34]}, new double[]{0, 0.067347});
                gp[23].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[24], gp[26], gp[31], gp[34]}, new double[]{0, 0.067347});
                gp[33].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[24], gp[26], gp[31], gp[34]}, new double[]{0, 0.067347});
                gp[35].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[24], gp[26], gp[31], gp[34]}, new double[]{0, 0.067347});
                gp[22].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[20], gp[23], gp[33], gp[35]}, new double[]{0, 0.067347});
                gp[25].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[20], gp[23], gp[33], gp[35]}, new double[]{0, 0.067347});
                gp[30].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[20], gp[23], gp[33], gp[35]}, new double[]{0, 0.067347});
                gp[32].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[20], gp[23], gp[33], gp[35]}, new double[]{0, 0.067347});
                gp[29].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[22], gp[25], gp[30], gp[32]}, new double[]{0.05, 0.089796});
                //眼睛y
                gp[35].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[37]}, new double[]{0.04081633, 0.081633});
                gp[33].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[35]}, new double[]{0, 0.008163});
                gp[32].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[35]}, new double[]{0, 0.008163});
                gp[34].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[35]}, new double[]{0, 0.008163});
                gp[30].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[33], gp[32], gp[34]}, new double[]{0, 0.004081});
                gp[31].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[33], gp[32], gp[34]}, new double[]{0, 0.004081});
                gp[27].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[30], gp[31]}, new double[]{0, 0.028571});
                gp[29].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[30], gp[31]}, new double[]{0.005, 0.025});
                gp[25].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[27], gp[29]}, new double[]{0, 0.028571});
                gp[26].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[27], gp[29]}, new double[]{0, 0.028571});
                gp[22].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[25], gp[26]}, new double[]{0, 0.004082});
                gp[24].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[25], gp[26]}, new double[]{0, 0.004082});
                gp[23].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[25], gp[26]}, new double[]{0, 0.004082});
                gp[20].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[22], gp[24], gp[23]}, new double[]{0, 0.008163});
                //眼睛z
                gp[22].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[29]}, new double[]{0, 0.058824});
                gp[25].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[29]}, new double[]{0, 0.058824});
                gp[30].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[29]}, new double[]{0, 0.058824});
                gp[32].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[29]}, new double[]{0, 0.058824});
                gp[27].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[22], gp[25], gp[30], gp[32]}, new double[]{0, 0.029412});
                gp[35].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[22], gp[25], gp[30], gp[32]}, new double[]{0, 0.029412});
                gp[24].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[27], gp[35]}, new double[]{0, 0.011765});
                gp[26].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[27], gp[35]}, new double[]{0, 0.011765});
                gp[31].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[27], gp[35]}, new double[]{0, 0.011765});
                gp[34].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[27], gp[35]}, new double[]{0, 0.011765});
                gp[20].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[24], gp[26], gp[31], gp[34]}, new double[]{0, 0.011765});
                gp[23].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[24], gp[26], gp[31], gp[34]}, new double[]{0, 0.011765});
                gp[33].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[24], gp[26], gp[31], gp[34]}, new double[]{0, 0.011765});
                base = gp[28].getcoordValue('z');
                up = gp[25].getcoordValue('z');
                gp[29].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[28]}, new double[]{((up - base) * 9.5) / 11, ((up - base) * 10.5) / 11});

                //嘴巴x
                gp[6].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6]}, new double[]{0, 0});
                gp[7].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6]}, new double[]{0, 0});
                gp[8].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6]}, new double[]{0, 0});
                gp[9].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6]}, new double[]{0, 0});
                gp[43].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6], gp[7], gp[8], gp[9]}, new double[]{0, 0.0204});
                gp[44].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[43]}, new double[]{0, 0.0408});
                gp[48].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[43]}, new double[]{0, 0.0327});
                gp[49].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[43]}, new double[]{0, 0.0408});
                gp[47].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[44], gp[48], gp[49]}, new double[]{0, 0.0408});
                gp[46].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[47]}, new double[]{0, 0.0286});
                //嘴巴y
                gp[9].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[10]}, new double[]{0.12245, 0.14286});
                gp[8].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[9]}, new double[]{0, 0.0408});
                gp[49].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[8]}, new double[]{0, 0.0204});
                gp[7].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[49]}, new double[]{0, 0.049});
                gp[46].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[49]}, new double[]{0, 0.049});
                gp[47].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[49]}, new double[]{0, 0.049});
                gp[48].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[49]}, new double[]{0, 0.049});
                gp[6].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[7], gp[46], gp[47], gp[48]}, new double[]{0, 0.0327});
                gp[44].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[7], gp[46], gp[47], gp[48]}, new double[]{0, 0.0327});
                gp[43].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[6], gp[44]}, new double[]{0, 0.0122});
                //嘴巴z
                gp[46].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[29]}, new double[]{0, 0.117647059});
                gp[47].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[46]}, new double[]{0, 0.0647});
                gp[44].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[47]}, new double[]{0, 0.0088});
                gp[48].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[47]}, new double[]{0, 0.005});
                gp[49].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[47]}, new double[]{0, 0.008823});
                gp[7].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[44], gp[48]}, new double[]{0, 0.0588});
                gp[9].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[48], gp[49]}, new double[]{0, 0.045});
                gp[6].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[7], gp[9]}, new double[]{0, 0.0147});
                gp[8].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[7], gp[9]}, new double[]{0, 0.0176});
                gp[43].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[6], gp[8]}, new double[]{0, 0.0118});
                //嘴巴x
                gp[6].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6]}, new double[]{0, 0});
                gp[7].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6]}, new double[]{0, 0});
                gp[8].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6]}, new double[]{0, 0});
                gp[9].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6]}, new double[]{0, 0});
                gp[43].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[6], gp[7], gp[8], gp[9]}, new double[]{0, 0.0204});
                gp[44].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[43]}, new double[]{0.065, 0.090});
                gp[48].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[43]}, new double[]{0, 0.0327});
                gp[49].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[47]}, new double[]{-0.070, -0.0408});

                gp[47].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[44], gp[48], gp[49]}, new double[]{0.199, 0.0550});

                gp[46].setPrePointsAndAddScopeBasedonPrePoints('x', new GraphPoint[]{gp[47]}, new double[]{0, 0.0286});
                //嘴巴y
                gp[9].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[10]}, new double[]{0.14, 0.18});
                gp[8].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[9]}, new double[]{0.061, 0.0103});
                gp[49].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[8]}, new double[]{0.005, 0.020});

                gp[7].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[6]}, new double[]{-0.04, -0.035});

                gp[46].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[49]}, new double[]{0, 0.049});
                gp[47].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[49]}, new double[]{0, 0.049});
                gp[48].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[49]}, new double[]{0, 0.049});

                gp[6].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[7], gp[46], gp[47], gp[48]}, new double[]{0.0, 0.0327});

                gp[44].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[7], gp[46], gp[47], gp[48]}, new double[]{0.012, 0.0328});
                gp[43].setPrePointsAndAddScopeBasedonPrePoints('y', new GraphPoint[]{gp[6], gp[44]}, new double[]{0.01, 0.016});
                //嘴巴z
                gp[46].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[29]}, new double[]{0, 0.117647059});
                gp[47].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[46]}, new double[]{0, 0.0647});
                gp[44].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[47]}, new double[]{0, 0.0088});
                gp[48].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[47]}, new double[]{0, 0.005});
                gp[49].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[47]}, new double[]{0, 0.008823});
                gp[7].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[44], gp[48]}, new double[]{0, 0.0588});

                gp[9].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[48], gp[49]}, new double[]{0, 0.045});
                gp[6].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[7], gp[9]}, new double[]{0, 0.0147});
                gp[8].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[7], gp[9]}, new double[]{0, 0.0176});
                gp[43].setPrePointsAndAddScopeBasedonPrePoints('z', new GraphPoint[]{gp[6], gp[8]}, new double[]{0, 0.0118});

            }
            //下面设置各点的后继
            {
                /*这里是由前驱取后继的程序
            *
            new FaceVertexData();
            FaceVertexData.init();
            for (int i = 0; i < FaceVertexData.gp.length; i++) {
            if (FaceVertexData.gp[i].xpre > 0) {
            for (int j = 0; j < FaceVertexData.gp[i].xpre ; j++) {
            System.out.println("gp[" + Integer.toString(FaceVertexData.gp[i].getPrePoints()[0][j].getPointNum()) + "].setNextPoints('x', new GraphPoint[]{gp[" + FaceVertexData.gp[i].getPointNum() + "]});");
            }
            }
            if (FaceVertexData.gp[i].ypre > 0) {
            for (int j = 0; j < FaceVertexData.gp[i].ypre; j++) {
            System.out.println("gp[" + Integer.toString(FaceVertexData.gp[i].getPrePoints()[1][j].getPointNum()) + "].setNextPoints('y', new GraphPoint[]{gp[" + FaceVertexData.gp[i].getPointNum() + "]});");
            }
            }
            if (FaceVertexData.gp[i].zpre > 0) {
            for (int j = 0; j < FaceVertexData.gp[i].zpre; j++) {
            System.out.println("gp[" + Integer.toString(FaceVertexData.gp[i].getPrePoints()[2][j].getPointNum()) + "].setNextPoints('z', new GraphPoint[]{gp[" + FaceVertexData.gp[i].getPointNum() + "]});");
            }
            }
            }
                 */
                gp[11].setNextPoints('y', new GraphPoint[]{gp[0]});
                gp[12].setNextPoints('z', new GraphPoint[]{gp[0]});
                gp[15].setNextPoints('z', new GraphPoint[]{gp[0]});
                gp[13].setNextPoints('z', new GraphPoint[]{gp[1]});
                gp[21].setNextPoints('y', new GraphPoint[]{gp[2]});
                gp[21].setNextPoints('z', new GraphPoint[]{gp[2]});
                gp[36].setNextPoints('z', new GraphPoint[]{gp[2]});
                gp[4].setNextPoints('y', new GraphPoint[]{gp[3]});
                gp[39].setNextPoints('y', new GraphPoint[]{gp[3]});
                gp[2].setNextPoints('z', new GraphPoint[]{gp[3]});
                gp[5].setNextPoints('z', new GraphPoint[]{gp[3]});
                gp[5].setNextPoints('y', new GraphPoint[]{gp[4]});
                gp[40].setNextPoints('z', new GraphPoint[]{gp[4]});
                gp[43].setNextPoints('y', new GraphPoint[]{gp[5]});
                gp[39].setNextPoints('z', new GraphPoint[]{gp[5]});
                gp[36].setNextPoints('z', new GraphPoint[]{gp[5]});
                gp[7].setNextPoints('y', new GraphPoint[]{gp[6]});
                gp[46].setNextPoints('y', new GraphPoint[]{gp[6]});
                gp[47].setNextPoints('y', new GraphPoint[]{gp[6]});
                gp[48].setNextPoints('y', new GraphPoint[]{gp[6]});
                gp[7].setNextPoints('z', new GraphPoint[]{gp[6]});
                gp[9].setNextPoints('z', new GraphPoint[]{gp[6]});
                gp[49].setNextPoints('y', new GraphPoint[]{gp[7]});
                gp[44].setNextPoints('z', new GraphPoint[]{gp[7]});
                gp[48].setNextPoints('z', new GraphPoint[]{gp[7]});
                gp[9].setNextPoints('y', new GraphPoint[]{gp[8]});
                gp[7].setNextPoints('z', new GraphPoint[]{gp[8]});
                gp[9].setNextPoints('z', new GraphPoint[]{gp[8]});
                gp[10].setNextPoints('y', new GraphPoint[]{gp[9]});
                gp[48].setNextPoints('z', new GraphPoint[]{gp[9]});
                gp[49].setNextPoints('z', new GraphPoint[]{gp[9]});
                gp[50].setNextPoints('z', new GraphPoint[]{gp[10]});
                gp[0].setNextPoints('x', new GraphPoint[]{gp[11]});
                gp[1].setNextPoints('x', new GraphPoint[]{gp[11]});
                gp[13].setNextPoints('y', new GraphPoint[]{gp[11]});
                gp[12].setNextPoints('y', new GraphPoint[]{gp[11]});
                gp[11].setNextPoints('x', new GraphPoint[]{gp[12]});
                gp[13].setNextPoints('x', new GraphPoint[]{gp[12]});
                gp[1].setNextPoints('y', new GraphPoint[]{gp[12]});
                gp[15].setNextPoints('y', new GraphPoint[]{gp[12]});
                gp[14].setNextPoints('y', new GraphPoint[]{gp[12]});
                gp[11].setNextPoints('z', new GraphPoint[]{gp[12]});
                gp[0].setNextPoints('x', new GraphPoint[]{gp[13]});
                gp[1].setNextPoints('x', new GraphPoint[]{gp[13]});
                gp[1].setNextPoints('y', new GraphPoint[]{gp[13]});
                gp[15].setNextPoints('y', new GraphPoint[]{gp[13]});
                gp[14].setNextPoints('y', new GraphPoint[]{gp[13]});
                gp[14].setNextPoints('z', new GraphPoint[]{gp[13]});
                gp[11].setNextPoints('x', new GraphPoint[]{gp[14]});
                gp[13].setNextPoints('x', new GraphPoint[]{gp[14]});
                gp[0].setNextPoints('z', new GraphPoint[]{gp[14]});
                gp[12].setNextPoints('x', new GraphPoint[]{gp[15]});
                gp[14].setNextPoints('x', new GraphPoint[]{gp[15]});
                gp[11].setNextPoints('z', new GraphPoint[]{gp[15]});
                gp[19].setNextPoints('x', new GraphPoint[]{gp[16]});
                gp[17].setNextPoints('y', new GraphPoint[]{gp[16]});
                gp[18].setNextPoints('z', new GraphPoint[]{gp[16]});
                gp[19].setNextPoints('x', new GraphPoint[]{gp[17]});
                gp[18].setNextPoints('y', new GraphPoint[]{gp[17]});
                gp[19].setNextPoints('y', new GraphPoint[]{gp[17]});
                gp[18].setNextPoints('z', new GraphPoint[]{gp[17]});
                gp[16].setNextPoints('x', new GraphPoint[]{gp[18]});
                gp[17].setNextPoints('x', new GraphPoint[]{gp[18]});
                gp[20].setNextPoints('y', new GraphPoint[]{gp[18]});
                gp[28].setNextPoints('z', new GraphPoint[]{gp[18]});
                gp[21].setNextPoints('x', new GraphPoint[]{gp[19]});
                gp[20].setNextPoints('y', new GraphPoint[]{gp[19]});
                gp[16].setNextPoints('z', new GraphPoint[]{gp[19]});
                gp[17].setNextPoints('z', new GraphPoint[]{gp[19]});
                gp[24].setNextPoints('x', new GraphPoint[]{gp[20]});
                gp[26].setNextPoints('x', new GraphPoint[]{gp[20]});
                gp[31].setNextPoints('x', new GraphPoint[]{gp[20]});
                gp[34].setNextPoints('x', new GraphPoint[]{gp[20]});
                gp[22].setNextPoints('y', new GraphPoint[]{gp[20]});
                gp[24].setNextPoints('y', new GraphPoint[]{gp[20]});
                gp[23].setNextPoints('y', new GraphPoint[]{gp[20]});
                gp[24].setNextPoints('z', new GraphPoint[]{gp[20]});
                gp[26].setNextPoints('z', new GraphPoint[]{gp[20]});
                gp[31].setNextPoints('z', new GraphPoint[]{gp[20]});
                gp[34].setNextPoints('z', new GraphPoint[]{gp[20]});
                gp[2].setNextPoints('x', new GraphPoint[]{gp[21]});
                gp[3].setNextPoints('y', new GraphPoint[]{gp[21]});
                gp[36].setNextPoints('y', new GraphPoint[]{gp[21]});
                gp[41].setNextPoints('z', new GraphPoint[]{gp[21]});
                gp[20].setNextPoints('x', new GraphPoint[]{gp[22]});
                gp[23].setNextPoints('x', new GraphPoint[]{gp[22]});
                gp[33].setNextPoints('x', new GraphPoint[]{gp[22]});
                gp[35].setNextPoints('x', new GraphPoint[]{gp[22]});
                gp[25].setNextPoints('y', new GraphPoint[]{gp[22]});
                gp[26].setNextPoints('y', new GraphPoint[]{gp[22]});
                gp[29].setNextPoints('z', new GraphPoint[]{gp[22]});
                gp[24].setNextPoints('x', new GraphPoint[]{gp[23]});
                gp[26].setNextPoints('x', new GraphPoint[]{gp[23]});
                gp[31].setNextPoints('x', new GraphPoint[]{gp[23]});
                gp[34].setNextPoints('x', new GraphPoint[]{gp[23]});
                gp[25].setNextPoints('y', new GraphPoint[]{gp[23]});
                gp[26].setNextPoints('y', new GraphPoint[]{gp[23]});
                gp[24].setNextPoints('z', new GraphPoint[]{gp[23]});
                gp[26].setNextPoints('z', new GraphPoint[]{gp[23]});
                gp[31].setNextPoints('z', new GraphPoint[]{gp[23]});
                gp[34].setNextPoints('z', new GraphPoint[]{gp[23]});
                gp[27].setNextPoints('x', new GraphPoint[]{gp[24]});
                gp[25].setNextPoints('y', new GraphPoint[]{gp[24]});
                gp[26].setNextPoints('y', new GraphPoint[]{gp[24]});
                gp[27].setNextPoints('z', new GraphPoint[]{gp[24]});
                gp[35].setNextPoints('z', new GraphPoint[]{gp[24]});
                gp[20].setNextPoints('x', new GraphPoint[]{gp[25]});
                gp[23].setNextPoints('x', new GraphPoint[]{gp[25]});
                gp[33].setNextPoints('x', new GraphPoint[]{gp[25]});
                gp[35].setNextPoints('x', new GraphPoint[]{gp[25]});
                gp[27].setNextPoints('y', new GraphPoint[]{gp[25]});
                gp[29].setNextPoints('y', new GraphPoint[]{gp[25]});
                gp[29].setNextPoints('z', new GraphPoint[]{gp[25]});
                gp[27].setNextPoints('x', new GraphPoint[]{gp[26]});
                gp[27].setNextPoints('y', new GraphPoint[]{gp[26]});
                gp[29].setNextPoints('y', new GraphPoint[]{gp[26]});
                gp[27].setNextPoints('z', new GraphPoint[]{gp[26]});
                gp[35].setNextPoints('z', new GraphPoint[]{gp[26]});
                gp[21].setNextPoints('x', new GraphPoint[]{gp[27]});
                gp[30].setNextPoints('y', new GraphPoint[]{gp[27]});
                gp[31].setNextPoints('y', new GraphPoint[]{gp[27]});
                gp[22].setNextPoints('z', new GraphPoint[]{gp[27]});
                gp[25].setNextPoints('z', new GraphPoint[]{gp[27]});
                gp[30].setNextPoints('z', new GraphPoint[]{gp[27]});
                gp[32].setNextPoints('z', new GraphPoint[]{gp[27]});
                gp[38].setNextPoints('x', new GraphPoint[]{gp[28]});
                gp[37].setNextPoints('y', new GraphPoint[]{gp[28]});
                gp[22].setNextPoints('x', new GraphPoint[]{gp[29]});
                gp[25].setNextPoints('x', new GraphPoint[]{gp[29]});
                gp[30].setNextPoints('x', new GraphPoint[]{gp[29]});
                gp[32].setNextPoints('x', new GraphPoint[]{gp[29]});
                gp[30].setNextPoints('y', new GraphPoint[]{gp[29]});
                gp[31].setNextPoints('y', new GraphPoint[]{gp[29]});
                gp[28].setNextPoints('z', new GraphPoint[]{gp[29]});
                gp[20].setNextPoints('x', new GraphPoint[]{gp[30]});
                gp[23].setNextPoints('x', new GraphPoint[]{gp[30]});
                gp[33].setNextPoints('x', new GraphPoint[]{gp[30]});
                gp[35].setNextPoints('x', new GraphPoint[]{gp[30]});
                gp[33].setNextPoints('y', new GraphPoint[]{gp[30]});
                gp[32].setNextPoints('y', new GraphPoint[]{gp[30]});
                gp[34].setNextPoints('y', new GraphPoint[]{gp[30]});
                gp[29].setNextPoints('z', new GraphPoint[]{gp[30]});
                gp[27].setNextPoints('x', new GraphPoint[]{gp[31]});
                gp[33].setNextPoints('y', new GraphPoint[]{gp[31]});
                gp[32].setNextPoints('y', new GraphPoint[]{gp[31]});
                gp[34].setNextPoints('y', new GraphPoint[]{gp[31]});
                gp[27].setNextPoints('z', new GraphPoint[]{gp[31]});
                gp[35].setNextPoints('z', new GraphPoint[]{gp[31]});
                gp[20].setNextPoints('x', new GraphPoint[]{gp[32]});
                gp[23].setNextPoints('x', new GraphPoint[]{gp[32]});
                gp[33].setNextPoints('x', new GraphPoint[]{gp[32]});
                gp[35].setNextPoints('x', new GraphPoint[]{gp[32]});
                gp[35].setNextPoints('y', new GraphPoint[]{gp[32]});
                gp[29].setNextPoints('z', new GraphPoint[]{gp[32]});
                gp[24].setNextPoints('x', new GraphPoint[]{gp[33]});
                gp[26].setNextPoints('x', new GraphPoint[]{gp[33]});
                gp[31].setNextPoints('x', new GraphPoint[]{gp[33]});
                gp[34].setNextPoints('x', new GraphPoint[]{gp[33]});
                gp[35].setNextPoints('y', new GraphPoint[]{gp[33]});
                gp[24].setNextPoints('z', new GraphPoint[]{gp[33]});
                gp[26].setNextPoints('z', new GraphPoint[]{gp[33]});
                gp[31].setNextPoints('z', new GraphPoint[]{gp[33]});
                gp[34].setNextPoints('z', new GraphPoint[]{gp[33]});
                gp[27].setNextPoints('x', new GraphPoint[]{gp[34]});
                gp[35].setNextPoints('y', new GraphPoint[]{gp[34]});
                gp[27].setNextPoints('z', new GraphPoint[]{gp[34]});
                gp[35].setNextPoints('z', new GraphPoint[]{gp[34]});
                gp[24].setNextPoints('x', new GraphPoint[]{gp[35]});
                gp[26].setNextPoints('x', new GraphPoint[]{gp[35]});
                gp[31].setNextPoints('x', new GraphPoint[]{gp[35]});
                gp[34].setNextPoints('x', new GraphPoint[]{gp[35]});
                gp[37].setNextPoints('y', new GraphPoint[]{gp[35]});
                gp[22].setNextPoints('z', new GraphPoint[]{gp[35]});
                gp[25].setNextPoints('z', new GraphPoint[]{gp[35]});
                gp[30].setNextPoints('z', new GraphPoint[]{gp[35]});
                gp[32].setNextPoints('z', new GraphPoint[]{gp[35]});
                gp[2].setNextPoints('x', new GraphPoint[]{gp[36]});
                gp[39].setNextPoints('y', new GraphPoint[]{gp[36]});
                gp[40].setNextPoints('y', new GraphPoint[]{gp[36]});
                gp[41].setNextPoints('z', new GraphPoint[]{gp[36]});
                gp[38].setNextPoints('y', new GraphPoint[]{gp[37]});
                gp[42].setNextPoints('z', new GraphPoint[]{gp[37]});
                gp[42].setNextPoints('x', new GraphPoint[]{gp[38]});
                gp[37].setNextPoints('x', new GraphPoint[]{gp[38]});
                gp[42].setNextPoints('y', new GraphPoint[]{gp[38]});
                gp[28].setNextPoints('z', new GraphPoint[]{gp[38]});
                gp[21].setNextPoints('x', new GraphPoint[]{gp[39]});
                gp[40].setNextPoints('x', new GraphPoint[]{gp[39]});
                gp[36].setNextPoints('x', new GraphPoint[]{gp[39]});
                gp[5].setNextPoints('y', new GraphPoint[]{gp[39]});
                gp[41].setNextPoints('y', new GraphPoint[]{gp[39]});
                gp[41].setNextPoints('z', new GraphPoint[]{gp[39]});
                gp[2].setNextPoints('x', new GraphPoint[]{gp[40]});
                gp[41].setNextPoints('y', new GraphPoint[]{gp[40]});
                gp[3].setNextPoints('z', new GraphPoint[]{gp[40]});
                gp[39].setNextPoints('x', new GraphPoint[]{gp[41]});
                gp[43].setNextPoints('y', new GraphPoint[]{gp[41]});
                gp[29].setNextPoints('z', new GraphPoint[]{gp[41]});
                gp[43].setNextPoints('y', new GraphPoint[]{gp[42]});
                gp[38].setNextPoints('z', new GraphPoint[]{gp[42]});
                gp[6].setNextPoints('x', new GraphPoint[]{gp[43]});
                gp[7].setNextPoints('x', new GraphPoint[]{gp[43]});
                gp[8].setNextPoints('x', new GraphPoint[]{gp[43]});
                gp[9].setNextPoints('x', new GraphPoint[]{gp[43]});
                gp[6].setNextPoints('y', new GraphPoint[]{gp[43]});
                gp[44].setNextPoints('y', new GraphPoint[]{gp[43]});
                gp[6].setNextPoints('z', new GraphPoint[]{gp[43]});
                gp[8].setNextPoints('z', new GraphPoint[]{gp[43]});
                gp[43].setNextPoints('x', new GraphPoint[]{gp[44]});
                gp[7].setNextPoints('y', new GraphPoint[]{gp[44]});
                gp[46].setNextPoints('y', new GraphPoint[]{gp[44]});
                gp[47].setNextPoints('y', new GraphPoint[]{gp[44]});
                gp[48].setNextPoints('y', new GraphPoint[]{gp[44]});
                gp[47].setNextPoints('z', new GraphPoint[]{gp[44]});
                gp[50].setNextPoints('x', new GraphPoint[]{gp[45]});
                gp[50].setNextPoints('y', new GraphPoint[]{gp[45]});
                gp[47].setNextPoints('x', new GraphPoint[]{gp[46]});
                gp[49].setNextPoints('y', new GraphPoint[]{gp[46]});
                gp[29].setNextPoints('z', new GraphPoint[]{gp[46]});
                gp[44].setNextPoints('x', new GraphPoint[]{gp[47]});
                gp[48].setNextPoints('x', new GraphPoint[]{gp[47]});
                gp[49].setNextPoints('x', new GraphPoint[]{gp[47]});
                gp[49].setNextPoints('y', new GraphPoint[]{gp[47]});
                gp[46].setNextPoints('z', new GraphPoint[]{gp[47]});
                gp[43].setNextPoints('x', new GraphPoint[]{gp[48]});
                gp[49].setNextPoints('y', new GraphPoint[]{gp[48]});
                gp[47].setNextPoints('z', new GraphPoint[]{gp[48]});
                gp[43].setNextPoints('x', new GraphPoint[]{gp[49]});
                gp[8].setNextPoints('y', new GraphPoint[]{gp[49]});
                gp[47].setNextPoints('z', new GraphPoint[]{gp[49]});
                gp[10].setNextPoints('x', new GraphPoint[]{gp[50]});
                gp[10].setNextPoints('y', new GraphPoint[]{gp[50]});
                gp[45].setNextPoints('z', new GraphPoint[]{gp[50]});
                //一些特殊点的后继
                gp[2].setNextPoints('y', new GraphPoint[]{gp[16]});
                gp[16].setNextPoints('y', new GraphPoint[]{gp[14]});
                gp[18].setNextPoints('x', new GraphPoint[]{gp[15]});
                gp[29].setNextPoints('x', new GraphPoint[]{gp[28]});
                gp[41].setNextPoints('x', new GraphPoint[]{gp[45]});
                gp[45].setNextPoints('y', new GraphPoint[]{gp[38]});
                gp[46].setNextPoints('x', new GraphPoint[]{gp[45]});
            }
        } else {//是以仿射变换参数作为进化对象的
            //不必再设置点的前驱及后继等内容了！因为点的坐标直接通过仿射变换得到，而不必计算每个点的坐标了；
            //但是需要设置仿射变换矩阵
            switch (FaceControlParameters.part) {
                case 3: {
                    facePart[3].getAffineMatrix().setTranslationScope(new NodeWithScopeElement[]{new NodeWithScopeElement(new double[]{-0.005, 0.05}), new NodeWithScopeElement(new double[]{-0.005, 0.05}), new NodeWithScopeElement(new double[]{-0.008, 0.03})});
                    facePart[3].getAffineMatrix().setScaleScope(new NodeWithScopeElement[]{new NodeWithScopeElement(new double[]{0.4, 1.1}), new NodeWithScopeElement(new double[]{0.4, 1.2}), new NodeWithScopeElement(new double[]{0.4, 1.2})});
                    //ShearScope的参数个数是dimension*(dimension-1)
                    facePart[3].getAffineMatrix().setShearScope(new NodeWithScopeElement[]{new NodeWithScopeElement(new double[]{-0.2, 0.2}),
                        new NodeWithScopeElement(new double[]{-0.2, 0.2}), new NodeWithScopeElement(new double[]{-0.2, 0.2}), new NodeWithScopeElement(new double[]{-0.2, 0.2}),
                        new NodeWithScopeElement(new double[]{-0.2, 0.2}), new NodeWithScopeElement(new double[]{-0.2, 0.2})});
                    facePart[3].getAffineMatrix().setRotationScope(
                            new NodeWithScopeElement[]{new NodeWithScopeElement(new double[]{-Math.PI / 10,  Math.PI / 10}), new NodeWithScopeElement(new double[]{ -Math.PI / 10,  Math.PI / 10}), new NodeWithScopeElement(new double[]{ -Math.PI / 10,  Math.PI / 10})});
                    facePart[3].getAffineMatrix().calNormalAndGSUCount();
                    //置矩阵为单位矩阵
                    MyArray.setUnit(facePart[3].getAffineMatrix().getScaleDouble());
                    MyArray.setUnit(facePart[3].getAffineMatrix().getShearDouble());
                    MyArray.setUnit(facePart[3].getAffineMatrix().getRotateDouble());
                    //该实现种群初始化了，初始化完成后计算仿射变换矩阵，然后再求人脸的坐标点值
                }
                break;
            }
        }
    }

    public static double[][] getRandomScope() {
        //设置变化范围
        int[] tem = FaceVertexData.facePart[FaceControlParameters.part].getVertNum();//得到顶点序号
        double[][] randScope = new double[tem.length * 3][2];
        for (int i = 0; i < tem.length; i++) {
            //float a=FaceVertexData.gp[tem[i]].getfirstPrePoint('x').getPointCoordinates()[0];
            // float b=FaceVertexData.gp[tem[i]].getcoordMutationScope('x')[0];
            randScope[i * 3][0] = FaceVertexData.gp[tem[i]].getfirstPrePoint('x').getPointCoordinates()[0] + FaceVertexData.gp[tem[i]].getcoordMutationScope('x')[0];
            randScope[i * 3][1] = FaceVertexData.gp[tem[i]].getfirstPrePoint('x').getPointCoordinates()[0] + FaceVertexData.gp[tem[i]].getcoordMutationScope('x')[1];
            randScope[i * 3 + 1][0] = FaceVertexData.gp[tem[i]].getfirstPrePoint('y').getPointCoordinates()[1] + FaceVertexData.gp[tem[i]].getcoordMutationScope('y')[0];
            randScope[i * 3 + 1][1] = FaceVertexData.gp[tem[i]].getfirstPrePoint('y').getPointCoordinates()[1] + FaceVertexData.gp[tem[i]].getcoordMutationScope('y')[1];
            randScope[i * 3 + 2][0] = FaceVertexData.gp[tem[i]].getfirstPrePoint('z').getPointCoordinates()[2] + FaceVertexData.gp[tem[i]].getcoordMutationScope('z')[0];
            randScope[i * 3 + 2][1] = FaceVertexData.gp[tem[i]].getfirstPrePoint('z').getPointCoordinates()[2] + FaceVertexData.gp[tem[i]].getcoordMutationScope('z')[1];
        }
        return randScope;
    }
}
