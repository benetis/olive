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
class SampleController @Inject()(
  sampleDao: SampleDAO,
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport with AuthenticationController {

  def index = silhouette.SecuredAction.async { implicit request =>
      Future.successful(Ok(views.html.samples()))
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
