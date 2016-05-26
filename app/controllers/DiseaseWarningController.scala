package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import forms.DiseaseWarningForm
import models.DiseaseWarning
import models.daos.{DiseaseWarningDAO, PlantDiseaseModelDAO}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import utils.auth.{AuthenticationController, DefaultEnv}

import scala.concurrent.Future


@Singleton
class DiseaseWarningController @Inject()(
                                          diseaseWarningDao: DiseaseWarningDAO,
                                          plantDiseaseModelDao: PlantDiseaseModelDAO,
                                          val messagesApi: MessagesApi,
                                          silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport with AuthenticationController  {

  def index = silhouette.SecuredAction { implicit request =>
      Ok(views.html.disease_warnings.disease_warnings(diseaseWarningDao.triggeredWarningsAsModels(), diseaseWarningDao.notTriggeredWarnings()))
  }

  def createWarning = silhouette.SecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.disease_warnings.create_warning(DiseaseWarningForm.form, plantDiseaseModelDao.allForSelect())))
  }

  def submit = silhouette.SecuredAction.async { implicit request =>
    DiseaseWarningForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(BadRequest(views.html.disease_warnings.create_warning(DiseaseWarningForm.form, plantDiseaseModelDao.allForSelect())))
      },
      diseaseWarningData => {
        val userId = request.identity.userID
        //todo: research how to use UUID as type
        diseaseWarningDao.insert(DiseaseWarning(modelId = diseaseWarningData.modelId.toLong, userId = userId.toString))
          .map(_ => Redirect(routes.DiseaseWarningController.index()))
      }
    )
  }
}
