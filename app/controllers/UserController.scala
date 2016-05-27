package controllers

import javax.inject._

import com.mohiva.play.silhouette.api.Silhouette
import models.daos.UserDAOImpl
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import utils.WithService
import utils.auth.{AuthenticationController, DefaultEnv}

@Singleton
class UserController @Inject()(
                                userDao: UserDAOImpl,
                                val messagesApi: MessagesApi,
                                silhouette: Silhouette[DefaultEnv]
) extends Controller with I18nSupport with AuthenticationController {

  def index = silhouette.SecuredAction(WithService()).async { implicit request =>
    userDao.all().map {
      users => Ok(views.html.users(users)) }
  }
}
