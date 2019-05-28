package com.cizuss.genetics4s.selectors
import com.cizuss.genetics4s.{Individual, Population}

import scala.collection.mutable

/*
This Selector selects the best two individuals of a population (by their fitness)
 */
class BestTwoSelector[U, T <: Individual[U]] extends Selector[U, T] {
  override def select(population: Population[U, T]): (T, T) = {
    implicit val individualOrd: Ordering[T] = (x: T, y: T) => {
      (x.fitness - y.fitness).toInt
    }
    val pq = mutable.PriorityQueue[T]()
    pq.enqueue(population.getIndividuals: _*)

    (pq.dequeue(), pq.dequeue())
  }
}
