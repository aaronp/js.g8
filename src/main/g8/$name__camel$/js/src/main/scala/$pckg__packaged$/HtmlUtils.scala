package $pckg;format="lower,package"$

import org.scalajs.dom.{document, window}
import org.scalajs.dom.html.{Canvas, Div}
import org.scalajs.dom.raw.{HTMLElement, Node}

import scala.collection.immutable

object HtmlUtils {

  def childrenFor(html: HTMLElement): immutable.IndexedSeq[Node] = {
    (0 until html.childNodes.length).map { i =>
      html.childNodes.item(i)
    }
  }

  def divById(id: String): Div = elmById(id) match {
    case div: Div => div
  }

  def canvasById(id: String): Canvas = elmById(id) match {
    case c: Canvas => c
  }

  def elmById(id: String) = document.getElementById(id)

  def log(msg: String) = window.console.log("INFO:" + msg)
}
