package com.cizuss.genetics4s

import scala.util.Random

class GeneFactory[T](domain: Seq[T]) {
  def generateGene(): T = {
    val idx = new Random().nextInt(domain.length)
    domain(idx)
  }
}
