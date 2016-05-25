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
      "modelId" -> number
    )(Data.apply)(Data.unapply)
  )

  case class Data(
    modelId: Int
                 )
}
