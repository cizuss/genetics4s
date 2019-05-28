package com.cizuss.genetics4s.selectors
import com.cizuss.genetics4s.{Individual, Population}

import scala.util.Random

/*
This is the Tournament Selection algorithm
 */
class TournamentSelector[U, T <: Individual[U]] extends Selector[U, T] {
  override def select(population: Population[U, T]): (T, T) = {
    val tournamentSize: Int = Math.min(10, population.size / 3)
    val rng = new Random()

    val shuffled = rng.shuffle(population.getIndividuals)
    val chunk1 = shuffled.take(tournamentSize)
    val chunk2 = shuffled.takeRight(tournamentSize)

    (chunk1.maxBy(_.fitness), chunk2.maxBy(_.fitness))
  }
}
