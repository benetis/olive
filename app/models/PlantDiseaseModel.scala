package models

import play.api.libs.json.{JsPath, Json, Reads}
import play.api.libs.functional.syntax._

case class PlantDiseaseModel(id: Option[Long] = None,
                             name: String,
                             description : Option[String] = None,
                             modelImageUrl: Option[String] = None
                            ){
}

case class PlantDiseaseModelWithCondition(id: Option[Long] = None,
                             name: String,
                             description : Option[String] = None,
                             modelImageUrl: Option[String] = None,
                             conditions: Option[Seq[PlantDiseaseCondition]] = None
                            ){
}

object PlantDiseaseModel {
  implicit val plantModelsReads = Json.reads[PlantDiseaseModel]
  implicit val plantModelsWrites = Json.writes[PlantDiseaseModel]
}

object PlantDiseaseModelWithCondition {
  implicit val plantModelsReadsWithCondition : Reads[PlantDiseaseModelWithCondition] = (
      (JsPath \ "id").readNullable[Long] and
      (JsPath \ "name").read[String] and
      (JsPath \ "description").readNullable[String] and
      (JsPath \ "modelImageUrl").readNullable[String] and
      (JsPath \ "conditions").readNullable[Seq[PlantDiseaseCondition]]
    )(PlantDiseaseModelWithCondition.apply _)
}