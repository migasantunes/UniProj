# Binary Tree vs Balanced Trees Performance Comparison

This project compares the performance of different tree data structures (Binary Tree, AVL Tree, Red-Black Tree, and Treap) under various conditions. 
The goal is to analyze insertion times and rotation counts for each tree type with different input patterns.

## Project Structure

The project consists of the following Java classes:

1. **Tree Implementations**:
   - `BinaryKey.java` and `BinaryTreeMethods.java`: Basic binary tree implementation.
   - `AVLKey.java` and `AVLTreeMethods.java`: AVL tree (self-balancing BST) implementation.
   - `RBKey.java` and `RBTreeMethods.java`: Red-Black tree implementation.
   - `TREAPKey.java` and `TREAPTreeMethods.java`: Treap (BST + heap) implementation.

2. **Main Program**:
   - `Projecto2JMLA.java`: Main driver that runs performance tests.

3. **Utilities**:
   - `ResultExporter.java`: Handles exporting results to CSV files.

## Functionalities

1. **Tree Operations**:
   - Insertion of keys with time measurement
   - Automatic balancing (for AVL, Red-Black, and Treap)
   - Rotation counting (for balancing operations)

2. **Test Scenarios**:
   - Tests with four different input patterns:
     - Low repetition, ascending order
     - Low repetition, descending order
     - Low repetition, random order
     - High repetition, random order
   - Tests with varying dataset sizes (100k, 250k, 500k, 750k, 1M elements - for Binary Tree use less) 

3. **Performance Metrics**:
   - Measures insertion time for each tree type
   - Counts rotations required for balancing
   - Exports results to CSV files for analysis

## How It Works

1. The main program (`Projecto2JMLA`) generates test datasets with different characteristics.
2. For each tree type and dataset combination:
   - Creates a tree structure
   - Inserts all elements while measuring time
   - Counts balancing rotations (where applicable)
3. Results are exported to CSV files:
   - `-resultsBinary.csv`: Binary tree results
   - `-resultsAVL.csv`: AVL tree results
   - `-resultsVP.csv`: Red-Black tree results
   - `-resultsTREAP.csv`: Treap results

## Key Features

- **Comparative Analysis**: Direct comparison of different tree structures
- **Multiple Test Cases**: Different input patterns to test various scenarios
- **Scalability Testing**: Tests with large datasets (up to 1 million elements)
- **Detailed Metrics**: Both time and operation counts are recorded
- **CSV Export**: Easy analysis of results in spreadsheet software

## Usage

1. Run `Projecto2JMLA`
2. Results will be generated in CSV files
3. Analyze the performance metrics in the output files
