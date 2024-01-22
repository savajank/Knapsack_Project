<h1>The Knapsack Problem<h1>

<h2>What is the Knapsack Problem?</h2>

The Knapsack Problem is like deciding what to pack in a suitcase when you can't take everything. You want to pack the most valuable items without exceeding the weight limit of the suitcase. The goal is to get the maximum value from the items you choose to pack. It's essential to maximize the resources you have without going overweight... which is exactly what this project aims to achieve through Dynamic Programming and Greedy Algorithms. 

<h2>Dynamic Programming</h2>

Dynamic Programming is a method where a big problem is solved by breaking it down into smaller problems, solving each small problem just once, and saving their answers. This way, you don't have to solve the same small problem over and over again, making the whole process more efficient.

Throughout dynamic programming, the Knapsack problem was approached throughout the project as it could be solved through recursion with memoization and would output a string that would represent a solution to the problem. This solution would ultimately be the result of smaller problems being solved one at a time. 

<h2>Greedy Algorithm</h2>

A Greedy Algorithm is anintuitive method used to find a good solution for a problem by making the best choice at each step. It's always picking the biggest current scenario of the solution at the time - you make the choice that seems best at that moment, without worrying about the future. This method doesn't always give the perfect solution, but it often leads to solutions that are good enough in a practical sense.

Each object of the class represents an instance of our knapsack problem. Two static methods are represented through this algorithm, and would eventually solve the fractional knapsack problem specified by the input string using the algorithm. The public method fractional has a KnapsackGreedy object, corresponding to the problem specified by the input. 
