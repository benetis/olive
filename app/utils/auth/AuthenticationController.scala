package utils.auth

import com.mohiva.play.silhouette.api.Silhouette
import com.mohiva.play.silhouette.api.actions.{SecuredRequest, UserAwareRequest}
import models.User
import play.api.i18n.I18nSupport
import play.api.mvc.Controller

trait AuthenticationController extends I18nSupport with Controller {
  implicit def securedRequest2User[B](implicit request: SecuredRequest[DefaultEnv, B]): User = request.identity
  implicit def userAwareRequest2UserOpt[B](implicit request: UserAwareRequest[DefaultEnv, B]): Option[User] = request.identity
}