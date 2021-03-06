package models.daos

import java.util.concurrent.TimeUnit
import javax.inject.Inject

import models.{DiseaseWarning, PlantDiseaseCondition, PlantDiseaseModel, Sample}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class DiseaseWarningDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTableDefinitions {

  import driver.api._

  private val diseaseWarnings = TableQuery[DiseaseWarningTable]

  def all(): Future[Seq[DiseaseWarning]] = db.run(diseaseWarnings.result)

  def allWithModels(userId: String): Future[Seq[(DiseaseWarning, PlantDiseaseModel)]] = {
    val join = for {
      (warning, model) <- diseaseWarnings.filter(_.userId === userId) join slickPlantDiseaseModels on (_.modelId === _.id)
    } yield (warning, model)
    db.run(join.result)
  }

  def allOnlyModels(userId: String): Future[Seq[PlantDiseaseModel]] = {
    val join = for {
      (warning, model) <- diseaseWarnings.filter(_.userId === userId) join slickPlantDiseaseModels on (_.modelId === _.id)
    } yield (model)
    db.run(join.result)
  }

//  def allWithConditions(): Future[Seq[(DiseaseWarning, PlantDiseaseCondition)]] = {
//    val join = for {
//      (warning, conditions) <- diseaseWarnings join slickPlantDiseaseConditions on (_.modelId === _.modelId)
//    } yield (warning, conditions)
//    db.run(join.result)
//  }

  def allConditionsForWarning(modelId: Long) : Future[Seq[PlantDiseaseCondition]] = {
    db.run(slickPlantDiseaseConditions.filter(_.modelId === modelId).result)
  }

//  //todo: optimize query or better go with insert strategy
  //todo: fix async
  def triggeredWarningsAsModels(userId: String): Seq[PlantDiseaseModel] = {
    val conditionsFuture = allWithModels(userId).map(models => models.map(model => {
      db.run(slickPlantDiseaseConditions.filter(_.modelId === model._2.id).result)
    })).map(condition => Future.sequence(condition)).flatMap(identity)

    val conditions: Seq[PlantDiseaseCondition] = Await.result(conditionsFuture ,Duration(1, TimeUnit.SECONDS)).flatten
    val triggeredWarnings = conditions.filter(condition => {
      val duration = condition.duration
      val paramId = condition.paramId
      val param = PlantDiseaseCondition.getWeatherDaoParameterById(paramId)
      val conditionLimit = condition.condition
      val conditionParam = PlantDiseaseCondition.getInvertedConditionParamById(condition.conditionParam)

      val result = sql"""
        select s.id, avg(s.temperature), avg(s.humidity),
                               avg(s.wind_direction), avg(s.wind_speed), avg(s.rain_level), s.clocked
        from SAMPLE s
        where
        s.clocked >= DATE_SUB(NOW(),INTERVAL #$duration HOUR) AND
        s.#$param #$conditionParam #$conditionLimit
        group by hour(s.clocked);
        """.as[Sample]

      val samples = Await.result(db.run(result) ,Duration(1, TimeUnit.SECONDS))

      samples.headOption match {
        case Some(x) => false
        case _ => true
      }
    })
    val warnings = Future.sequence(triggeredWarnings.map(condition => {
      db.run(slickPlantDiseaseModels.filter(_.id === condition.modelId).result)
    }))
    Await.result(warnings ,Duration(1, TimeUnit.SECONDS)).flatten.distinct
  }

  //todo: fix async
  def allObservedWarnings(userId: String) = {
    Await.result(allWithModels(userId) ,Duration(1, TimeUnit.SECONDS))
  }

  def insert(diseaseWarning: DiseaseWarning): Future[Unit] = db.run(diseaseWarnings += diseaseWarning).map { _ => () }

  def deleteById(id: Long) = { db.run(
    diseaseWarnings.filter(_.id === id).delete
  )}


  def createTable() = {
    db.run(diseaseWarnings.schema.create)
  }
}
