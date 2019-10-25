package $package;format="lower,package"$

import org.scalajs.dom.html.Form
import org.scalajs.dom.window
import scalatags.JsDom
import scalatags.JsDom.all.{`class`, _}

/**
  * Rendering of our configuration form
  */
object ConfigForm {

  def apply(logGeneration: Generation[Equation] => Unit,
            onSolve: (CountdownConfig, Int) => Unit): JsDom.TypedTag[Form] = {
    import FormElement.configListItems

    val submit = input(`type` := "submit", value := "Solve").render

    form(`class` := "form-style-7")(
      ul(
        li(submit)
      ))
  }

}
