package core.tools.MyDataStructure.Node;

/**
 *
 * @author 郝国生 HAO Guo-Sheng
 */
public class NodeWithScopeElement extends BaseNodeWithoutElement {

    public double scope[] = new double[2];

    public NodeWithScopeElement(double[] scope) {
        super();
        for (int i = 0; i < scope.length; i++) {
            this.scope[i] = scope[i];
        }
    }

    public NodeWithScopeElement getClone() {
        double[] resultScope = new double[this.scope.length];
        System.arraycopy(this.scope, 0, resultScope, 0, this.scope.length);
        NodeWithScopeElement result = new NodeWithScopeElement(scope);
        return result;
    }
}
