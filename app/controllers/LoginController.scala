package controllers

import java.net.URL

import infrastructure.security.Auth
import models.Credentials
import play.api.data.Forms._
import play.api.data._
import play.api.mvc._
import services.ServiceRegistry

object LoginController extends Controller {

  val form = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(Credentials.apply)(Credentials.unapply))

  def viewLogin = Action {
    Ok(views.html.login(form))
  }

  def doLogin = Action { implicit request =>

    val boundForm = form.bindFromRequest()
    if (boundForm.hasErrors)
      Ok(views.html.login(boundForm))
    else {
      if (!ServiceRegistry.Authentication.authenticate(boundForm.get)) {
        Ok(views.html.login(boundForm.withGlobalError("Invalid credentials")))
      }
      else {
        // set auth cookie
        // determine URL to go back to or else root
        Redirect(routes.Application.index())
          .withCookies(Cookie("AuthToken", "asldkfjlwejorjho"))
      }
    }
  }

  def logOut = Auth.AuthenticatedAction { implicit request =>
    val referer = request.headers.get("referer")
    if (referer.isEmpty) {
      NotImplemented
    }
    else {
      val url = new URL(referer.get)
      val path = url.getPath
      Redirect(path).discardingCookies(DiscardingCookie("AuthToken"))
    }
  }
}