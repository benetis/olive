package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.daos.WeatherStationDAO
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import utils.auth.DefaultEnv

import scala.concurrent.Future

@Singleton
class WeatherStationController @Inject()(
  weatherStationDao: WeatherStationDAO,
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport {

  def index = silhouette.SecuredAction.async { implicit request =>
    weatherStationDao.all().map {
      weatherStations => Ok(views.html.weatherStations(weatherStations)) }
  }
}
