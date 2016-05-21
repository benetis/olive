package models

import play.api.libs.json.Json

case class PlantDiseaseCondition(id: Option[Long] = None,
                                 paramId: Int,
                                 modelId: Long,
                                 condition: Float,
                                 conditionParam: Int,
                                 duration: Int
                            ){
}

object PlantDiseaseCondition {
  implicit val plantModelsReads = Json.reads[PlantDiseaseCondition]
  implicit val plantModelsWrites = Json.writes[PlantDiseaseCondition]
}