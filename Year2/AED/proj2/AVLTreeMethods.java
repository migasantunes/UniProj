
public class AVLTreeMethods {
    public long makingTree(int[] listKeys, int sizes, int[] rotations) {
        long start, end;

        AVLKey root = new AVLKey(listKeys[0]);

        start = System.currentTimeMillis(); //starts timer

        for(int i = 1; i < sizes; i++){
            root = addKey(new AVLKey(listKeys[i]), root, rotations);
        }

        end = System.currentTimeMillis(); //ends timer

        System.out.println("Size " + sizes + " Done");
        return end - start;
    }

    public AVLKey addKey(AVLKey key, AVLKey root, int[] rotations) {
        if (root == null) {
            return key;
        }

        if (key.getKey() == root.getKey()){
            return root;
        }   

        if (key.getKey() < root.getKey()) {
            if (root.getLeftNode() == null) {
                root.setLeftNode(key);
                root.setLeftHight(1);
            }
            else {
                root.setLeftNode(addKey(key, root.getLeftNode(), rotations));
                root.setLeftHight(root.getLeftNode().getLeftHight() + 1);

            }
        }
        else {
            if (root.getRightNode() == null) {
                root.setRightNode(key);
                root.setRightHight(1);
            }
            else {
                root.setRightNode(addKey(key, root.getRightNode(), rotations));
                root.setRightHight(root.getRightNode().getRightHight() + 1);
            }
        }

        if (root.getBalanceFactor() > 1) {
            if (key.getKey() < root.getLeftNode().getKey()) {
                return rightRotate(root, rotations); //Right rotation
            }
            else {
                root.setLeftNode(leftRotate(root.getLeftNode(), rotations)); //Left-Right rotation
                return rightRotate(root, rotations);
            }
        }
        if (root.getBalanceFactor() < -1) {
            if (key.getKey() > root.getRightNode().getKey()) {
                return leftRotate(root, rotations); //Left rotation
            }
            else {
                root.setRightNode(rightRotate(root.getRightNode(), rotations)); //Right-Left rotation
                return leftRotate(root, rotations);
            }
        }
        return root;
    }

    public AVLKey rightRotate(AVLKey root, int[] rotations) {
        AVLKey newRoot = root.getLeftNode();

        if (newRoot == null) {
            return root;
        }

        if (newRoot.getRightNode() != null) {
            root.setLeftNode(newRoot.getRightNode());
        } else {
            root.setLeftNode(null);
        }

        newRoot.setRightNode(root);
        
        if (root.getLeftNode() != null) {
            root.setLeftHight(Math.max(root.getLeftNode().getLeftHight(), root.getLeftNode().getRightHight()) + 1);
        } else {
            root.setLeftHight(0);
        }
    
        if (root.getRightNode() != null) {
            root.setRightHight(Math.max(root.getRightNode().getLeftHight(), root.getRightNode().getRightHight()) + 1);
        } else {
            root.setRightHight(0);
        }
    
        if (newRoot.getLeftNode() != null) {
            newRoot.setLeftHight(Math.max(newRoot.getLeftNode().getLeftHight(), newRoot.getLeftNode().getRightHight()) + 1);
        } else {
            newRoot.setLeftHight(0);
        }
    
        newRoot.setRightHight(Math.max(root.getLeftHight(), root.getRightHight()) + 1);    
        
        rotations[0]++;
        return newRoot;
    }

    public AVLKey leftRotate(AVLKey root, int[] rotations) {
        AVLKey newRoot = root.getRightNode();

        if (newRoot == null) {
            return root;
        }
        
        if (newRoot.getLeftNode() != null) {
            root.setRightNode(newRoot.getLeftNode());
        } else {
            root.setRightNode(null);
        }

        newRoot.setLeftNode(root);
        
        if (root.getLeftNode() != null) {
            root.setLeftHight(Math.max(root.getLeftNode().getLeftHight(), root.getLeftNode().getRightHight()) + 1);
        } else {
            root.setLeftHight(0);
        }
    
        if (root.getRightNode() != null) {
            root.setRightHight(Math.max(root.getRightNode().getLeftHight(), root.getRightNode().getRightHight()) + 1);
        } else {
            root.setRightHight(0);
        }
    
        if (newRoot.getRightNode() != null) {
            newRoot.setRightHight(Math.max(newRoot.getRightNode().getLeftHight(), newRoot.getRightNode().getRightHight()) + 1);
        } else {
            newRoot.setRightHight(0);
        }
    
        newRoot.setLeftHight(Math.max(root.getLeftHight(), root.getRightHight()) + 1);    
        
        rotations[0]++;
        return newRoot;
    }
}
