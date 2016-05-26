package models.daos

import javax.inject.Inject

import models.PlantDiseaseCondition
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

class PlantDiseaseConditionDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTableDefinitions {
  import driver.api._

  private val plantDiseaseConditions = TableQuery[PlantDiseaseConditionTable]

  def all(): Future[Seq[PlantDiseaseCondition]] = db.run(plantDiseaseConditions.result)

  def findByModelId(modelId: Option[Long]): Future[Seq[PlantDiseaseCondition]] =
    db.run(plantDiseaseConditions.filter(_.modelId === modelId).result)

  def insert(plantDiseaseCondition: PlantDiseaseCondition): Future[Unit] = db.run(plantDiseaseConditions += plantDiseaseCondition).map { _ => () }

  def createTable() = {
    db.run(plantDiseaseConditions.schema.create)
  }
}
