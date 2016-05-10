package models.daos

import java.sql.Timestamp
import javax.inject.Inject

import models.Sample
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

class SampleDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Samples = TableQuery[SamplesTable]

  def all(): Future[Seq[Sample]] = db.run(Samples.result)

  def getJsonSamples(): Future[Seq[Sample]] = {
   val result = sql"""select s.id, s.temperature, s.humidity, s.wind_direction, s.wind_speed, s.rain_level, s.clocked
      from sample s
      group by hour(s.clocked)""".as[Sample]
    db.run(result)
  }

  def insert(sample: Sample): Future[Unit] = db.run(Samples += sample).map { _ => () }

  private class SamplesTable(tag: Tag) extends Table[Sample](tag, "SAMPLE") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def temperature = column[Float]("TEMPERATURE")
    def humidity = column[Float]("HUMIDITY")
    def windDirection = column[String]("WIND_DIRECTION")
    def windSpeed = column[Float]("WIND_SPEED")
    def rainLevel = column[Float]("RAIN_LEVEL")
    def clocked = column[Timestamp]("CLOCKED", SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"))

    def * = (id.?, temperature, humidity, windDirection, windSpeed, rainLevel, clocked) <> ((Sample.apply _).tupled, Sample.unapply _)
  }
}
