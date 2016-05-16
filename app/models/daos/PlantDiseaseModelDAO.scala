package models.daos

import java.sql.Timestamp
import javax.inject.Inject

import models.{PlantDiseaseModel, Sample}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

class PlantDiseaseModelDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val PlantDiseaseModels = TableQuery[PlantDiseaseModelTable]

  def all(): Future[Seq[PlantDiseaseModel]] = db.run(PlantDiseaseModels.result)

  private class PlantDiseaseModelTable(tag: Tag) extends Table[PlantDiseaseModel](tag, "PLANT_DISEASE_MODEL") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def paramId = column[Int]("param_id")
    def condition = column[Float]("condition")
    def conditionParam = column[String]("condition_param")
    def duration = column[Int]("duration")

    def * = (id.?, paramId, condition, conditionParam, duration) <> ((PlantDiseaseModel.apply _).tupled, PlantDiseaseModel.unapply _)
  }
}
