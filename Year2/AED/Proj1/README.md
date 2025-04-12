# Sequence Analysis and Performance Benchmarking
## What It Does

This program generates shuffled sequences of consecutive integers with one missing value and applies three different algorithms to find the missing number. 
It measures the execution time of each method and visualizes their computational complexity using regression plots.

## Key Functionalities

### Algorithms Implemented

1. **Exhaustive Search (`exaustivo1`)** – `O(n²)`
   - Checks every number in the expected range to see if it’s missing from the list using nested iteration.
   
2. **Sorting-Based Search (`ordenamento`)** – `O(n log n)`
   - Sorts the list and then scans for a gap between adjacent numbers.

3. **Mathematical Approach (`elaborado`)** – `O(n)`
   - Uses the arithmetic series formula to calculate the expected sum and subtracts the actual sum to find the missing number.

## Features

- **Sequence Generator**:
  - Creates a random list of consecutive integers with one missing element.

- **Time Benchmarking**:
  - Each algorithm is tested on lists of increasing size, and average runtime over 5 runs is calculated.

- **Complexity Analysis**:
  - Generates performance graphs and regression curves to model each algorithm:
    - Quadratic regression for exhaustive search.
    - `n log n` regression for sorting-based.
    - Linear regression for the mathematical method.
  - Displays R² values to assess regression fit quality.

- **Results Table**:
  - A summary table is generated with the average runtimes for all algorithms across different list sizes.

## Visualization

- Utilizes `matplotlib` for:
  - Runtime plots
  - Regression curves
  - R² coefficient display
  - Final result table as a graphic

## Libraries used

- Python 3
- NumPy
- Matplotlib
- SciPy
- Time
- Random
