package core.tools.MyDataStructure.Node;
/**
 *
 * @author 郝国生  HAO Guo-Sheng
 */
public class BaseNodeWithoutElement {
    public BaseNodeWithoutElement next=null;//为链式存储准备
    public BaseNodeWithoutElement pre=null;
    public BaseNodeWithoutElement(){
    }
    public boolean hasNext(){
        return this.next!=null;
    }
}
