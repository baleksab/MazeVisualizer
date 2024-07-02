package gui.components

object CellState extends Enumeration {

  type CellState = Value
  val WALL, PATH, VISITED, PART_OF_SOLUTION = Value

}