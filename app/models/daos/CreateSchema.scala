package models.daos

import javax.inject.Inject

import com.mohiva.play.silhouette.api.LoginInfo
import models.WeatherStation
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape.proveShapeOf
import scala.concurrent.Await
//TODO needs injection
class CreateSchema @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends DBTableDefinitions {
  val driver = slick.driver.MySQLDriver
  import driver.api._

  lazy val allTables = Array(
    TableQuery[WeatherStationTable].schema
  ).reduceLeft(_ ++ _)

  def create() = {
        allTables.create
  }
}
