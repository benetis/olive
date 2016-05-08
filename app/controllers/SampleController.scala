package controllers

import javax.inject._

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import models.Sample
import models.daos.SampleDAO
import play.api._
import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.duration._


@Singleton
class SampleController @Inject()(sampleDao: SampleDAO) extends Controller {

  def index = Action.async {
    sampleDao.all().map {
      sample: Seq[Sample] => Ok(views.html.samples()) }
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