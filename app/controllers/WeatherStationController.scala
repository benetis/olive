package controllers

import javax.inject._

import models.daos.WeatherStationDAO
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.api.i18n.{ I18nSupport, Messages, MessagesApi }

@Singleton
class WeatherStationController @Inject()(
  weatherStationDao: WeatherStationDAO,
  val messagesApi: MessagesApi
) extends Controller with I18nSupport {

  def index = Action.async {
    weatherStationDao.all().map {
      weatherStations => Ok(views.html.weatherStations(weatherStations)) }
  }
}
