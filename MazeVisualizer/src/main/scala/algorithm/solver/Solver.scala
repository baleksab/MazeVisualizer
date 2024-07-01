package algorithm.solver

import gui.components.MazeCell

trait Solver {

  def solve(mazeCells: Array[Array[MazeCell]]): Unit
  
}
