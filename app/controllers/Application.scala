package controllers

import infrastructure.security.Auth
import play.api.mvc._

object Application extends Controller {

  def index = Auth.AuthenticatedAction { implicit request =>
    Ok(views.html.index("Your new application is ready."))
  }

}