package algorithm.solver
import gui.components.{CellState, MazeCell}

import scala.collection.mutable
import scala.util.Random

class DepthFirstSearch extends Solver {

  val random = new Random()

  override def solve(mazeCells: Array[Array[MazeCell]]): Unit = {
    val rows = mazeCells.length
    val cols = mazeCells(0).length
    val startCell = mazeCells(0)(1)
    val endCell = mazeCells(rows - 1)(cols - 2)

    val stack = mutable.Stack[MazeCell]()

    stack.push(startCell)
    startCell.setState(CellState.VISITED)

    while (stack.nonEmpty) {
      val current = stack.pop()

      if (current == endCell) {
        markPath(current)

        return
      }

      val (x, y) = current.getPosition
      val directions = random.shuffle(List((0, 1), (1, 0), (0, -1), (-1, 0)))

      for ((dx, dy) <- directions) {
        val nextX = x + dx
        val nextY = y + dy

        if (nextX >= 0 && nextX < rows  && nextY >= 0 && nextY < cols) {
          val neighbor = mazeCells(nextX)(nextY)
          if (neighbor.state == CellState.PATH) {
            neighbor.setState(CellState.VISITED)
            neighbor.predecessor = Some(current)
            stack.push(neighbor)
            Thread.sleep(10)
          }
        }
      }
    }
  }

  private def markPath(endCell: MazeCell): Unit = {
    var current = endCell
    while (current.predecessor.isDefined) {
      current.setState(CellState.PART_OF_SOLUTION)
      current = current.predecessor.get

      Thread.sleep(10)
    }
    current.setState(CellState.PART_OF_SOLUTION)
  }

  override def toString: String = "Depth-First Search (DFS)"

}
