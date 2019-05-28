package com.cizuss.genetics4s.crossover_makers

import com.cizuss.genetics4s.Individual

/*
Generic trait for the algorithm of crossover between two selected individuals
 */
trait CrossoverMaker[U, T <: Individual[U]] {
  def performCrossover(parent1: T, parent2: T): T
}
