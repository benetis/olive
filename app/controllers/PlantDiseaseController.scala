package controllers

import java.util.concurrent.TimeUnit
import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import forms.{PlantDiseaseConditionForm, PlantDiseaseModelForm}
import models.daos.{PlantDiseaseConditionDAO, PlantDiseaseModelDAO}
import models.{PlantDiseaseCondition, PlantDiseaseModel, PlantDiseaseModelWithCondition}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import utils.WithService
import utils.auth.{AuthenticationController, DefaultEnv}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


@Singleton
class PlantDiseaseController @Inject()(
                                        plantDiseaseModelDao: PlantDiseaseModelDAO,
                                        plantDiseaseConditionDao: PlantDiseaseConditionDAO,
                                        val messagesApi: MessagesApi,
                                        silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport with AuthenticationController  {

  def index = silhouette.SecuredAction.async { implicit request =>
    plantDiseaseModelDao.all().map {
      models: Seq[PlantDiseaseModel] => {
        Future.sequence(models.map(plantDiseaseModel => {
          for {
            conditions <- plantDiseaseConditionDao.findByModelId(plantDiseaseModel.id)
          } yield (plantDiseaseModel, conditions)
        }))
      }
        //change to proper handling w/o await
    }.map(modelsWithCond => Ok(views.html.plant_disease_models.plant_models(Await.result(modelsWithCond, Duration(1, TimeUnit.SECONDS)))))
  }

  def createModel = silhouette.SecuredAction(WithService()).async { implicit request =>
      Future.successful(Ok(views.html.plant_disease_models.create_model(PlantDiseaseModelForm.form,
                                                   PlantDiseaseConditionForm.form)))
  }

  def submit = silhouette.SecuredAction(WithService()).async { implicit request =>
    val jsonRequest = request.body.asJson
    //Callback hell :o TODO: fix this
    jsonRequest.map { json => json.validate[PlantDiseaseModelWithCondition] match {
      case JsSuccess(s, _) =>
        println(s)
        plantDiseaseModelDao.insertId(PlantDiseaseModel(
          name = s.name,
          description = s.description,
          modelImageUrl = s.modelImageUrl)).map(id => {
          s.conditions.map(seq => seq.map(c => {
            plantDiseaseConditionDao.insert(PlantDiseaseCondition(modelId = Some(id), paramId = c.paramId,
              condition=c.condition, conditionParam = c.conditionParam, duration = c.duration))
          }))
          Ok(Json.obj("status" -> "OK", "id" -> id, "conditions" -> s.conditions))
        })
      case err@JsError(_) => Future.successful(BadRequest(JsError.toJson(err)))
    }
    } match {
      case Some(a) => Future.successful(Ok(Json.obj("status" -> "OK")))
      case None => Future.successful(BadRequest("json error"))
    }
  }

  def conditionSubmit() = Action.async { implicit request =>
    request.body.asJson.map { json => json.validate[PlantDiseaseCondition] match {
      case JsSuccess(s, _) => plantDiseaseConditionDao.insert(s).map(_ => Ok(Json.obj("status" -> "OK")))
      case err@JsError(_) => Future.successful(BadRequest(err.toString))
    }
    } match {
      case Some(a) => a
      case None => Future.successful(BadRequest("json error"))
    }
  }

  def deleteDiseaseModel(id: String) = silhouette.SecuredAction(WithService()).async { implicit request =>
    plantDiseaseModelDao.deleteById(id.toLong).map(deleted => {
      Redirect(routes.PlantDiseaseController.index())
    })
  }

}
