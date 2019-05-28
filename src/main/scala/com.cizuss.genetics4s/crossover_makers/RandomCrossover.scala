package com.cizuss.genetics4s.crossover_makers

import com.cizuss.genetics4s.Individual

import scala.util.Random

/*
This Crossover implementation randomly decides for each gene which parent to select (with equal probability)
 */
class RandomCrossover[U, T <: Individual[U]] extends CrossoverMaker[U, T] {
  override def performCrossover(parent1: T, parent2: T): T = {
    val offspring = parent1.cloneMe().asInstanceOf[T]

    offspring.initGenes()
    offspring.clearGenes()

    val rng = new Random()

    for(i <- 0 until parent1.geneLength) {
      if (rng.nextDouble() <= 0.5) {
        offspring.appendGene(parent1.getGenes(i))
      } else {
        offspring.appendGene(parent2.getGenes(i))
      }
    }

    offspring
  }
}
