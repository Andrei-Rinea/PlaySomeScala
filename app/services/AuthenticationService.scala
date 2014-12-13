package services

import models.Credentials

trait AuthenticationService {
  def authenticate(credentials : Credentials) : Boolean
}