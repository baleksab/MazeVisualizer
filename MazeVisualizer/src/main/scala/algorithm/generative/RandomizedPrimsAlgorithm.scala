package algorithm.generative
import gui.components.{CellState, MazeCell}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.Random

class RandomizedPrimsAlgorithm extends Generative {

  private val random = new Random()

  override def generate(mazeCells: Array[Array[MazeCell]]): Unit = {
    val rows = mazeCells.length
    val cols = mazeCells(0).length

    val startCell = mazeCells(1)(1)
    startCell.setState(CellState.PATH)

    val visitedCells = ListBuffer[MazeCell](startCell)
    val wallList = ListBuffer[MazeCell]()

    addWalls(startCell, mazeCells, wallList, visitedCells)

    while (wallList.nonEmpty) {
      val wall: MazeCell = wallList(random.nextInt(wallList.length))

      val (x, y) = wall.getPosition

      val directions = random.shuffle(List(
        (0, -1), (0, 1), (-1, 0), (1, 0)
      ))

      var connected = false

      for ((dx, dy) <- directions if !connected) {
        val cell1X = x + dx
        val cell1Y = y + dy

        if (cell1X >= 1 && cell1X < rows - 1 && cell1Y >= 1 && cell1Y < cols - 1) {
          val cell1 = mazeCells(cell1X)(cell1Y)

          val cell2X = x - dx
          val cell2Y = y - dy

          if (cell2X >= 1 && cell2X < rows - 1 && cell2Y >= 1 && cell2Y < cols - 1) {
            val cell2 = mazeCells(cell2X)(cell2Y)

            if (visitedCells.contains(cell1) && !visitedCells.contains(cell2)) {
              wall.setState(CellState.PATH)
              cell2.setState(CellState.PATH)

              visitedCells.addOne(wall)
              visitedCells.addOne(cell2)

              addWalls(cell2, mazeCells, wallList, visitedCells)

              connected = true

              Thread.sleep(10)
            }
          }
        }
      }

      wallList -= wall
    }
  }

  private def addWalls(cell: MazeCell, mazeCells: Array[Array[MazeCell]], walls: ListBuffer[MazeCell], visited: ListBuffer[MazeCell]): Unit = {
    val rows = mazeCells.length
    val cols = mazeCells(0).length

    val (x, y) = cell.getPosition

    val directions = random.shuffle(List(
      (0, -1), (0, 1), (-1, 0), (1, 0)
    ))

    for ((dx, dy) <- directions) {
      val wallX = x + dx
      val wallY = y + dy

      if (wallX >= 1 && wallX < rows - 1 && wallY >= 1 && wallY < cols - 1) {
        val wall = mazeCells(wallX)(wallY)

        if (!visited.contains(wall)) {
          walls.addOne(wall)
        }
      }
    }
  }


  override def toString = "Randomized Prim's algorithm"

}
