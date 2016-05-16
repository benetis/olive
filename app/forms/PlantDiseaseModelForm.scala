package forms

import play.api.data.Form
import play.api.data.Forms._

/**
 * The form which handles the submission of the credentials.
 */
object PlantDiseaseModelForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "name" -> nonEmptyText,
      "description" -> text,
      "modelImageUrl" -> text
    )(Data.apply)(Data.unapply)
  )

  case class Data(
    name: String,
    description: String,
    modelImageUrl: String)
}
