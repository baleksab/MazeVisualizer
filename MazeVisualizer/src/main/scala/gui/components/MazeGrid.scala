package gui.components

import gui.components.{CellState, MazeCell}
import scalafx.Includes.*
import scalafx.beans.property.{DoubleProperty, IntegerProperty}
import scalafx.scene.control.Button
import scalafx.scene.layout.{GridPane, VBox}

class MazeGrid extends GridPane {

  private val rows = 51
  private val cols = 51

  private val cellWidth = 800 / rows
  private val cellHeight = 800 / cols

  private val cells: Array[Array[MazeCell]] = Array.ofDim(rows, cols)

  private def generateCells(): Unit = {
    children.clear()

    for (i <- 0 until rows; j <- 0 until cols) {
      val cell = new MazeCell(CellState.WALL, i, j, cellWidth, cellHeight)  // Start with all cells as walls

      cells(i)(j) = cell

      add(cell, i, j)
    }
  }

  def resetCells(): Unit = {
    for (i <- 0 until rows; j <- 0 until cols) {
      val cell = cells(i)(j)
      cell.setState(CellState.WALL)
      cell.predecessor = None
    }
  }

  def getCells: Array[Array[MazeCell]] = {
    cells
  }

  generateCells()

}
