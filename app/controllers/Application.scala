package controllers

import javax.inject._

import dao.SampleDAO
import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Application @Inject() (sampleDao: SampleDAO) extends Controller {

  def index = Action.async {
    sampleDao.all().map(_ => Ok(views.html.index()))
  }

}
