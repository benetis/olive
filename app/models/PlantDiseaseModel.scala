package models

case class PlantDiseaseModel(id: Option[Long],
                             paramId: Int,
                             condition: Float,
                             conditionParam: String,
                             duration: Int
                            ){
}

