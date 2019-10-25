package $package;format="lower,package"$

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
  *
  * Contains some functions for creating a [[CountdownConfig]] from an HTML form, as well as a means
  * to display the compute/solution pages
  *
  * A big thanks to:
  * {{{
  * http://getspringy.com/
  * https://www.sanwebe.com/2014/08/css-html-forms-designs
  * https://www.scala-js.org/
  * https://swipe.js.org/
  * https://typelevel.org/cats/
  * }}}
  */
@JSExportTopLevel("CountdownPage")
object package $name;format="upper"$Page {

  private var logContent = ListBuffer[String]()

  @JSExport
  def render(configId: String,
             computeContainerId: String,
             scriptContainerId: String,
             resultCanvasId: String) = {

    val computeContainer = HtmlUtils.divById(computeContainerId)
    computeContainer.innerHTML = ""
  
  }

}
