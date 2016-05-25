package controllers

import java.util.concurrent.TimeUnit
import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import forms.{DiseaseWarningForm, PlantDiseaseConditionForm, PlantDiseaseModelForm}
import models.daos.{DiseaseWarningDAO, PlantDiseaseConditionDAO, PlantDiseaseModelDAO}
import models.{PlantDiseaseCondition, PlantDiseaseModel, PlantDiseaseModelWithCondition}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import utils.auth.{AuthenticationController, DefaultEnv}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


@Singleton
class DiseaseWarningController @Inject()(
                                          diseaseWarningDao: DiseaseWarningDAO,
                                          plantDiseaseModelDao: PlantDiseaseModelDAO,
                                          val messagesApi: MessagesApi,
                                          silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport with AuthenticationController  {

  def index = silhouette.SecuredAction.async { implicit request =>
    diseaseWarningDao.all().map(warnings => {
      Ok(views.html.disease_warnings.disease_warnings())
    })
  }

  def createWarning = silhouette.SecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.disease_warnings.create_warning(DiseaseWarningForm.form, plantDiseaseModelDao.allForSelect())))
  }

  def submit = silhouette.SecuredAction { implicit request =>
    DiseaseWarningForm.form.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.disease_warnings.create_warning(DiseaseWarningForm.form, plantDiseaseModelDao.allForSelect()))
      },
      diseaseWarningData => {
        Redirect(routes.DiseaseWarningController.index())
      }
    )
  }
}
