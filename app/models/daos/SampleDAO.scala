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
   val result = sql"""select avg(s.id), avg(s.temperature), avg(s.humidity),
                             avg(s.wind_direction), avg(s.wind_speed), avg(s.rain_level), s.clocked
      from sample s
      group by hour(s.clocked)""".as[Sample]
    db.run(result)
  }

  def insert(sample: Sample): Future[Unit] = db.run(Samples += sample).map { _ => () }

  private class SamplesTable(tag: Tag) extends Table[Sample](tag, "SAMPLE") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def temperature = column[Float]("temperature")
    def humidity = column[Float]("humidity")
    def windDirection = column[String]("wind_direction")
    def windSpeed = column[Float]("wind_speed")
    def rainLevel = column[Float]("rain_level")
    def clocked = column[Timestamp]("clocked", SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"))

    def * = (id.?, temperature, humidity, windDirection, windSpeed, rainLevel, clocked) <> ((Sample.apply _).tupled, Sample.unapply _)
  }
}
