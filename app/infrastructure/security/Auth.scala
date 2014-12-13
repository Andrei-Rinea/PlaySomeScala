package infrastructure.security

import play.api.mvc._

object Auth extends scala.AnyRef with play.api.mvc.Results {

  var LoginUrl : Option[Call] = None

  def AuthenticatedAction(f: Request[AnyContent] => Result): Action[AnyContent] = {

    Action { request =>
      if (request.cookies.get("AuthToken") != None ) // insert authentication logic
        f(request)
      else
        Redirect(LoginUrl.get) // also add current request URL to querystring
    }
  }
}