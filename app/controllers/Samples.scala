package controllers

import javax.inject._

import dao.SampleDAO
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import models.Sample
import play.api._
import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.duration._


@Singleton
class Samples @Inject()(sampleDao: SampleDAO) extends Controller {

  def index = Action.async {
    sampleDao.all().map {
      sample: Seq[Sample] => Ok(views.html.samples(sample)) }
  }

  //TODO: can use some imrpovements for code
  def insertSample() = Action.async { implicit request =>
    request.body.asJson.map { json =>
      json.validate[Sample] match {
        case JsSuccess(s, _) => sampleDao.insert(s).map(_ => Ok(Json.obj("status" -> "OK")))
        case JsError(_) => Future.successful(BadRequest("error"))
      }
    } match {
      case Some(a) => a
      case None => Future.successful(BadRequest("db error"))
    }
  }
//  def saveSamples = Action(BodyParsers.parse.json) { request =>
//    val b = request.body.validate[Book]
//    b.fold(
//      errors => {
//        BadRequest(Json.obj("status" -> "OK", "message" -> JsError.toFlatJson(errors)))
//      },
//      book => {
//        addBook(book)
//        Ok(Json.obj("status" -> "OK"))
//      }
//    )
//  }

}
