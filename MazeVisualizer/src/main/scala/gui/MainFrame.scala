package gui

import algorithm.generative.{Generative, RandomizedDepthFirstSearch, RandomizedKruskalsAlgorithm, RandomizedPrimsAlgorithm, WilsonsAlgorithm}
import algorithm.solver.{AStarSearch, BreadthFirstSearch, DepthFirstSearch, Solver}
import gui.components.{CellState, MazeGrid}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ComboBox}
import scalafx.scene.layout.{BorderPane, HBox}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object MainFrame extends JFXApp3 {

  private val generativeAlgorithms: ObservableBuffer[Generative] = ObservableBuffer(
    new RandomizedDepthFirstSearch,
    new RandomizedKruskalsAlgorithm,
    new RandomizedPrimsAlgorithm,
    new WilsonsAlgorithm
  )

  private val solverAlgorithms: ObservableBuffer[Solver] = ObservableBuffer(
    new BreadthFirstSearch,
    new DepthFirstSearch,
    new AStarSearch
  )

  override def start(): Unit = {
    val mazeGrid = new MazeGrid

    val controlsContainer = new HBox()

    val algorithmDropdown = new ComboBox[Generative](generativeAlgorithms) {
      promptText = "Select Generation Algorithm"
    }

    val solverDropdown = new ComboBox[Solver](solverAlgorithms) {
      promptText = "Select Solver Algorithm"
    }

    val solveButton = new Button("Solve Maze")
    val generateButton = new Button("Generate Maze")
    val resetButton = new Button("Reset Maze")


    generateButton.onAction = _ => {
      if (algorithmDropdown.value.value != null) {
        val selectedAlgorithm = algorithmDropdown.value.value
        generateButton.setDisable(true)
        resetButton.setDisable(true)
        val future = Future {
          selectedAlgorithm.generate(mazeGrid.getCells)
        }
        future.onComplete { _ =>
          resetButton.setDisable(false)
          mazeGrid.getCells(0)(1).setState(CellState.PATH)
          mazeGrid.getCells(mazeGrid.getCells.length - 1)(mazeGrid.getCells(0).length - 2).setState(CellState.PATH)
        }
      } else {
        val alert = new Alert(AlertType.Warning) {
          initOwner(generateButton.scene().getWindow)
          title = "No Generative Algorithm Selected"
          headerText = "Please select an algorithm."
          contentText = "You need to select an algorithm to generate the maze."
        }
        alert.showAndWait()
      }
    }

    solverDropdown.onAction = _ => {
      if (solverDropdown.value.value != null) {
       //
      } else {
        val alert = new Alert(AlertType.Warning) {
          initOwner(generateButton.scene().getWindow)
          title = "No Solver Algorithm Selected"
          headerText = "Please select an algorithm."
          contentText = "You need to select an algorithm to solve the maze."
        }
        alert.showAndWait()
      }
    }

    resetButton.onAction = _ => {
      mazeGrid.resetCells()
      generateButton.setDisable(false)
    }

    controlsContainer.getChildren.addAll(resetButton, algorithmDropdown, generateButton, solverDropdown, solveButton)

    stage = new PrimaryStage {
      title = "Maze Visualizer"
      resizable = false
      scene = new Scene {
        content = new BorderPane {
          center = mazeGrid
          bottom = controlsContainer
        }
      }
    }
  }

}
