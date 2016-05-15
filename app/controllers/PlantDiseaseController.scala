package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.Sample
import models.Sample.tempAndClockedFormat
import models.daos.SampleDAO
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import utils.auth.{AuthenticationController, DefaultEnv}

import scala.concurrent.Future


@Singleton
class PlantDiseaseController @Inject()(
  sampleDao: SampleDAO,
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport with AuthenticationController {

  def index = silhouette.SecuredAction.async { implicit request =>
    sampleDao.all().map {
      samples: Seq[Sample] => Ok(views.html.samples(samples)) }
  }
}
