package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import models.Sample
import models.Sample.tempAndClockedFormat
import models.daos.{SampleDAO, WeatherStationDAO}
import play.api._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json._
import utils.auth.{AuthenticationController, DefaultEnv}

import scala.concurrent.Future
import scala.concurrent.duration._


@Singleton
class SampleController @Inject()(
  sampleDao: SampleDAO,
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport with AuthenticationController {

  def index = silhouette.SecuredAction.async { implicit request =>
    sampleDao.all().map {
      samples: Seq[Sample] => Ok(views.html.samples(samples)) }
  }

  def getRange(from: String, to: String) = silhouette.SecuredAction.async { implicit request =>
    sampleDao.getJsonSamples().map {
      samples  => Ok(Json.toJson(samples)(tempAndClockedFormat)) }
  }

  //TODO: can use some imrpovements for code
  def insertSample() = Action.async { implicit request =>
    request.body.asJson.map { json => json.validate[Sample] match {
        case JsSuccess(s, _) => sampleDao.insert(s).map(_ => Ok(Json.obj("status" -> "OK")))
        case err@JsError(_) => Future.successful(BadRequest(err.toString))
      }
    } match {
      case Some(a) => a
      case None => Future.successful(BadRequest("json error"))
    }
  }

}
