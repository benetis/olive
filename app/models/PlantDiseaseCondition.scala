package models

case class PlantDiseaseCondition(id: Option[Long],
                                 paramId: Int,
                                 modelId: Int,
                                 condition: Float,
                                 conditionParam: Int,
                                 duration: Int
                            ){
}
