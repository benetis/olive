package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.{PlantDiseaseFilter, PlantDiseaseModel, Sample}
import models.Sample.tempAndClockedFormat
import models.daos.{PlantDiseaseFilterDAO, PlantDiseaseModelDAO, SampleDAO}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import utils.auth.{AuthenticationController, DefaultEnv}

import scala.concurrent.Future


@Singleton
class PlantDiseaseController @Inject()(
                                        plantDiseaseModelDao: PlantDiseaseModelDAO,
                                        val messagesApi: MessagesApi,
                                        silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport with AuthenticationController {

  def index = silhouette.SecuredAction.async { implicit request =>
    plantDiseaseModelDao.all().map {
      model: Seq[PlantDiseaseModel] => Ok(views.html.plant_models()) }
  }
}
