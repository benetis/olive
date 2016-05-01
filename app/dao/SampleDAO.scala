package dao

import javax.inject.Inject

import models.Sample
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape
import scala.concurrent.Future

class SampleDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val Samples = TableQuery[SamplesTable]

  def all(): Future[Seq[Sample]] = db.run(Samples.result)

  private class SamplesTable(tag: Tag) extends Table[Sample](tag, "SAMPLE") {
    def id = column[Long]("ID", O.PrimaryKey, O.AutoInc)
    def temperature = column[Float]("TEMPERATURE")
    def humidity = column[Float]("HUMIDITY")
    def windDirection = column[String]("WIND_DIRECTION")
    def windSpeed = column[Float]("WIND_SPEED")
    def rainLevel = column[Float]("RAIN_LEVEL")

    def * = (id.?, temperature, humidity, windDirection, windSpeed, rainLevel) <> (Sample.tupled, Sample.unapply _)
  }
}
