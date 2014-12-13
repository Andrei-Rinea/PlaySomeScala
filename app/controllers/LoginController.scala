package controllers

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
      val authenticated = ServiceRegistry.Authentication.authenticate(boundForm.get)
      if (!authenticated) {
        Ok(views.html.login(boundForm.withGlobalError("Invalid credentials")))
      }
      else {
        // determine URL to go back to or else root
        Redirect(routes.Application.index())
      }
    }
  }
}