# GeneticAlgorithmTSP
This application uses genetic algorithm to solve the Traveling Salesman Problem. The test data comes from http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/, and the best solution is known as 10628.

The test file att48.tsp should be put at the root path of the project.

(1) The city index (Vertex in the graph) is our gene in this solution, and the number of genes is 48.

(2) An array of genes in this solution is our trait (the order in the trait is the path direction in the graph, order [1, 3, 5, 2] means the path in the graph is 1 -> 3 -> 5 -> 2), each trait includes city index from 1 to 48. 30 traits form a group.

(3) The fitness of each trait is calculated by computing the path length of this trait. Path length (Edge in the graph) is stored in a distance matrix when initiating the InitialGeneration group, fitness function will call it to compute.

(4) We will let our group evolve 1,000 times. After each evolution, we find the best fit trait and set that trait to be the first trait in the next generation, then let the other traits to evolve using a function called "Crossing", also there will be mutations.

(5) Each group will have a best trait, this is the current best solution of TSP.

(6) After each evolution, best trait will be logged in the console.

(7) After 1,000 evolution, application will log the best fit result.

(8) We have two different evolution function, the analyse of result will be logged.

