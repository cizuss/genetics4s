package com.cizuss.genetics4s.mutators
import com.cizuss.genetics4s.Individual

import scala.util.Random

/*
 This mutator takes a gene at a random positions and replaces it with another random one from the problem domain
 */
class RandomGeneReplaceMutator[U, T <: Individual[U]] extends Mutator[U, T] {
  override def mutateOneGene(individual: T, index: Int): Unit = {
    def generateNewGene(rng: Random): U = {
      individual.domain(rng.nextInt(individual.domain.length))
    }

    val rng = new Random()
    var newGene = generateNewGene(rng)
    if (individual.domain.length > 1) {
      while (newGene == individual.genes(index)) {
        newGene = generateNewGene(rng)
      }
    }

    individual.updateGene(index, newGene)
  }
}
