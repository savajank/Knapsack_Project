package Algorithms;
//Sava Jankovic Dynamic Programming Knapsack Project


public class KnapsackDP 
{
    private int num;
    private int targetWeight;
    private int[] value;
    private int[] weight;
    private int[][] x;
    private int[][] totalValue;
    private int[][] totalWeight;

    public KnapsackDP(int num, int targetWeight, int[] value, int[] weight) {
        // assign the number of items, target weight, value array, and weight array to instance variables.
        this.num = num;
        this.targetWeight = targetWeight;
        this.value = value;
        this.weight = weight;
        // Create a 2D array to store the selected items.
        // The x[i][j] element represents whether or not item i has been selected when the knapsack has a capacity of j.
        this.x = new int[num + 1][targetWeight + 1];
        // Create a 2D array to store the total value of the selected items.
        // Represents the maximum total value when selecting items up to i with a knapsack capacity of j.
        this.totalValue = new int[num + 1][targetWeight + 1];
        this.totalWeight = new int[num + 1][targetWeight + 1];
    }
    
    private int recMemoHelper(int i, int w) {
        if (i == 0 || w == 0) { // base case: no more items or no more capacity
            return 0;
        }
        if (x[i][w] != 0) { // if already computed, return the previously computed value
            return totalValue[i][w];
        }
        if (weight[i - 1] > w) { // item cannot fit in remaining capacity
            totalValue[i][w] = recMemoHelper(i - 1, w); // don't include current item
        } else {
            int valueWithoutItem = recMemoHelper(i - 1, w); // maximum value without current item
            int valueWithItem = recMemoHelper(i - 1, w - weight[i - 1]) + value[i - 1]; // maximum value with current item
            if (valueWithoutItem > valueWithItem) { // if not including current item is better
                totalValue[i][w] = valueWithoutItem;
            } else { // including current item is better
                totalValue[i][w] = valueWithItem;
                x[i][w] = 1; // mark item as included
            }
        }
        return totalValue[i][w]; // return maximum value that can be obtained with remaining capacity and items from 1 to 'i'
    }
    
    public static String recMemo(String s) 
    {
        // Split the input string into an array of strings using whitespace as delimiter
        String[] input = s.split("\\s+");
        int num = Integer.parseInt(input[0]);
        int targetWeight = Integer.parseInt(input[1]);
        // create arrays of size num for value and weight and fill them using input array
        int[] value = new int[num];
        int[] weight = new int[num];
        for (int i = 0; i < num; i++) {
            value[i] = Integer.parseInt(input[2 * i + 2]);
            weight[i] = Integer.parseInt(input[2 * i + 3]);
        }
        // Create an instance of KnapsackDP class and pass necessary parameters
        KnapsackDP knapsack = new KnapsackDP(num, targetWeight, value, weight);
        // call recMemoHelper method on the KnapsackDP instance and store the returned value
        int totalValue = knapsack.recMemoHelper(num, targetWeight);
        int totalWeight = knapsack.totalWeight[num][targetWeight];
        // create a StringBuilder object to construct the output string
        StringBuilder resultBuilder = new StringBuilder();
        // Iterate  
        for (int i = num; i > 0; i--) {
            resultBuilder.append(knapsack.x[i][targetWeight]).append(", ");
            targetWeight -= knapsack.x[i][targetWeight] * weight[i - 1];
        }
        return resultBuilder.append("total value = ").append(totalValue)
                            .append(", total weight = ").append(totalWeight)
                            .toString();
    }


    public KnapsackDP(String input) {
        // Split the input string by whitespace
        String[] tokens = input.split(" ");
        // Extract the number of items and target weight from the input
        this.num = Integer.parseInt(tokens[0]);
        this.targetWeight = Integer.parseInt(tokens[1]);
        this.value = new int[num];
        this.weight = new int[num];
        this.x = new int[num][targetWeight + 1];
        // Create 2D arrays to store the maximum total value and weight for selected items.
        this.totalValue = new int[num + 1][targetWeight + 1];
        this.totalWeight = new int[num + 1][targetWeight + 1];
        // Extract values and weights of each item from the input and store them in the respective arrays.
        for (int i = 0; i < num; i++) {
            this.value[i] = Integer.parseInt(tokens[2*i + 2]);
            this.weight[i] = Integer.parseInt(tokens[2*i + 3]);
        }
    }
    

    public static String nonRec(String s) 
    {
        KnapsackDP knapsack = new KnapsackDP(s);
        int n = knapsack.num;
        int W = knapsack.targetWeight;
    
        // Initialize base case
        for (int j = 0; j <= W; j++) {
            if (j >= knapsack.weight[0]) {
                knapsack.totalValue[0][j] = knapsack.value[0];
                knapsack.totalWeight[0][j] = knapsack.weight[0];
            }
        }
    
        // Fill in the rest of the table
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= W; j++) {
                // Copy values from the previous row
                knapsack.totalValue[i][j] = knapsack.totalValue[i-1][j];
                knapsack.totalWeight[i][j] = knapsack.totalWeight[i-1][j];
    
                // Include item i
                if (j >= knapsack.weight[i]) {
                    int newValue = knapsack.totalValue[i-1][j-knapsack.weight[i]] + knapsack.value[i];
                    if (newValue > knapsack.totalValue[i][j]) {
                        knapsack.totalValue[i][j] = newValue;
                        knapsack.totalWeight[i][j] = knapsack.totalWeight[i-1][j-knapsack.weight[i]] + knapsack.weight[i];
                    }
                }
            }
        }
    
        // Extract optimal subset
        int[] ind = new int[n];
        int j = W;
        // Start at the maximum weight and iterate backwards through the items
        for (int i = n - 1; i >= 0; i--) {
            if (i == 0) {
                ind[i] = (knapsack.totalWeight[i][j] > 0) ? 1 : 0;
            } else if (knapsack.totalWeight[i][j] > knapsack.totalWeight[i-1][j]) {
                ind[i] = 1;
                j -= knapsack.weight[i]; // update the remaining weight
            } else {
                ind[i] = 0; // set to 0 if item is not in the knapsack
            }
        }

        // Construct output string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(ind[i] + ", ");
        }
        sb.append("total value = " + knapsack.totalValue[n-1][W] + ", ");
        sb.append("total weight = " + knapsack.totalWeight[n-1][W]);
        return sb.toString();

    }
    

}
