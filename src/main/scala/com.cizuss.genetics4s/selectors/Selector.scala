package com.cizuss.genetics4s.selectors

import com.cizuss.genetics4s.{Individual, Population}

trait Selector[U, T <: Individual[U]] {
  def select(population: Population[U, T]): (T, T)
}
