package services

import models.Credentials

class HardcodedAuthenticationService(credentials: Credentials) extends AuthenticationService {
  private val _credentials = credentials
  override def authenticate(credentials: Credentials): Boolean = {
    credentials == _credentials
  }
}