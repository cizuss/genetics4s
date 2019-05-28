package com.cizuss.genetics4s.examples

import java.time.Instant

import com.cizuss.genetics4s.mutators.Mutator
import com.cizuss.genetics4s.{Algorithm, Individual}

import scala.util.Random

case class Item(value: Double, weight: Int)

class KnapsackInd extends Individual[Boolean](KnapsackExample.domain, KnapsackExample.items.length) {
  var totalWeight: Int = 0
  def weight: Int = {
    var totalWeight = 0
    for (i <- 0 until genes.length) {
      if (genes(i)) {
        totalWeight += KnapsackExample.items(i).weight
      }
    }
    totalWeight
  }

  override def fitnessFnc: Double = {
    var itemsValueSum = 0d
    var totalWeight = 0d
    for (i <- 0 until genes.length) {
      if (genes(i)) {
        itemsValueSum += KnapsackExample.items(i).value
        totalWeight += KnapsackExample.items(i).weight
      }
    }

    if (totalWeight > KnapsackExample.weight) 0d else itemsValueSum / KnapsackExample.dpSol
  }

  override def generateGenes(): Unit = {
    clearGenes()

    var accWeight = 0
    val rng = new Random()

    for(i <- 0 until KnapsackExample.items.length) {
      val canTake = (accWeight + KnapsackExample.items(i).weight) <= KnapsackExample.weight
      if (!canTake) {
        appendGene(false)
      } else {
        val take = rng.nextDouble() <= 0.5d
        if (take) accWeight += KnapsackExample.items(i).weight
        appendGene(take)
      }
    }
  }

  override def appendGene(gene: Boolean): Unit = {
    super.appendGene(gene)
    if (gene) {
      val idx = genes.length - 1
      totalWeight += KnapsackExample.items(idx).weight
    }
  }

  override def updateGene(index: Int, gene: Boolean): Unit = {
    val oldGene = genes(index)
    if (oldGene && !gene) {
      totalWeight -= KnapsackExample.items(index).weight
    } else if (!oldGene && gene) {
      totalWeight += KnapsackExample.items(index).weight
    }
    super.updateGene(index, gene)
  }

  override def clearGenes(): Unit = {
    super.clearGenes()
    totalWeight = 0
  }

  override def toString: String = {
    var itemsValueSum = 0d
    for (i <- 0 until genes.length) {
      if (genes(i)) {
        itemsValueSum += KnapsackExample.items(i).value
      }
    }
    val s2 = itemsValueSum.toString

    s2
  }
}

object DPSolution {
  def solve(items: Seq[Item], weight: Int): Double = {
    val now = Instant.now().toEpochMilli

    val m = Array.ofDim[Double](items.length + 1, weight + 1)

    for(i <- 1 to weight) m(0)(i) = 0

    for(i <- 1 to items.length) {
      for(j <- 1 to weight) {
        val w = items(i-1).weight
        val v = items(i-1).value
        if(w > j) {
          m(i)(j) = m(i-1)(j)
        } else {
          m(i)(j) = Math.max(m(i-1)(j-w) + v, m(i-1)(j))
        }
      }
    }

    val after = Instant.now().toEpochMilli
    println(s"Dynamic programming executed in ${after - now} ms")

    m(items.length)(weight)
  }
}

object KnapsackMutator extends Mutator[Boolean, KnapsackInd] {
  override def mutateOneGene(individual: KnapsackInd, index: Int): Unit = {
    val gene = individual.getGenes(index)
    if (gene) {
      individual.updateGene(index, false)
    } else {
      val w = individual.totalWeight
      if ((w + KnapsackExample.items(index).weight) <= KnapsackExample.weight) {
        individual.updateGene(index, true)
      }
    }
  }
}

/*
This is an example of the 0/1 Knapsack Problem. You can see that the Genetic Algorithm greatly outperforms
the Dynamic Programming one for problems where the Knapsack capacity is big.
 */
object KnapsackExample extends App {
  val domain: Seq[Boolean] = Seq(true, false)
  val rng = new Random()
  val items = (1 to 10).map { _ =>
    val value = 200 + rng.nextInt(400)
    val weight = 500000 + rng.nextInt(300000)
    Item(value, weight)
  }
  val weight = 7000000
  val sum = items.foldLeft(0d)((acc, item) => acc + item.value)
  val dpSol = DPSolution.solve(items, weight)

  val alg = new Algorithm[Boolean, KnapsackInd](50, () => new KnapsackInd, 1).withMutator(KnapsackMutator)

  val now = Instant.now()
  val solution = alg.run(maxIters = 10000)
  val after = Instant.now()

  println(s"Genetic Algorithm executed in ${after.toEpochMilli - now.toEpochMilli} ms")

  println("Genetic algorithm solution = ", solution)
  println(solution.fitness)
  println("DP solution = ", dpSol)
}
