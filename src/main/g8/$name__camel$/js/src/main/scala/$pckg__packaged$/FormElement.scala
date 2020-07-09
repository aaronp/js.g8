package $pckg;format="lower,package"$

import scalatags.JsDom.all.{value, _}

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

final case class FormElement[A] private (
    field: String,
    initialValue: String,
    hint: String,
    validate: String => Either[String, A]) {
  val fieldId = field.filter(_.isLetterOrDigit).toLowerCase

  val inputElement = input(`type` := "text",
                           name := fieldId,
                           maxlength := "100",
                           value := initialValue).render

  inputElement.onkeyup = _ => {
    validateCurrentValue()
  }

  def validateCurrentValue() = {
    validate(textValue) match {
      case Left(msg) => validationSpan.innerHTML = msg
      case Right(_)  => validationSpan.innerHTML = ""
    }
  }

  private val validationSpan = span(style := "color:orange !important").render
  val liElement = li(
    label(`for` := fieldId)(field),
    inputElement,
    span(hint),
    validationSpan,
  )

  def currentValue(): Option[A] = currentValueEither().toOption

  def currentValueEither(): Either[String, A] = validate(textValue)

  private def textValue = inputElement.value
}

object FormElement {

  private def valueAsNumbers(textValue: String): Try[Set[Int]] = {
    Try(textValue.split(",").flatMap(_.split(" ")).map(_.toInt).toSet)
  }

  // our poor-man's enum 'values()' for the form
  private val formElements = ListBuffer[FormElement[_]]()

  private def newFormElement[A](field: String,
                                initialValue: String,
                                hint: String,
                                validate: String => Either[String, A]) = {
    val elm = new FormElement[A](field, initialValue, hint, validate)
    formElements += elm
    elm
  }

  def int(field: String,
          initialValue: String,
          hint: String,
          min: Int = 0,
          max: Int = 500) = {
    def asInt(value: String) = Try(value.trim.toInt) match {
      case Failure(_)                         => Left("invalid integer")
      case Success(a) if a >= min && a <= max => Right(a)
      case Success(a)                         => Left(s"\$a should be between \${min} and \$max")
    }

    newFormElement(field, initialValue, hint, asInt)
  }

  def double(field: String,
             initialValue: String,
             hint: String,
             min: Double,
             max: Double) = {
    def asInt(value: String) = Try(value.trim.toDouble) match {
      case Failure(_)                         => Left("invalid double")
      case Success(a) if a >= min && a <= max => Right(a)
      case Success(a)                         => Left(s"\$a should be between \${min} and \$max")
    }

    newFormElement(field, initialValue, hint, asInt)
  }

  def optLong(field: String,
              initialValue: String,
              hint: String): FormElement[Option[Long]] = {
    def asInt(value: String) = Try(value.trim.toLong) match {
      case Failure(_) if value.trim.isEmpty => Right(Option.empty[Long])
      case Failure(_)                       => Left("invalid integer")
      case Success(a)                       => Right(Option(a))
    }

    newFormElement(field, initialValue, hint, asInt)
  }

  def ints(field: String, initialValue: String, hint: String) = {
    def asInt(value: String) = valueAsNumbers(value.trim) match {
      case Failure(_) =>
        Left("required a space or comma-separated list of integers")
      case Success(a) => Right(a)
    }

    newFormElement[Set[Int]](field, initialValue, hint, asInt)
  }


}
