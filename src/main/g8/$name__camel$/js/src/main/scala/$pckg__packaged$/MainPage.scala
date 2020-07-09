package $pckg;format="lower,package"$

import org.scalajs.dom.html.{Div, Paragraph}
import org.scalajs.dom.raw.HTMLTextAreaElement
import org.scalajs.dom.window
import scalatags.JsDom.all._

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}
import scala.util.Try

/**
  */
@JSExportTopLevel("MainPage")
object MainPage {

  @JSExport
  def render(computeContainerId: String) = {
      val computeContainer = HtmlUtils.divById(computeContainerId)

      computeContainer.innerHTML = ""
      computeContainer.appendChild(h1("Hello World").render)
  }

}
