package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

/**
 * The form which handles the submission of the credentials.
 */
object DiseaseWarningForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "paramId" -> number,
      "condition" -> of(floatFormat),
      "conditionParam" -> number,
      "duration" -> number(min = 0)
    )(Data.apply)(Data.unapply)
  )

  case class Data(
    paramId: Int,
    condition: Float,
    conditionParam: Int,
    duration: Int)
}
