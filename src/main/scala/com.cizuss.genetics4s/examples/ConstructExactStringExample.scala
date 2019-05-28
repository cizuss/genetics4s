package com.cizuss.genetics4s.examples

import com.cizuss.genetics4s.crossover_makers.RandomCrossover
import com.cizuss.genetics4s.selectors.TournamentSelector
import com.cizuss.genetics4s.{Algorithm, Individual}

class StringInd(target: String, domain: Seq[Char]) extends Individual[Char](domain, target.length) {
  override def fitnessFnc: Double = {
    var diff = 0
    for(i <- 0 until this.geneLength) {
      if (this.genes(i) != target.charAt(i)) {
        diff += 1
      }
    }

    1 / (1 + diff).toDouble
  }

  override def toString: String = this.genes.mkString
}

/*
 Example of an instance of the algorithm that tries to construct a String
 */
object ConstructExactStringExample extends App {
  val domain: Seq[Char] = ('a' to 'z') ++ ('A' to 'Z') ++ Seq(' ', '!')
  val target: String = "This is a really complicated and long string but the algorithm should still construct it"

  val alg = new Algorithm[Char, StringInd](40, () => new StringInd(target, domain), 1).withSelector(new TournamentSelector).withCrossoverMaker(new RandomCrossover)
  val result = alg.run(maxIters = 20000)

  println(result)
}
