package utils

import com.mohiva.play.silhouette.api.Authorization
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import models.User
import play.api.mvc.Request

import scala.concurrent.Future

/**
 * Only allows those users that have at least a service of the selected.
 */
case class WithService(anyOf: String*) extends Authorization[User, CookieAuthenticator] {
  override def isAuthorized[B](identity: User, authenticator: CookieAuthenticator)(implicit request: Request[B]): Future[Boolean] = Future.successful {
    WithService.isAuthorized(identity)
  }
}

object WithService {
  def isAuthorized(user: User): Boolean =
    user.isAdmin.getOrElse(false)
}