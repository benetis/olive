package controllers

import javax.inject._

import dao.SampleDAO
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import models.Sample
import play.api._
import scala.concurrent.duration._


@Singleton
class Samples @Inject()(sampleDao: SampleDAO) extends Controller {

  def index = Action.async {
    sampleDao.all().map {
      sample: Seq[Sample] => Ok(views.html.samples(sample)) }
  }

  def insertSample = TODO
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
