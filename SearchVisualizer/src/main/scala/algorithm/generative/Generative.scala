package algorithm.generative

import gui.MazeCell

trait Generative {

  def generate(mazeCells: Array[Array[MazeCell]]): Unit

}
