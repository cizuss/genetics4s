package com.cizuss.genetics4s

import scala.collection.mutable

class Population[U, T <: Individual[U]](populationSize: Int, construct: () => T) {
  var individuals: mutable.ArrayBuffer[T] = mutable.ArrayBuffer()

  def generateIndividuals(): Unit = {
    individuals = mutable.ArrayBuffer((1 to populationSize).map(_ => construct()): _*)
    individuals.foreach(_.generateGenes())
  }

  def addIndividual(individual: T): Unit = {
    individuals.append(individual)
  }

  def size: Int = individuals.length

  def getFittest: T = {
    individuals.maxBy(_.fitness)
  }

  def getIndividuals: Seq[T] = individuals

  def computeFitness: Unit = individuals.foreach(_.computeFitness)

  def getLeastFittestIndex: Int = {
    individuals.zipWithIndex.minBy(_._1.fitness)._2
  }

  def replaceIndividualAtIndex(index: Int, newIndividual: T): Unit = {
    individuals.update(index, newIndividual)
  }
}
