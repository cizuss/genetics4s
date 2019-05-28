package com.cizuss.genetics4s.mutators

import com.cizuss.genetics4s.Individual

import scala.util.Random

trait Mutator[U, T <: Individual[U]] {
  val mutationRate: Double = 0.0075

  def mutateOneGene(individual: T, index: Int): Unit

  def mutate(individual: T): Unit = {
    val rng = new Random()
    for(i <- individual.genes.indices) {
      if (rng.nextDouble() <= mutationRate) {
        mutateOneGene(individual, i)
      }
    }
  }
}
