public class TREAPKey {
    private int key;
    private int priority;
    private TREAPKey leftNode;
    private TREAPKey rightNode;

    public TREAPKey(int key, int priority) {
        this.key = key;
        this.priority = priority;
        this.leftNode = null;
        this.rightNode = null;
    }

    public int getKey() {return key;}
    public int getPriority() {return priority;}
    public TREAPKey getLeftNode() {return leftNode;}
    public TREAPKey getRightNode() {return rightNode;}
    public void setKey(int key) {this.key = key;}
    //public void setPriority(int priority) {this.priority = priority;} no need to set priority as it will be set when creating a new node
    public void setLeftNode(TREAPKey leftNode) {this.leftNode = leftNode;}
    public void setRightNode(TREAPKey rightNode) {this.rightNode = rightNode;}
}
