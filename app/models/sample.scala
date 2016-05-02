package models

import play.api.libs.json.Json

case class Sample (id: Option[Long], temperature: Float, humidity: Float, windDirection: String, windSpeed: Float, rainLevel: Float){
}

object Sample {
  implicit val sampleReads = Json.reads[Sample]
}