import infrastructure.security.Auth
import play.api._
import play.api.mvc._

import scala.concurrent.Future

class MyFilter extends Filter {

  override def apply(f: (RequestHeader) => Future[Result])(rh: RequestHeader): Future[Result] = {

    f(rh)
  }
}

object Global extends WithFilters(new MyFilter) {

  override def onStart(app: Application): Unit = {

    Auth.LoginUrl = Some[Call](controllers.routes.LoginController.viewLogin())
  }
}