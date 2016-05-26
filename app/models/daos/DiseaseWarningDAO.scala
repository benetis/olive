package models.daos

import javax.inject.Inject

import models.{DiseaseWarning, PlantDiseaseModel}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future

class DiseaseWarningDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTableDefinitions {
  import driver.api._

  private val diseaseWarnings = TableQuery[DiseaseWarningTable]

  def all(): Future[Seq[DiseaseWarning]] = db.run(diseaseWarnings.result)

  def allWithModels(): Future[Seq[(DiseaseWarning, PlantDiseaseModel)]] = {
    val join = for {
      (warning, model) <- diseaseWarnings join slickPlantDiseaseModels
    } yield (warning, model)
    db.run(join.result)
  }

  def insert(diseaseWarning: DiseaseWarning): Future[Unit] = db.run(diseaseWarnings += diseaseWarning).map { _ => () }

  def createTable() = {
    db.run(diseaseWarnings.schema.create)
  }
}
