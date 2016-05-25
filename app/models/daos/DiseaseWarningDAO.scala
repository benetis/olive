package models.daos

import javax.inject.Inject

import models.{DiseaseWarning, PlantDiseaseCondition}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

class DiseaseWarningDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val diseaseWarning = TableQuery[DiseaseWarningTable]

  def all(): Future[Seq[DiseaseWarning]] = db.run(diseaseWarning.result)

  //  def findByModelId(modelId: Option[Long]): Future[Seq[PlantDiseaseCondition]] =
  //    db.run(diseaseWarning.filter(_.modelId === modelId).result)
  //
  //  def insert(plantDiseaseCondition: PlantDiseaseCondition): Future[Unit] = db.run(diseaseWarning += plantDiseaseCondition).map { _ => () }

  private class DiseaseWarningTable(tag: Tag) extends Table[DiseaseWarning](tag, "DISEASE_WARNING") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def modelId = column[Long]("model_id")
    def userId = column[Long]("user_id")

    def * = (id.?, modelId, userId) <> ((DiseaseWarning.apply _).tupled, DiseaseWarning.unapply _)
  }

  def createTable() = {
    db.run(diseaseWarning.schema.create)
  }
}
