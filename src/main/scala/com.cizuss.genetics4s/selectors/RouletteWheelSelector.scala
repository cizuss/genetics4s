package com.cizuss.genetics4s.selectors

import com.cizuss.genetics4s.{Individual, Population}

import scala.util.Random

/*
This selects two individuals with probability based on their fitness
 */
class RouletteWheelSelector[U, T <: Individual[U]] extends Selector[U, T] {
  override def select(population: Population[U, T]): (T, T) = {
    def selectOne(sum: Double): T = {
      val rng = new Random()
      val num = sum * rng.nextDouble()

      var acc = 0d
      var ind = -1

      while (num >= acc) {
        ind += 1
        acc += population.individuals(ind).fitness
      }

      population.individuals(ind)
    }

    val sum = population.getIndividuals.foldLeft(0d)((acc, ind) => acc + ind.fitness)
    val i1 = selectOne(sum)
    var i2 = selectOne(sum)

    while(i2 == i1) i2 = selectOne(sum)

    (i1, i2)
  }
}
