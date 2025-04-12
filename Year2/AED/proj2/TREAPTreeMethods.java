import java.util.ArrayList;
import java.util.Collections;
public class TREAPTreeMethods {
    public long makingTree(int[] listKeys, int sizes, int[] rotations) {
        // Create a list of random priorities for each key as it is shuffled
        ArrayList<Integer> priorities = new ArrayList<>();
        for (int i = 0; i < sizes; i++) {
            priorities.add(i);
        }
        Collections.shuffle(priorities);

        TREAPKey root = new TREAPKey(listKeys[0], priorities.get(0));
        
        long start, end;
        
        start = System.currentTimeMillis(); //starts timer
        
        for (int i = 1; i < sizes; i++) {
            root = addKey(root, new TREAPKey(listKeys[i], priorities.get(i)), rotations);
        }
        
        end = System.currentTimeMillis(); //ends timer
        
        System.out.println("Size " + sizes + " Done");
        return end - start;
    }

    public TREAPKey addKey(TREAPKey root, TREAPKey key, int[] rotations) {
        if (root == null) {
            return key;
        }
        
        if (key.getKey() < root.getKey()) {
            root.setLeftNode(addKey(root.getLeftNode(), key, rotations));
            if (root.getLeftNode().getPriority() > root.getPriority()) {
                root = rightRotate(root, rotations);
            }
        } else if (key.getKey() > root.getKey()) {
            root.setRightNode(addKey(root.getRightNode(), key, rotations));
            if (root.getRightNode().getPriority() > root.getPriority()) {
                root = leftRotate(root, rotations);
            }
        }
        
        return root;
    }

    private TREAPKey rightRotate(TREAPKey root, int[] rotations) {
        TREAPKey newRoot = root.getLeftNode();
        root.setLeftNode(newRoot.getRightNode());
        newRoot.setRightNode(root);
        rotations[0]++;
        return newRoot;
    }

    private TREAPKey leftRotate(TREAPKey root, int[] rotations) {
        TREAPKey newRoot = root.getRightNode();
        root.setRightNode(newRoot.getLeftNode());
        newRoot.setLeftNode(root);
        rotations[0]++;
        return newRoot;
    }
}
