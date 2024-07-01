package algorithm.generative

import gui.components.MazeCell

trait Generative {

  def generate(mazeCells: Array[Array[MazeCell]]): Unit

}
