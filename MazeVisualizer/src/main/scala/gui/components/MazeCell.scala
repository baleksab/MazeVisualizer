package gui.components

import gui.components.CellState.{CellState, PATH, WALL}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class MazeCell(var state: CellState, val posX: Int, posY: Int, val cellWidth: Double, val cellHeight: Double) extends Rectangle {
  var predecessor: Option[MazeCell] = None

  width = cellWidth
  height = cellHeight

  fill = getColor

  def setState(newState: CellState): Unit = {
    state = newState
    fill = getColor
  }

  private def getColor: Color = {
    state match {
      case CellState.WALL => Color.Black
      case CellState.PATH => Color.White
      case CellState.VISITED => Color.Blue
      case CellState.PART_OF_SOLUTION => Color.Red
    }
  }

  def getPosition: (Int, Int) = {
    (posX, posY)
  }

}
