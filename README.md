### Genetics4s - A Scala library for genetic algorithms

## Steps to use this library

1. Decide how you would represent the DNA of an individual. For example, for the 0/1 Knapsack Problem where you have `N` items,
you would like to have a `Boolean` for each of the item, which will mean if you take that item or not. This means your DNA
will be an array of `Boolean`s. Let's call this type `GeneType`.

2. Decide the domain a gene can take values from. This domain will be used in the population generation step. For the 0/1
Knapsack Problem, the domain will be `Seq(true, false)`.

3. Decide the length of an individual's DNA.

4. Write a class that extends `Individual[GeneType]`. For the 0/1 Knapsack Problem, `GeneType` will be `Boolean`. Let's call
this class `Ind`.

5. Implement the `def fitnessFnc: Double` method in your newly created class.

6. Instantiate a new instance of `Algorithm[GeneType, Ind]`. You can give it parameters for the population size, the fitness
threshold for the algorithm to stop, custom strategies for selection, crossover making or mutating.

7. Call the `run` method on the algorithm. You can give it parameters for the maximum number of generations to run 
or for the fitness threshold.

8. Finally, you can look at all the examples provided.

## TODOS
- [x] Write initial version
- [ ] Publish to maven
- [ ] Do some code cleanup / refactor
- [ ] Provide more examples of usage
- [ ] Provide more strategies for crossover/selection/mutation
- [ ] WRITE TESTS!! :)