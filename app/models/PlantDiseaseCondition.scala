package models

import play.api.i18n.Messages
import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Json, Reads}

case class PlantDiseaseCondition(id: Option[Long] = None,
                                 paramId: Int,
                                 modelId: Option[Long] = None,
                                 condition: Float,
                                 conditionParam: Int,
                                 duration: Int
                            ){
}

object PlantDiseaseCondition {
  implicit val plantDiseaseConditionReads: Reads[PlantDiseaseCondition] = (
    (JsPath \ "id").readNullable[Long] and
      (JsPath \ "paramId").read[Int] and
      (JsPath \ "modelId").readNullable[Long] and
      (JsPath \ "condition").read[Float] and
      (JsPath \ "conditionParam").read[Int] and
      (JsPath \ "duration").read[Int]
    ) (PlantDiseaseCondition.apply _)
  implicit val plantModelsWrites = Json.writes[PlantDiseaseCondition]

  def conditionParams() = Seq(
    "1" -> ">",
    "2" -> "=",
    "3" -> "<"
  )

  /**
    *
    * @param messages Messages, passing messages object to translate the language
    * @return
    */
  def weatherParameters(messages: Messages) = Seq(
    "1" -> messages("temperature"),
    "2" -> messages("humidity"),
    "3" -> messages("wind.direction"),
    "4" -> messages("wind.speed"),
    "5" -> messages("rain.level")
  )

  def weatherDaoParameters() = Seq(
    1 -> "temperature",
    2 -> "humidity",
    3 -> "wind_direction",
    4 -> "wind_speed",
    5 -> "rain_level"
  )

  def getConditionParamById(id: Int): String = {
    conditionParams().filter(condParam => condParam._1 == id.toString).head._2
  }

  def getInvertedConditionParamById(id: Int): String = id match {
    case 1 => getConditionParamById(3)
    case 3 => getConditionParamById(1)
    case 2 => getConditionParamById(2)
  }

  def getWeatherParameterById(id: Int, messages: Messages): String = {
    weatherParameters(messages).filter(weatherParam => weatherParam._1 == id.toString).head._2
  }

  def getWeatherDaoParameterById(id: Int) : String = {
    weatherDaoParameters().filter(param => param._1 == id).head._2
  }
}