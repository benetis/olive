package utils

import models.User
import play.api.i18n.Messages
import play.twirl.api.Html
import scala.language.implicitConversions
object Mailer {

  implicit def html2String(html: Html): String = html.toString

  def welcome(user: User, link: String)(implicit ms: MailService, m: Messages) {
    ms.sendEmailAsync(user.email.getOrElse(""))(
      subject = Messages("mail.welcome.subject"),
      bodyHtml = views.html.auth.mails.welcome(user.firstName.getOrElse(""), link),
      bodyText = views.html.auth.mails.welcomeTxt(user.firstName.getOrElse(""), link)
    )
  }
//
//  def forgotPassword(email: String, link: String)(implicit ms: MailService, m: Messages) {
//    ms.sendEmailAsync(email)(
//      subject = Messages("mail.forgotpwd.subject"),
//      bodyHtml = mails.forgotPassword(email, link),
//      bodyText = mails.forgotPasswordTxt(email, link)
//    )
//  }

}