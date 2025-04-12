import java.util.ArrayList;

public class BinaryTreeMethods {

    public long makingTree(int[] listKeys, int sizes) {
        ArrayList<BinaryKey> listNodes = new ArrayList<>();
        ArrayList<Integer> uniqueKeys = new ArrayList<>();
        long start, end;

        //adding the root
        BinaryKey root = new BinaryKey(1);
        uniqueKeys.add(root.getKey());
        addKey(root, listNodes);
        
        start = System.currentTimeMillis(); //starts timer
        
        for(int i = 0; i < sizes; i++){
            BinaryKey newKey = new BinaryKey(listKeys[i]);
            if(!uniqueKeys.contains(listKeys[i])){
                uniqueKeys.add(listKeys[i]);
                addKey(newKey, listNodes);
            }
        }
        
        end = System.currentTimeMillis(); //ends timer

        System.out.println("Size " + sizes + " Done");
        return end - start;
    }

    //adds a key to the tree horizontally
    public void addKey(BinaryKey key, ArrayList<BinaryKey> listNodes) {
        if (listNodes.isEmpty()) {
            //this will be the root
            listNodes.add(key);
        }
        else {
            BinaryKey parentNode = listNodes.get(0);
            if(parentNode.getLeftNode() == null){
                parentNode.setLeftNode(key);
                listNodes.add(parentNode.getLeftNode());
            }
            else{
                parentNode.setRightNode(key);
                listNodes.add(parentNode.getRightNode());
                listNodes.remove(0);
            }
        }
    }
}
