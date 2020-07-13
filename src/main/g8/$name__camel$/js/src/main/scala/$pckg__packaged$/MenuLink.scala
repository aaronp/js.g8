package $pckg;format="lower,package"$

import org.scalajs.dom.Event
import org.scalajs.dom.html.Anchor
import scalatags.JsDom.all._

object MenuLink {

  /** @param icon the icon name -- see https://material.io/resources/icons/?style=baseline
   * @param name  the link text
   * @returna link element
   */
  def apply(icon: String, name: String)(action: Event => Unit): Anchor = {
    val elm = create(icon, name)
    elm.onclick = e => {
      e.preventDefault()
      action(e)
    }
    elm
  }

  def create(icon: String, name: String): Anchor = {
    val link = a(`class` := "mdc-list-item", `href` := "#")(
      i(`class` := "material-icons mdc-list-item__graphic", attr("aria-hidden") := "true")(icon),
      span(`class` := "mdc-list-item__text")(name)
    )

    link.render
  }
}
