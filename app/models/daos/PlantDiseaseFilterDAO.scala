package models.daos

import java.sql.Timestamp
import javax.inject.Inject

import models.{PlantDiseaseFilter, Sample}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile
import slick.profile.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

class PlantDiseaseFilterDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val plantDiseaseFilters = TableQuery[PlantDiseaseFilterTable]

  def all(): Future[Seq[PlantDiseaseFilter]] = db.run(plantDiseaseFilters.result)

  def insert(plantDiseaseModel: PlantDiseaseFilter): Future[Unit] = db.run(plantDiseaseFilters += plantDiseaseModel).map { _ => () }

  private class PlantDiseaseFilterTable(tag: Tag) extends Table[PlantDiseaseFilter](tag, "PLANT_DISEASE_MODEL") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def paramId = column[Int]("param_id")
    def condition = column[Float]("condition")
    def conditionParam = column[String]("condition_param")
    def duration = column[Int]("duration")

    def * = (id.?, paramId, condition, conditionParam, duration) <> ((PlantDiseaseFilter.apply _).tupled, PlantDiseaseFilter.unapply _)
  }

  def createTable() = {
    db.run(plantDiseaseFilters.schema.create)
  }
}
