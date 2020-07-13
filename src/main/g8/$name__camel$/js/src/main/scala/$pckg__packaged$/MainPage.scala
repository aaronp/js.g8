package $pckg;format="lower,package"$

import org.scalajs.dom.document
import org.scalajs.dom.raw.Element

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

/**
 */
@JSExportTopLevel("MainPage")
object MainPage {

  @JSExport
  def render(computeContainerId: String) = {
    val computeContainer = HtmlUtils.elmById(computeContainerId)

    val drawerContentList: Element = document.querySelector("div.mdc-drawer__content div.mdc-list")

    val waves = MenuLink("waves", "Waves") { e =>
      computeContainer.innerHTML = "Waves"
    }
    val gestures = MenuLink("gestures", "Gestures") { e =>
      computeContainer.innerHTML = "Gestures"
    }
    drawerContentList.innerHTML = ""
    drawerContentList.appendChild(waves)
    drawerContentList.appendChild(gestures)
  }
}
