package com.cizuss.genetics4s.crossover_makers
import com.cizuss.genetics4s.Individual

import scala.collection.mutable
import scala.util.Random

/*
This class implements the Crossover Point based crossover, where a random index in the DNA is selected,
genes up until the index are taken from the first parent and genes starting from the index are taken from
the second parent
 */
class CrossoverPointCrossoverMaker[U, T <: Individual[U]] extends CrossoverMaker[U, T]{
  override def performCrossover(parent1: T, parent2: T): T = {
    val offspring = parent1.cloneMe().asInstanceOf[T]
    val crossoverPoint = new Random().nextInt(parent1.geneLength)

    offspring.genes = mutable.ArrayBuffer[U]()

    for (i <- 0 to crossoverPoint) {
      offspring.appendGene(parent1.genes(i))
    }
    for (i <- crossoverPoint+1 until parent2.geneLength) {
      offspring.appendGene(parent2.genes(i))
    }
    offspring
  }
}
