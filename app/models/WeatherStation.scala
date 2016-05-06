package models

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.UUID

import play.api.libs.json.{Json, _}

case class WeatherStation (id: Option[Long],
                           key: UUID,
                           name: String,
                           coordinatesX: BigDecimal,
                           coordinatesY: BigDecimal
                          )
