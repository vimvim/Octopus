package octorise.repo.gdrive.services

import scala.concurrent.Future

import octorise.repo.gdrive.models.GDriveRepo


/**
 *
 */
trait GDriveService {

  def create(repoName: String):GDriveRepo

  def get(repoName: String):Option[GDriveRepo]

  def auth(repo:GDriveRepo):String

  def authConfirm(repo:GDriveRepo, authCode:String):Future[Boolean]

  def disconnect(repo:GDriveRepo)

}
