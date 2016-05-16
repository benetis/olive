package models.daos

import javax.inject.Inject

import models.{PlantDiseaseFilter, PlantDiseaseModel}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

class PlantDiseaseModelDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import driver.api._

  private val plantDiseaseModels = TableQuery[PlantDiseaseModelTable]

  def all(): Future[Seq[PlantDiseaseModel]] = db.run(plantDiseaseModels.result)

  def insert(plantDiseaseModel: PlantDiseaseModel): Future[Unit] = db.run(plantDiseaseModels += plantDiseaseModel).map { _ => () }

  private class PlantDiseaseModelTable(tag: Tag) extends Table[PlantDiseaseModel](tag, "PLANT_DISEASE_MODEL") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def description = column[String]("description")
    def modelImageUrl = column[String]("model_image_url")

    def * = (id.?, name, description.? , modelImageUrl.? ) <> ((PlantDiseaseModel.apply _).tupled, PlantDiseaseModel.unapply _)
  }

  def createTable() = {
    db.run(plantDiseaseModels.schema.create)
  }
}
