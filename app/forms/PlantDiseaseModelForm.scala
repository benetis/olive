package forms

import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json

/**
 * The form which handles the submission of the credentials.
 */
object PlantDiseaseModelForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "name" -> nonEmptyText(maxLength = 20),
      "description" -> text,
      "modelImageUrl" -> text
    )(Data.apply)(Data.unapply)
  )

  case class Data(
    name: String,
    description: String,
    modelImageUrl: String)

}
