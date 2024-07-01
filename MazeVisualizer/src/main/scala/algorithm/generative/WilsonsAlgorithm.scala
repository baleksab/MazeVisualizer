package algorithm.generative
import gui.components.{CellState, MazeCell}

import scala.collection.mutable.ListBuffer
import scala.util.Random

class WilsonsAlgorithm extends Generative {

  private val random = new Random()

  override def generate(mazeCells: Array[Array[MazeCell]]): Unit = {
    val rows = mazeCells.length
    val cols = mazeCells(0).length

    val visited = ListBuffer[MazeCell]()
    val unvisited = ListBuffer[MazeCell]()

    for (i <- 1 until rows - 1; j <- 1 until cols - 1) {
      if (i % 2 != 0 && j % 2 != 0) {
        unvisited += mazeCells(i)(j)
      }
    }

    var current = unvisited(random.nextInt(unvisited.length))
    current.setState(CellState.PATH)

    unvisited -= current
    visited += current

    while (unvisited.nonEmpty) {
      val path = ListBuffer[MazeCell]()
      current = unvisited(random.nextInt(unvisited.length))
      path += current

      while (!visited.contains(current)) {
        val directions = random.shuffle(List((0, -1), (0, 1), (-1, 0), (1, 0)))
        var validPos = false

        for ((dx, dy) <- directions if !validPos) {
          val (x, y) = current.getPosition
          val wallX = x + dx
          val wallY = y + dy
          val cellX = x + dx + dx
          val cellY = y + dy + dy

          if (cellX >= 1 && cellX < rows - 1 && cellY >= 1 && cellY < cols - 1) {
            val wall = mazeCells(wallX)(wallY)
            current = mazeCells(cellX)(cellY)
            if (!path.contains(current)) {
              path += wall
              path += current
            }
            validPos = true
          }
        }
      }

      for (cell <- path) {
        visited += cell
        unvisited -= cell
        cell.setState(CellState.PATH)
        Thread.sleep(10)
      }
    }
  }

  override def toString: String = "Wilson's algorithm"

}
