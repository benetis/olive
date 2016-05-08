package models.daos

import java.sql.Timestamp
import java.util.UUID
import javax.inject.Inject

import models.{Sample, WeatherStation}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

class WeatherStationDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTableDefinitions {
  import driver.api._

  private val stations = TableQuery[WeatherStationTable]

  def all(): Future[Seq[WeatherStation]] = {
    val query = slickWeatherStations.map(station => (station.id, station.key, station.name, station.coordinatesX, station.coordinatesY))
    val wStations = db.run(query.result)
    wStations.map(s => s.map(p => WeatherStation(Option(p._1), UUID.fromString(p._2), p._3, p._4, p._5)))
  }

  def insert(station: DBWeatherStation): Future[Unit] = db.run(stations += station).map { _ => () }

    def createTable() = {
      stations.schema.create
    }

}