public class AVLKey {
    private int key;
    private AVLKey leftNode;
    private AVLKey rightNode;
    private int rightHight;
    private int leftHight;
    
    public AVLKey(int key) {
        this.key = key;
        this.leftNode = null;
        this.rightNode = null;
        this.rightHight = 0;
        this.leftHight = 0;
    }

    public int getKey() {return key;}
    public AVLKey getLeftNode() {return leftNode;}
    public AVLKey getRightNode() {return rightNode;}
    public int getRightHight() {return rightHight;}
    public int getLeftHight() {return leftHight;}
    public int getBalanceFactor() {return leftHight - rightHight;}

    public void setKey(int key) {this.key = key;}
    public void setLeftNode(AVLKey leftNode) {this.leftNode = leftNode;}
    public void setRightNode(AVLKey rightNode) {this.rightNode = rightNode;}
    public void setRightHight(int righthight) {this.rightHight = righthight;}
    public void setLeftHight(int lefthight) {this.leftHight = lefthight;}
}
