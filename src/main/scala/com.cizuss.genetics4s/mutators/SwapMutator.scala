package com.cizuss.genetics4s.mutators

import com.cizuss.genetics4s.Individual

import scala.util.Random

/*
 This mutator swaps genes at two random positions in the DNA
 */
class SwapMutator[U, T <: Individual[U]] extends Mutator[U, T]{
  override def mutateOneGene(individual: T, index: Int): Unit = {
    val rng = new Random()
    var i2 = rng.nextInt(individual.geneLength)
    while (i2 == index) i2 = rng.nextInt(individual.geneLength)

    val gene1 = individual.genes(index)
    val gene2 = individual.genes(i2)

    individual.updateGene(index, gene2)
    individual.updateGene(i2, gene1)
  }
}
