package controllers

import java.util.UUID
import javax.inject.Inject

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.repositories.AuthInfoRepository
import com.mohiva.play.silhouette.api.services.AvatarService
import com.mohiva.play.silhouette.api.util.PasswordHasher
import com.mohiva.play.silhouette.impl.providers._
import forms.SignUpForm
import models.{MailTokenUser, User}
import models.services.UserService
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.{Action, Controller}
import utils.{Mailer, WithService}
import utils.auth.DefaultEnv
import views.html.auth

import scala.concurrent.Future

/**
 * The `Sign Up` controller.
 *
 * @param messagesApi The Play messages API.
 * @param silhouette The Silhouette stack.
 * @param userService The user service implementation.
 * @param authInfoRepository The auth info repository implementation.
 * @param avatarService The avatar service implementation.
 * @param passwordHasher The password hasher implementation.
 * @param webJarAssets The webjar assets implementation.
 */
class SignUpController @Inject() (
  val messagesApi: MessagesApi,
  silhouette: Silhouette[DefaultEnv],
  userService: UserService,
  authInfoRepository: AuthInfoRepository,
  avatarService: AvatarService,
  passwordHasher: PasswordHasher,
  implicit val webJarAssets: WebJarAssets)
  extends Controller with I18nSupport {

  /**
   * Views the `Sign Up` page.
   *
   * @return The result to display.
   */
  def view = silhouette.SecuredAction(WithService()).async { implicit request =>
    Future.successful(Ok(views.html.signUp(SignUpForm.form)))
  }

  /**
    * Handles the form filled by the user. The user and its password are saved and it sends him an email with a link to confirm his email address.
    */
//  def handleStartSignUp = silhouette.SecuredAction(WithService()) { implicit request =>
//    SignUpForm.form.bindFromRequest.fold(
//      formWithErrors => BadRequest(views.html.signUp(formWithErrors)),
//      user => {
//        val loginInfo = LoginInfo(CredentialsProvider.ID, user.email)
//        userService.retrieve(loginInfo).flatMap {
//          case Some(userN) =>
//            Future.successful(BadRequest(views.html.signUp(SignUpForm.form.withError("email", Messages("user.exists")))))
//          case None => {
//            val user = User(
//              userID = UUID.randomUUID(),
//              loginInfo = loginInfo,
//              firstName = Some(data.firstName),
//              lastName = Some(data.lastName),
//              fullName = Some(data.firstName + " " + data.lastName),
//              email = Some(data.email),
//              avatarURL = None,
//              isAdmin = None
//            )
//              val token = MailTokenUser(user.email, isSignUp = true)
//              for {
//                savedUser <- userService.save(user.copy)
//                _ <- new MailTokenUserService.create(token)
//              } yield {
//                Mailer.welcome(savedUser, link = routes.Auth.signUp(token.id).absoluteURL())
//                Ok(views.auth.almostSignedUp(savedUser))
//              }
//            }
//          }
//        }
//    )
//  }

  /**
   * Handles the submitted form.
   *
   * @return The result to display.
   */
  def submit = silhouette.SecuredAction(WithService()).async { implicit request =>
    SignUpForm.form.bindFromRequest.fold(
      form => Future.successful(BadRequest(views.html.signUp(form))),
      data => {
        val loginInfo = LoginInfo(CredentialsProvider.ID, data.email)
        userService.retrieve(loginInfo).flatMap {
          case Some(user) =>
            Future.successful(BadRequest(views.html.signUp(SignUpForm.form.withError("email", Messages("user.exists")))))
          case None =>
            val user = User(
              userID = UUID.randomUUID(),
              loginInfo = loginInfo,
              firstName = Some(data.firstName),
              lastName = Some(data.lastName),
              fullName = Some(data.firstName + " " + data.lastName),
              email = Some(data.email),
              avatarURL = None,
              isAdmin = None
            )
            val token = MailTokenUser(data.email, isSignUp = true)
            for {
              avatar <- avatarService.retrieveURL(data.email)
              user <- userService.save(user.copy(avatarURL = avatar))
              result <- Future.successful(Redirect(routes.ApplicationController.index()))
            } yield {
//              silhouette.env.eventBus.publish(SignUpEvent(user, request))
//              silhouette.env.eventBus.publish(LoginEvent(user, request))
              result
            }
        }
      }
    )
  }
}
