package gui

import algorithm.generative.{Generative, RandomizedDepthFirstSearch}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ComboBox}
import scalafx.scene.layout.{BorderPane, HBox}

object MainFrame extends JFXApp3 {

  private val generativeAlgorithms: ObservableBuffer[Generative] = ObservableBuffer(
    new RandomizedDepthFirstSearch
  )


  override def start(): Unit = {
    val mazeGrid = new MazeGrid

    val controlsContainer = new HBox()

    // Dropdown list for generative algorithms
    val algorithmDropdown = new ComboBox[Generative](generativeAlgorithms) {
      promptText = "Select Algorithm"
      value.onChange((_, oldValue, newValue) => {
        if (newValue != null) {
          newValue.generate(mazeGrid.getCells)
        }
      })
    }

    // Button to manually trigger maze generation (optional)
    val generateButton = new Button("Generate Maze")
    generateButton.onAction = _ => {
      val selectedAlgorithm = algorithmDropdown.value.value
      selectedAlgorithm.generate(mazeGrid.getCells)
    }

    controlsContainer.getChildren.addAll(algorithmDropdown, generateButton)

    stage = new PrimaryStage {
      title = "Search Visualizer"
      scene = new Scene {
        content = new BorderPane {
          center = mazeGrid
          bottom = controlsContainer
        }
      }
    }
  }

}
