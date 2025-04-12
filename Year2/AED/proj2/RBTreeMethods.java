public class RBTreeMethods {
    public long makingTree(int sizes, int[] listKeys, int[] rotations) {
        long start, end;

        RBKey root = new RBKey(listKeys[0]);
        root.setRed(false);

        start = System.currentTimeMillis();

        for (int i = 1; i < sizes; i++) {
            root = addKey(root, new RBKey(listKeys[i]), rotations);
            root.setRed(false);
        }

        end = System.currentTimeMillis();

        System.out.println("Size " + sizes + " Done");
        return end - start;
    }

    public RBKey addKey(RBKey root, RBKey key, int[] rotations) {
        RBKey current = root;
        RBKey parent = null;

        while (current != null) {
            parent = current;
            if (key.getKey() < current.getKey()) {
                current = current.getLeftNode();
            } else if (key.getKey() > current.getKey()) {
                current = current.getRightNode();
            } else {
                return root;
            }
        }

        key.setParentNode(parent);
        if (key.getKey() < parent.getKey()) {
            parent.setLeftNode(key);
        } else {
            parent.setRightNode(key);
        }

        fixViolations(key, rotations);

        while (key.getParentNode() != null) {
            key = key.getParentNode();
        }

        return key; // Return new root
    }

    private void fixViolations(RBKey node, int[] rotations) {
        while (node != null && node != getRoot(node) && node.getParentNode().isRed()) {
            RBKey parent = node.getParentNode();
            RBKey grandParent = parent.getParentNode();

            if (parent == grandParent.getLeftNode()) {
                RBKey uncle = grandParent.getRightNode();

                if (uncle != null && uncle.isRed()) {
                    grandParent.setRed(true);
                    parent.setRed(false);
                    uncle.setRed(false);
                    node = grandParent;
                } else {
                    if (node == parent.getRightNode()) {
                        node = parent;
                        leftRotate(node, rotations);
                        parent = node.getParentNode();
                    }
                    parent.setRed(false);
                    grandParent.setRed(true);
                    rightRotate(grandParent, rotations);
                }
            } else {
                RBKey uncle = grandParent.getLeftNode();

                if (uncle != null && uncle.isRed()) {
                    grandParent.setRed(true);
                    parent.setRed(false);
                    uncle.setRed(false);
                    node = grandParent;
                } else {
                    if (node == parent.getLeftNode()) {
                        node = parent;
                        rightRotate(node, rotations);
                        parent = node.getParentNode();
                    }
                    parent.setRed(false);
                    grandParent.setRed(true);
                    leftRotate(grandParent, rotations);
                }
            }
        }
    }

    private RBKey getRoot(RBKey node) {
        while (node.getParentNode() != null) {
            node = node.getParentNode();
        }
        return node;
    }

    private RBKey leftRotate(RBKey root, int[] rotations) {
        RBKey newRoot = root.getRightNode();
        root.setRightNode(newRoot.getLeftNode());
        if (newRoot.getLeftNode() != null) {
            newRoot.getLeftNode().setParentNode(root);
        }
        newRoot.setParentNode(root.getParentNode());
        if (root.getParentNode() == null) {
            // root is the root of the tree
        } else if (root == root.getParentNode().getLeftNode()) {
            root.getParentNode().setLeftNode(newRoot);
        } else {
            root.getParentNode().setRightNode(newRoot);
        }
        newRoot.setLeftNode(root);
        root.setParentNode(newRoot);
        rotations[0]++;
        return newRoot;
    }

    private RBKey rightRotate(RBKey root, int[] rotations) {
        RBKey newRoot = root.getLeftNode();
        root.setLeftNode(newRoot.getRightNode());
        if (newRoot.getRightNode() != null) {
            newRoot.getRightNode().setParentNode(root);
        }
        newRoot.setParentNode(root.getParentNode());
        if (root.getParentNode() == null) {
            // root is the root of the tree
        } else if (root == root.getParentNode().getLeftNode()) {
            root.getParentNode().setLeftNode(newRoot);
        } else {
            root.getParentNode().setRightNode(newRoot);
        }
        newRoot.setRightNode(root);
        root.setParentNode(newRoot);
        rotations[0]++;
        return newRoot;
    }
} 
