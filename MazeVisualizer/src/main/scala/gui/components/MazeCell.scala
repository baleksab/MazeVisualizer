package gui.components

import gui.components.CellState.{CellState, PATH, WALL}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

class MazeCell(var state: CellState, val posX: Int, posY: Int, val cellWidth: Double, val cellHeight: Double) extends Rectangle {

  width = cellWidth
  height = cellHeight

  fill = getColor

  def setState(newState: CellState): Unit = {
    state = newState
    fill = getColor
  }

  private def getColor: Color = {
    if (state == WALL)
      Color.Black
    else if (state == PATH)
      Color.White
    else
      Color.Yellow
  }

  def getPosition: (Int, Int) = {
    (posX, posY)
  }

}
