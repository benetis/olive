package models

case class PlantDiseaseModel(id: Option[Long],
                             name: String,
                             description : Option[String],
                             modelImageUrl: Option[String]
                            ){
}

