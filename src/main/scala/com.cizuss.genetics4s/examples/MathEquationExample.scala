package com.cizuss.genetics4s.examples

import com.cizuss.genetics4s.selectors.RouletteWheelSelector
import com.cizuss.genetics4s.{Algorithm, Individual}

class Ind extends Individual[Int](MathEquationExample.domain, 4) {
  def a: Int = genes(0)
  def b: Int = genes(1)
  def c: Int = genes(2)
  def d: Int = genes(3)

  override def fitnessFnc: Double = {
    val diff = Math.abs(30 - (a + 2*b + 3*c + 4*d))

    1 / (1 + diff).toDouble
  }

  override def toString: String = {
    s"$a $b $c $d"
  }
}

/*
Example of an instance of the Algorithm that tries to solve the equation a + 2b + 3c + 4d = 30
 */
object MathEquationExample extends App {
  val domain = List.range(0, 30)
  val alg = new Algorithm[Int, Ind](40, () => new Ind(), 1).withSelector(new RouletteWheelSelector)

  val result = alg.run(maxIters = 500000)
  println(result)

}
