package models.daos

import java.sql.Timestamp
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

  def all(): Future[Seq[DBWeatherStation]] = db.run(stations.result)

  def insert(station: DBWeatherStation): Future[Unit] = db.run(stations += station).map { _ => () }

    def createTable() = {
      stations.schema.create
    }

}