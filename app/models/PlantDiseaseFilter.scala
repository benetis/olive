package models

case class PlantDiseaseFilter(id: Option[Long],
                              paramId: Int,
                              modelId: Int,
                              condition: Float,
                              conditionParam: String,
                              duration: Int
                            ){
}

