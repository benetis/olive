package models

import play.api.libs.json.Json

case class PlantDiseaseModel(id: Option[Long] = None,
                             name: String,
                             description : Option[String] = None,
                             modelImageUrl: Option[String] = None
                            ){
}

object PlantDiseaseModel {
  implicit val plantModelsReads = Json.reads[PlantDiseaseModel]
  implicit val plantModelsWrites = Json.writes[PlantDiseaseModel]
}