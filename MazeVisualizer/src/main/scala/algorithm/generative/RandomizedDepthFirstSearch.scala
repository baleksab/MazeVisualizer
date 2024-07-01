package algorithm.generative

import gui.components.{CellState, MazeCell}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Stack
import scala.util.Random

class RandomizedDepthFirstSearch extends Generative {

  private val random = new Random()

  override def generate(mazeCells: Array[Array[MazeCell]]): Unit = {
    val rows = mazeCells.length
    val cols = mazeCells(0).length

    val startCell = mazeCells(1)(1)
    val visitedCells = ListBuffer[MazeCell]()
    val stack = mutable.Stack[MazeCell]()

    dfs(startCell, mazeCells, rows, cols, visitedCells, stack)
  }

  private def dfs(cell: MazeCell, mazeCells: Array[Array[MazeCell]], rows: Int, cols: Int, visitedCells: ListBuffer[MazeCell], stack: mutable.Stack[MazeCell]): Unit = {
    cell.setState(CellState.PATH)
    visitedCells.addOne(cell)
    stack.push(cell)

    while (stack.nonEmpty) {
      val cell: MazeCell = stack.pop()

      val directions = random.shuffle(List(
        (0, -1), (0, 1), (-1, 0), (1, 0)
      ))

      for ((dx, dy) <- directions) {
        val (x, y) = cell.getPosition
        val neighborX = x + dx + dx
        val neighborY = y + dy + dy
        val wallX = x + dx
        val wallY = y + dy

        if (neighborX >= 1 && neighborX < rows - 1 && neighborY >= 1 && neighborY < cols - 1) {
          val neighbor: MazeCell = mazeCells(neighborX)(neighborY)
          val wall: MazeCell = mazeCells(wallX)(wallY)

          if (neighbor != null && wall != null && !visitedCells.contains(neighbor)) {
            stack.push(cell)
            wall.setState(CellState.PATH)
            neighbor.setState(CellState.PATH)
            visitedCells.addOne(neighbor)
            stack.push(neighbor)

            Thread.sleep(10)
          }
        }
      }
    }
  }

  override def toString: String = "Randomized depth-first search (Iterative)"

}
