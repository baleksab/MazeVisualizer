package gui

import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene

object MainFrame extends JFXApp3 {

  override def start(): Unit = {
    stage = new PrimaryStage {
      title = "Search Visualizer"
      scene = new Scene {

      }
    }
  }

}
