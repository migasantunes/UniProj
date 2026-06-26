import java.util.Arrays;
import java.util.Random;

public class Projecto2JMLA {
    public static void main(String[] args) {
        new Projecto2JMLA();
    }
    
    public Projecto2JMLA() {
        Random random = new Random();
        ResultExporter re = new ResultExporter();
        
        int sizes[] = {100000, 250000, 500000, 750000, 1000000};
        long[][] times = new long[sizes.length][4];
        
        int[][] rotationsResults = new int[sizes.length][4];
        int[] rotations4 = new int[4];

        int listKeysA[] = new int[sizes[sizes.length - 1]]; //low repetition and crecent order
        int listKeysB[] = new int[sizes[sizes.length - 1]]; //low repetition and decrescent order
        int listKeysC[] = new int[sizes[sizes.length - 1]]; //low repetition and random order
        int listKeysD[] = new int[sizes[sizes.length - 1]]; //high repetition and random order
        
        for (int i = 0; i < sizes[sizes.length - 1]; i++) { //around 5% of repeated keys
            if (random.nextInt(99) <= 4) {
                listKeysA[i] = i-1;
                listKeysB[i] = sizes[sizes.length - 1] - i + 1;
            }
            else {
                listKeysA[i] = i;
                listKeysB[i] = sizes[sizes.length - 1] - i;
            }
        }
        
        for (int i = 0; i < sizes[sizes.length - 1]; i++) {
            listKeysC[i] = random.nextInt((int)(sizes[sizes.length - 1] * 0.95)); //around 5% of repeated keys
            listKeysD[i] = random.nextInt((int)(sizes[sizes.length - 1] * 0.10)); //around 90% of repeated keys
        }


        //Binary tree
        for (int i = 0; i < sizes.length; i++) {
            times[i] = binaryTree(sizes[i], Arrays.copyOfRange(listKeysA, 0, sizes[i]), Arrays.copyOfRange(listKeysB, 0, sizes[i]), Arrays.copyOfRange(listKeysC, 0, sizes[i]), Arrays.copyOfRange(listKeysD, 0, sizes[i]));
        }

        re.ExportResults(times, rotationsResults, "-resultsBinary.csv", sizes);

        //AVL tree
        for (int i = 0; i < sizes.length; i++) {
            times[i] = avlTree(sizes[i], Arrays.copyOfRange(listKeysA, 0, sizes[i]), Arrays.copyOfRange(listKeysB, 0, sizes[i]), Arrays.copyOfRange(listKeysC, 0, sizes[i]), Arrays.copyOfRange(listKeysD, 0, sizes[i]), rotations4);
            rotationsResults[i] = Arrays.copyOf(rotations4, rotations4.length);
        }

        re.ExportResults(times, rotationsResults, "-resultsAVL.csv", sizes);

        //Red-black tree
        for (int i = 0; i < sizes.length; i++) {
            times[i] = redBlackTree(sizes[i], Arrays.copyOfRange(listKeysA, 0, sizes[i]), Arrays.copyOfRange(listKeysB, 0, sizes[i]), Arrays.copyOfRange(listKeysC, 0, sizes[i]), Arrays.copyOfRange(listKeysD, 0, sizes[i]), rotations4);
            rotationsResults[i] = Arrays.copyOf(rotations4, rotations4.length);
        }

        re.ExportResults(times, rotationsResults, "-resultsVP.csv", sizes);
        
        //Treap tree
        for (int i = 0; i < sizes.length; i++) {
            times[i] = treapTree(sizes[i], Arrays.copyOfRange(listKeysA, 0, sizes[i]), Arrays.copyOfRange(listKeysB, 0, sizes[i]), Arrays.copyOfRange(listKeysC, 0, sizes[i]), Arrays.copyOfRange(listKeysD, 0, sizes[i]), rotations4);
            rotationsResults[i] = Arrays.copyOf(rotations4, rotations4.length);
        }

        re.ExportResults(times, rotationsResults, "-resultsTREAP.csv", sizes);
    }
    
    //Binary tree
    private long[] binaryTree(int size, int[] lisKeysA, int[] lisKeysB, int[] lisKeysC, int[] lisKeysD) {
        BinaryTreeMethods btm = new BinaryTreeMethods();

        System.out.println("\nLow repetition and crecent order");
        long timeA = btm.makingTree(lisKeysA, size);

        System.out.println("\nLow repetition and decrescent order");
        long timeB = btm.makingTree(lisKeysB, size);
        
        System.out.println("\nLow repetition and random order");
        long timeC = btm.makingTree(lisKeysC, size);
        
        System.out.println("\nHigh repetition and random order");
        long timeD = btm.makingTree(lisKeysD, size);

        return new long[]{timeA, timeB, timeC, timeD};
    }

    //AVL tree
    private long[] avlTree(int size, int[] lisKeysA, int[] lisKeysB, int[] lisKeysC, int[] lisKeysD, int[] rotations4) {
        AVLTreeMethods atm = new AVLTreeMethods();
        int[] rotations = new int[1];
        
        System.out.println("\nLow repetition and crecent order");
        rotations[0] = 0;
        Long timeA = atm.makingTree(lisKeysA, size, rotations);
        rotations4[0] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);

        System.out.println("\nLow repetition and decrescent order");
        rotations[0] = 0;
        Long timeB = atm.makingTree(lisKeysB, size, rotations);
        rotations4[1] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);

        System.out.println("\nLow repetition and random order");
        rotations[0] = 0;
        Long timeC = atm.makingTree(lisKeysC, size, rotations);
        rotations4[2] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);

        System.out.println("\nHigh repetition and random order");
        rotations[0] = 0;
        Long timeD = atm.makingTree(lisKeysD, size, rotations);
        rotations4[3] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);

        return new long[]{timeA, timeB, timeC, timeD};
    }

    //Red-black tree
    private long[] redBlackTree(int size, int[] lisKeysA, int[] lisKeysB, int[] lisKeysC, int[] lisKeysD, int[] rotations4) {
        RBTreeMethods rbm = new RBTreeMethods();
        int[] rotations = new int[1];
        
        System.out.println("\nLow repetition and crecent order");
        rotations[0] = 0;
        Long timeA = rbm.makingTree(size, lisKeysA, rotations);
        rotations4[0] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);

        System.out.println("\nLow repetition and decrescent order");
        rotations[0] = 0;
        Long timeB = rbm.makingTree(size, lisKeysB, rotations);
        rotations4[1] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);
        
        System.out.println("\nLow repetition and random order");
        rotations[0] = 0;
        Long timeC = rbm.makingTree(size, lisKeysC, rotations);
        rotations4[2] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);
        
        System.out.println("\nHigh repetition and random order");
        rotations[0] = 0;
        Long timeD = rbm.makingTree(size, lisKeysD, rotations);
        rotations4[3] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);

        return new long[]{timeA, timeB, timeC, timeD};
    }

    //Treap tree
    private long[] treapTree(int size, int[] lisKeysA, int[] lisKeysB, int[] lisKeysC, int[] lisKeysD, int[] rotations4) {
        TREAPTreeMethods ttm = new TREAPTreeMethods();
        int[] rotations = new int[1];
        
        System.out.println("\nLow repetition and crecent order");
        rotations[0] = 0;
        Long timeA = ttm.makingTree(lisKeysA, size, rotations);
        rotations4[0] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);

        System.out.println("\nLow repetition and decrescent order");
        rotations[0] = 0;
        Long timeB = ttm.makingTree(lisKeysB, size, rotations);
        rotations4[1] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);
        
        System.out.println("\nLow repetition and random order");
        rotations[0] = 0;
        Long timeC = ttm.makingTree(lisKeysC, size, rotations);
        rotations4[2] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);
        
        System.out.println("\nHigh repetition and random order");
        rotations[0] = 0;
        Long timeD = ttm.makingTree(lisKeysD, size, rotations);
        rotations4[3] = rotations[0];
        System.out.println("Rotations: " + rotations[0]);

        return new long[]{timeA, timeB, timeC, timeD};
    }
}