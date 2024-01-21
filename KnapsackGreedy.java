// Sava Jankovic Knapsack Challenge (GReedy)

import java.util.Arrays;

public class KnapsackGreedy 
{
    private int n;
    private int W;
    private int[] v;
    private int[] w;

    public KnapsackGreedy(String input) {
        // Split the input string into an array of string tokens, separated by whitespace ("\s+").
        String[] greedtokens = input.split("\\s+");
        // The first token is the number of items in the knapsack problem kpins.
        n = Integer.parseInt(greedtokens[0]);
        W = Integer.parseInt(greedtokens[1]);
        v = new int[n];
        w = new int[n];
        // Loop through the remaining tokens in the array, which contain the values and weights of each item.
        for (int i = 0; i < n; i++) {
            // The value of the i-th item is located at token index i*2 + 2.
            v[i] = Integer.parseInt(greedtokens[i * 2 + 2]);
            // The weight of the i-th item is located at token index i*2 + 3.
            w[i] = Integer.parseInt(greedtokens[i * 2 + 3]);
        }
    }
    

    public static String fractional(String input) {
        // Create a new KnapsackGreedy instance based on the input string.
        KnapsackGreedy kpins = new KnapsackGreedy(input);
        // Calculate the ratio of value to weight for each item in the knapsack instance.
        double[] ratio = new double[kpins.n];
        for (int i = 0; i < kpins.n; i++) {
            ratio[i] = (double) kpins.v[i] / kpins.w[i];
        }
        // Create an index array to keep track of the order of the items in the ratio array.
        int[] ind = new int[kpins.n];
        for (int i = 0; i < kpins.n; i++) {
            ind[i] = i;
        }
        // Sort the index array based on the corresponding values in the ratio array, in descending order.
        for (int i = 0; i < kpins.n - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < kpins.n; j++) {
                if (ratio[ind[j]] > ratio[ind[maxIndex]]) {
                    maxIndex = j;
                }
            }
            int temp = ind[maxIndex];
            ind[maxIndex] = ind[i];
            ind[i] = temp;
        }
        // Initializing the variables for total weight and value.
        int weight = 0;
        double value = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < kpins.n; i++) {
            // If adding the next item would not exceed the knapsack weight capacity, add it completely.
            if (weight + kpins.w[ind[i]] <= kpins.W) {
                weight += kpins.w[ind[i]];
                value += kpins.v[ind[i]];
                sb.append("1");
            } else { 
                // Otherwise, add a fractional amount of the next item to fill the knapsack completely.
                double fraction = (kpins.W - weight) * 1.0 / kpins.w[ind[i]];
                sb.append(fraction);
                value += ratio[ind[i]] * (kpins.W - weight);
                weight = kpins.W;
                break;
            }
        }
        // Build and return the final solution string.
        return sb.toString() + ", total value = " + value + ", total weight = " + weight;
    }
    
    public static String greedy01(String input) 
    {
        String[] values = input.split(" ");
        // Parse the first two substrings as ints.
        int n = Integer.parseInt(values[0]);
        int W = Integer.parseInt(values[1]);

    
        // Initialize arrays to store values and weights
        int[] v = new int[n];
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            v[i] = Integer.parseInt(values[2*i + 2]);
            w[i] = Integer.parseInt(values[2*i + 3]);
        }
        // Compute the ratio of value to weight for each item
        double[] ratio = new double[n];
        for (int i = 0; i < n; i++) {
            ratio[i] = (double) v[i] / w[i];
        }
        // Create an array to store the indices of items in descending order of ratio
        int[] index = new int[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        // Sort the indices array in descending order of ratio
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (ratio[index[i]] < ratio[index[j]]) {
                    int temp = index[i];
                    index[i] = index[j];
                    index[j] = temp;
                }
            }
        }
    
        // Fill the knapsack with items in descending order of ratio until full
        int[] s = new int[n];
        int weight = 0;
        int value = 0;
        for (int i = 0; i < n; i++) {
            if (weight + w[index[i]] <= W) 
            {
                s[index[i]] = 1;
                weight += w[index[i]];
                value += v[index[i]];
            } else {
                s[index[i]] = 0;
            }
        }
    
        // Build the output string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) 
        {
            sb.append(s[i]).append(", "); 
        }
        sb.append("total value = ").append(value).append(", ");  // Outputting the total weight and value 
        sb.append("total weight = ").append(weight);
        return sb.toString();
    }
}    