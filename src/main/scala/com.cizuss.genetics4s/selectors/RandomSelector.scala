package com.cizuss.genetics4s.selectors

import com.cizuss.genetics4s.{Individual, Population}

import scala.util.Random

/*
This selector selects two random individuals of a population
 */
class RandomSelector[U, T <: Individual[U]] extends Selector[U, T] {
  override def select(population: Population[U, T]): (T, T) = {
    val rng = new Random()
    val ind1 = rng.nextInt(population.size)
    var ind2 = rng.nextInt(population.size)

    while (ind2 == ind1) {
      ind2 = rng.nextInt(population.size)
    }

    (population.getIndividuals(ind1), population.getIndividuals(ind2))
  }
}
