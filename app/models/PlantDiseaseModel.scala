package models

import play.api.libs.json.Json

case class PlantDiseaseModel(id: Option[Long],
                             name: String,
                             description : Option[String],
                             modelImageUrl: Option[String]
                            ){
}

object PlantDiseaseModel {
  implicit val plantModelsReads = Json.reads[PlantDiseaseModel]
  implicit val plantModelsWrites = Json.writes[PlantDiseaseModel]
}