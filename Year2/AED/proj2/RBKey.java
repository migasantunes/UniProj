public class RBKey {
    private int key;
    private RBKey leftNode;
    private RBKey rightNode;
    private boolean isRed;
    private RBKey parentNode;

    public RBKey(int key) {
        this.key = key;
        this.leftNode = null;
        this.rightNode = null;
        this.isRed = true; // New nodes are always red
        this.parentNode = null;
    }

    public int getKey() {return key;}
    public RBKey getLeftNode() {return leftNode;}
    public RBKey getRightNode() {return rightNode;}
    public boolean isRed() {return isRed;}
    public RBKey getParentNode() {return parentNode;}

    public void setKey(int key) {this.key = key;}
    public void setLeftNode(RBKey leftNode) {this.leftNode = leftNode;}
    public void setRightNode(RBKey rightNode) {this.rightNode = rightNode;}
    public void setRed(boolean isRed) {this.isRed = isRed;}
    public void setParentNode(RBKey parentNode) {this.parentNode = parentNode;}
}
