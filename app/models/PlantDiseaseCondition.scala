package models

import play.api.libs.json.{JsPath, Json, Reads}
import play.api.libs.functional.syntax._

case class PlantDiseaseCondition(id: Option[Long] = None,
                                 paramId: Int,
                                 modelId: Option[Long] = None,
                                 condition: Float,
                                 conditionParam: Int,
                                 duration: Int
                            ){
}

object PlantDiseaseCondition {
//  implicit val plantModelsReads = Json.reads[PlantDiseaseCondition]
implicit val plantDiseaseConditionReads : Reads[PlantDiseaseCondition] = (
    (JsPath \ "id").readNullable[Long] and
    (JsPath \ "paramId").read[Int] and
    (JsPath \ "modelId").readNullable[Long] and
    (JsPath \ "condition").read[Float] and
    (JsPath \ "conditionParam").read[Int] and
    (JsPath \ "duration").read[Int]
  )(PlantDiseaseCondition.apply _)
  implicit val plantModelsWrites = Json.writes[PlantDiseaseCondition]
}