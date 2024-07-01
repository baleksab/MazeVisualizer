package algorithm.generative
import gui.components.{CellState, MazeCell}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.Random

class RandomizedKruskalsAlgorithm extends Generative {

  private val random = new Random()

  override def generate(mazeCells: Array[Array[MazeCell]]): Unit = {
    val rows = mazeCells.length
    val cols = mazeCells(0).length

    val listOfWalls = ListBuffer[MazeCell]()
    val listOfSets = ListBuffer[Set[MazeCell]]()

    for (i <- 1 until (rows - 1); j <- 1 until (cols - 1)) {
      val cell: MazeCell = mazeCells(i)(j)

      if (i % 2 == 0 || j % 2 == 0) {
        listOfWalls.addOne(cell)
      } else {
        val set = Set[MazeCell](cell)
        listOfSets.addOne(set)
      }
    }

    val randomizedWalls = random.shuffle(listOfWalls)


    for (wall <- randomizedWalls) {
      if (wall != null) {
        val directions = random.shuffle(List(
          ((0, -1), (0, 1)), ((-1, 0), (1, 0))
        ))

        for (((dx1, dy1), (dx2, dy2)) <- directions) {
          val (x, y) = wall.getPosition

          val x1 = x + dx1
          val y1 = y + dy1
          val x2 = x + dx2
          val y2 = y + dy2

          if (x1 >= 0 && x1 < rows && y1 >= 0 && y1 < cols && x2 >= 0 && x2 < rows && y2 >= 0 && y2 < cols) {
            val cell1 = mazeCells(x1)(y1)
            val cell2 = mazeCells(x2)(y2)

            if (cell1 != null && cell2 != null) {
              val set1Opt = listOfSets.find(_.contains(cell1))
              val set2Opt = listOfSets.find(_.contains(cell2))

              (set1Opt, set2Opt) match {
                case (Some(set1), Some(set2)) if set1 != set2 =>
                  wall.setState(CellState.PATH)

                  val unionSet = set1 ++ set2
                  listOfSets -= set1
                  listOfSets -= set2
                  listOfSets += unionSet

                  for (cell <- unionSet) {
                    cell.setState(CellState.PATH)
                  }

                  Thread.sleep(10)
                case _ =>
              }
            }
          }
        }
      }
    }
  }

  override def toString: String = "Randomized Kruskal's algorithm (Iterative)"

}
