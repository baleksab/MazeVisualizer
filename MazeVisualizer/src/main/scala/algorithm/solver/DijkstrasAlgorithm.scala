package algorithm.solver
import gui.components.{CellState, MazeCell}

import scala.collection.mutable
import scala.util.Random

class DijkstrasAlgorithm extends Solver {

  var random = new Random()

  override def solve(mazeCells: Array[Array[MazeCell]]): Unit = {
    val rows = mazeCells.length
    val cols = mazeCells(0).length
    val startCell = mazeCells(0)(1)
    val endCell = mazeCells(rows - 1)(cols - 2)

    val pq = mutable.PriorityQueue[MazeCell]()(Ordering.by(_.distance))
    val visited = mutable.Set[MazeCell]()

    pq.enqueue(startCell)
    visited.addOne(startCell)
    startCell.distance = 0

    while (pq.nonEmpty) {
      val current = pq.dequeue()

      if (current == endCell) {
        markPath(current)
        return
      }

      current.setState(CellState.VISITED)

      val directions = random.shuffle(List((0, 1), (1, 0), (0, -1), (-1, 0)))
      val (x, y) = current.getPosition

      for ((dx, dy) <- directions) {
        val nextX = x + dx
        val nextY = y + dy

        if (nextX >= 0 && nextX < rows && nextY >= 0 && nextY < cols) {
          val next = mazeCells(nextX)(nextY)
          val newDistance = current.distance + 1

          if (next.state == CellState.PATH && newDistance < next.distance) {
            next.predecessor = Some(current)
            next.distance = newDistance

            if (!visited.contains(next)) {
              pq.enqueue(next)
              visited.add(next)
              next.setState(CellState.VISITED)

              Thread.sleep(10)
            }
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

  override def toString: String = "Dijkstra's Algorithm"

}
