package services

import models.Credentials

object ServiceRegistry {
  val Authentication: AuthenticationService = new HardcodedAuthenticationService(Credentials("Andrei", "password"))
}