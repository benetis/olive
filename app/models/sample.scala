package models

import java.sql.Timestamp
import java.text.SimpleDateFormat

import play.api.libs.json._
import play.api.libs.json.Json
import slick.jdbc.GetResult

case class Sample (id: Option[Long],
                   temperature: Float,
                   humidity: Float,
                   windDirection: String,
                   windSpeed: Float,
                   rainLevel: Float,
                   clocked: Timestamp){
}

object Sample {
  implicit val getSampleResult = GetResult(r => Sample(r.nextLongOption(), r.nextFloat(), r.nextFloat(),
    r.nextString(), r.nextFloat(), r.nextFloat(), r.nextTimestamp()))

  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }

    def writes(ts: Timestamp) = JsString(format.format(ts))
  }
  implicit object tempAndClockedFormat extends Writes[Seq[Sample]] {
    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    def writes(samples: Seq[Sample]) = {
      val seq = samples.map(sample => {
        Json.obj(
          "temperature" -> JsString(sample.temperature.toString),
          "clocked" -> JsString(format.format(sample.clocked))
        )
      })
      JsArray(seq)
    }
  }
  implicit val sampleReads = Json.reads[Sample]
  implicit val sampleWrites = Json.writes[Sample]

}