package controllers

import javax.inject._

import models.daos.WeatherStationDAO
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

@Singleton
class WeatherStationController @Inject()(weatherStationDao: WeatherStationDAO) extends Controller {

  def index = Action.async {
    weatherStationDao.create
    weatherStationDao.all().map {
      weatherStation => Ok(views.html.weatherStations()) }
  }
}
