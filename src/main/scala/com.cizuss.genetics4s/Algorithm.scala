package com.cizuss.genetics4s

import com.cizuss.genetics4s.crossover_makers.{CrossoverMaker, RandomCrossover}
import com.cizuss.genetics4s.mutators.{Mutator, RandomGeneReplaceMutator}
import com.cizuss.genetics4s.selectors.{Selector, TournamentSelector}

import scala.util.Random

class Algorithm[U, T <: Individual[U]](populationSize: Int, construct: () => T, fitnessThreshold: Double, elitism: Boolean = false) {
  private var selector: Selector[U, T] = new TournamentSelector()
  private var crossoverMaker: CrossoverMaker[U, T] = new RandomCrossover()
  private var mutator: Mutator[U, T] = new RandomGeneReplaceMutator()
  private var crossoverRate: Double = 0.7

  def withSelector(selector: Selector[U, T]): Algorithm[U, T] = {
    this.selector = selector
    this
  }

  def withCrossoverMaker(crossoverMaker: CrossoverMaker[U, T]): Algorithm[U, T] = {
    this.crossoverMaker = crossoverMaker
    this
  }

  def withMutator(mutator: Mutator[U, T]): Algorithm[U, T] = {
    this.mutator = mutator
    this
  }

  def withCrossoverRate(crossoverRate: Double) = {
    this.crossoverRate = crossoverRate
    this
  }

  def run(threshold: Double = fitnessThreshold, maxIters: Int = 10000): T = {
    var population: Population[U, T] = new Population(populationSize, construct)
    population.generateIndividuals()
    population.computeFitness

    var iter = 0
    var fittest = population.getFittest
    val rng = new Random()

    while(fittest.fitness < threshold && iter < maxIters) {
      iter+=1

      val newPopulation: Population[U, T] = new Population(populationSize, construct)

      for(i <- 1 to (populationSize / 2)) {
        val (parent1: T, parent2: T) = selector.select(population)

        val (child1, child2) = if (rng.nextDouble() <= crossoverRate) {
          (crossoverMaker.performCrossover(parent1, parent2), crossoverMaker.performCrossover(parent1, parent2))
        } else {
          (parent1, parent2)
        }

        mutator.mutate(child1)
        mutator.mutate(child2)

        newPopulation.addIndividual(child1)
        newPopulation.addIndividual(child2)
      }

      population = newPopulation
      population.computeFitness
      fittest = population.getFittest
    }

    fittest
  }
}