package models

import java.sql.Timestamp
import java.text.SimpleDateFormat
import play.api.libs.json._
import play.api.libs.json.Json

case class Sample (id: Option[Long],
                   temperature: Float,
                   humidity: Float,
                   windDirection: String,
                   windSpeed: Float,
                   rainLevel: Float,
                   clocked: Timestamp){
}

object Sample {
  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }

    def writes(ts: Timestamp) = JsString(format.format(ts))
  }
  implicit val sampleReads = Json.reads[Sample]


}