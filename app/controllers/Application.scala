package controllers

import javax.inject._

import play.api.Logger
import play.api.i18n.{Lang, MessagesApi}
import play.api.mvc._
import utils.silhouette.{AuthenticationController, AuthenticationEnvironment, WithService, WithServices}
import scala.concurrent.Future

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class Application @Inject() (val env: AuthenticationEnvironment, val messagesApi: MessagesApi) extends AuthenticationController {

  def index = UserAwareAction.async { implicit request =>
    Future.successful(Ok(views.html.index()))
  }

  def myAccount = SecuredAction.async { implicit request =>
    Future.successful(Ok(views.html.myAccount()))
  }

  // REQUIRED ROLES: serviceA (or master)
  def serviceA = SecuredAction(WithService("serviceA")).async { implicit request =>
    Future.successful(Ok(views.html.serviceA()))
  }

  // REQUIRED ROLES: serviceA OR serviceB (or master)
  def serviceAorServiceB = SecuredAction(WithService("serviceA", "serviceB")).async { implicit request =>
    Future.successful(Ok(views.html.serviceAorServiceB()))
  }

  // REQUIRED ROLES: serviceA AND serviceB (or master)
  def serviceAandServiceB = SecuredAction(WithServices("serviceA", "serviceB")).async { implicit request =>
    Future.successful(Ok(views.html.serviceAandServiceB()))
  }

  // REQUIRED ROLES: master
  def settings = SecuredAction(WithService("master")).async { implicit request =>
    Future.successful(Ok(views.html.settings()))
  }

  def selectLang(lang: String) = Action { implicit request =>
    Logger.logger.debug("Change user lang to : " + lang)
    request.headers.get(REFERER).map { referer =>
      Redirect(referer).withLang(Lang(lang))
    }.getOrElse {
      Redirect(routes.Application.index).withLang(Lang(lang))
    }
  }

}
