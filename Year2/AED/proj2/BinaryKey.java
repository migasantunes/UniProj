public class BinaryKey {
    private int key;
    private BinaryKey leftNode;
    private BinaryKey rightNode;
    
    public BinaryKey(int key) {
        this.key = key;
        this.leftNode = null;
        this.rightNode = null;
    }

    public int getKey() {return key;}
    public BinaryKey getLeftNode() {return leftNode;}
    public BinaryKey getRightNode() {return rightNode;}

    public void setKey(int key) {this.key = key;}
    public void setLeftNode(BinaryKey leftNode) {this.leftNode = leftNode;}
    public void setRightNode(BinaryKey rightNode) {this.rightNode = rightNode;}
}
