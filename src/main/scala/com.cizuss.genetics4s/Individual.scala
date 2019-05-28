package com.cizuss.genetics4s

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

abstract class Individual[T](val domain: Seq[T], val geneLength: Int) extends Cloneable {
  private val geneFactory = new GeneFactory(domain)

  protected var genes: mutable.ArrayBuffer[T] = mutable.ArrayBuffer()

  var fitness: Double = 0

  def fitnessFnc: Double

  def computeFitness: Unit = {
    this.fitness = fitnessFnc
  }

  def generateGenes() = {
    clearGenes()

    (1 to geneLength).foreach { _ =>
      appendGene(geneFactory.generateGene())
    }
  }

  def initGenes(): Unit = {
    genes = mutable.ArrayBuffer()
  }

  def clearGenes(): Unit = genes.clear()
  def appendGene(gene: T) = genes.append(gene)
  def updateGene(index: Int, gene: T) = genes.update(index, gene)
  def getGenes: ArrayBuffer[T] = genes

  def cloneMe(): Individual[T] = super.clone().asInstanceOf[Individual[T]]
}