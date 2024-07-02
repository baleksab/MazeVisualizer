package algorithm.solver
import gui.components.{CellState, MazeCell}

import scala.collection.mutable
import scala.util.Random

class BreadthFirstSearch extends Solver {

  private val random = new Random()

  override def solve(mazeCells: Array[Array[MazeCell]]): Unit = {
    val rows = mazeCells.length
    val cols = mazeCells(0).length
    val startCell = mazeCells(0)(1)
    val endCell = mazeCells(rows - 1)(cols - 2)

    val queue = mutable.Queue[MazeCell]()
    queue.enqueue(startCell)
    startCell.setState(CellState.VISITED)

    while (queue.nonEmpty) {
      val current = queue.dequeue()

      if (current == endCell) {
        markPath(current)

        return;
      }

      val directions = random.shuffle(List((0, 1), (1, 0), (0, -1), (-1, 0)))
      val (x, y) = current.getPosition

      for ((dx, dy) <- directions) {
        val nextX = x + dx
        val nextY = y + dy

        if (nextX >= 0 && nextX < rows  && nextY >= 0 && nextY < cols) {
          val next = mazeCells(nextX)(nextY)
          if (next.state == CellState.PATH) {
            next.setState(CellState.VISITED)
            next.predecessor = Some(current)
            queue.enqueue(next)

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

  override def toString: String = "Breadth-First Search (BFS)"

}
