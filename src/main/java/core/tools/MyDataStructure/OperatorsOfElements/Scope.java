package core.tools.MyDataStructure.OperatorsOfElements;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class Scope {

    public static int scopeCompare(double[] a, double[] b) {
        int compareResult = 0;
        if (a[0] <= b[0]) {
            if (a[1] <= b[0]) {
                compareResult = 0;//a<b,b作a的后继
            } else {//a[1]>b[0]
                if (a[1] < b[1]) {
                    compareResult = 1;//b下限夹在a中间，结果[a下,b上]
                } else {
                    compareResult = 2;//b被a包围，结果表明为a
                }
            }
        } else {//a[0]>b[0]
            if (a[0] > b[1]) {
                compareResult = 3;//b<a
            } else {//a[0]>b[0],a[0]<b[1],a下限夹在b中间，
                if (a[1] >= b[1]) {
                    compareResult = 4;//结果[b下,a上]
                } else {
                    compareResult = 5;///a被b包围，结果表明为b
                }
            }
        }
        return compareResult;
    }
  public static int scopePointCompare(double[] a,double b)  {
      int tem=0;
      if(b<a[0]){
          tem=0;//b在a之前；
      }else{
          if(b<a[1]){
              tem=1;//b夹在a之间
          }else{
              tem=2;//b在a之后；
          }
      }
      return tem;
  }
}
