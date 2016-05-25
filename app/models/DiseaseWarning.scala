package models

case class DiseaseWarning(id: Option[Long] = None,
                                 modelId: Long,
                                 condition : Float,
                                 conditionParam: Int,
                                 duration: Int
                                ){
}