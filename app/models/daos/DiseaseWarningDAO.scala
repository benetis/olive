package models.daos

import javax.inject.Inject

import models.PlantDiseaseCondition
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

class DiseaseWarningDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val plantDiseaseConditions = TableQuery[PlantDiseaseConditionTable]

  def all(): Future[Seq[PlantDiseaseCondition]] = db.run(plantDiseaseConditions.result)

  def findByModelId(modelId: Option[Long]): Future[Seq[PlantDiseaseCondition]] =
    db.run(plantDiseaseConditions.filter(_.modelId === modelId).result)

  def insert(plantDiseaseCondition: PlantDiseaseCondition): Future[Unit] = db.run(plantDiseaseConditions += plantDiseaseCondition).map { _ => () }

  private class PlantDiseaseConditionTable(tag: Tag) extends Table[PlantDiseaseCondition](tag, "PLANT_DISEASE_CONDITION") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def paramId = column[Int]("param_id")
    def modelId = column[Long]("model_id")
    def condition = column[Float]("condition")
    def conditionParam = column[Int]("condition_param")
    def duration = column[Int]("duration")

    def * = (id.?, paramId, modelId.?, condition, conditionParam, duration ) <> ((PlantDiseaseCondition.apply _).tupled, PlantDiseaseCondition.unapply _)
  }

  def createTable() = {
    db.run(plantDiseaseConditions.schema.create)
  }
}
