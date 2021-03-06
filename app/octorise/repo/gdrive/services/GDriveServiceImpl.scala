package octorise.repo.gdrive.services

import java.util

import scala.concurrent.{ExecutionContext, Future}

import ExecutionContext.Implicits.global

import com.google.api.client.googleapis.auth.oauth2.{GoogleCredential, GoogleBrowserClientRequestUrl}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson.JacksonFactory
import com.google.api.services.drive.Drive

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

import octorise.repo.gdrive.models.{GDriveModelsRepo, GDriveRepo}

import play.api.libs.ws.WS


/**
 * GDrive based repository
 */
@Service("gdrive")
class GDriveServiceImpl extends GDriveService {

  val CLIENT_ID = "XXXXXXXXXXX.apps.googleusercontent.com"
  val CLIENT_SECRET = "XXXXXXXXXXXXX"

  val redirectURI = "http://localhost:9000/gdrive/auth/"
  val googleAuthUrl = "https://accounts.google.com/o/oauth2/token"

  val httpTransport = new NetHttpTransport
  val jsonFactory = new JacksonFactory

  @Autowired
  var repo:GDriveModelsRepo =_

  /**
   * Create GDrive repository representation
   *
   * @param repoName      GDrive repository model name
   *
   * @return Auth url
   */
  def create(repoName: String):GDriveRepo = {

    repo.findByName(repoName) match {
      case Some(gdriveRepo) => gdriveRepo
      case None =>

        val gdriveRepo = new GDriveRepo()
        gdriveRepo.setName(repoName)

        repo.saveAndFlush(gdriveRepo)
    }
  }

  /**
   * Find GDrive repository by name
   *
   * @param repoName      GDrive repository model name
   * @return
   */
  def get(repoName: String):Option[GDriveRepo] = {
    repo.findByName(repoName)
  }

  /**
   * Authorize and connect GDrive repository
   *
   * @param repo          GDrive repository model
   */
  def auth(repo:GDriveRepo):String = {

    new GoogleBrowserClientRequestUrl(
      CLIENT_ID,
      redirectURI+"?repoName="+repo.name,
      util.Arrays.asList(
        "https://www.googleapis.com/auth/userinfo.email",
        "https://www.googleapis.com/auth/userinfo.profile",
        "https://www.googleapis.com/auth/drive")
    ).set("access_type", "offline").set("response_type", "code").build()
  }

  /**
   * Confirm GDrive access.
   *
   * @param repo            GDrive repository model
   * @param authCode        Auth code passed to the redirect by Google
   */
  def authConfirm(repo:GDriveRepo, authCode:String):Future[Boolean] = {

    val url = "https://accounts.google.com/o/oauth2/token"
    val urlParameters = s"code=$authCode&client_id=$CLIENT_ID&client_secret=$CLIENT_SECRET&redirect_uri="+redirectURI+repo.name+"&grant_type=authorization_code&Content-Type=application/x-www-form-urlencoded"

    WS.url(s"$url?$urlParameters")
      .get()
      .map(
      result => {

        true
      }
    )

    // Expect to get: "access_token" : "ya29.AHES6ZSyNMy0btyYrYflOPkYGEF6ijCKjCASjYoxK5CuqHPOHLDG0og",  "expires_in" : 3600,  "refresh_token" : "1/U33p2VTgqIqtll2P43x6PVen156ys0EMGcrkYW4lYuMI"

  }

  /**
   * Disconnect external repository
   *
   * @param repo            GDrive repository model
   */
  def disconnect(repo:GDriveRepo): Unit = {


  }

  private def createGDrive(accessToken: String): Drive = {

    //Build the Google credentials and make the Drive ready to interact
    val credential = new GoogleCredential.Builder()
      .setJsonFactory(jsonFactory)
      .setTransport(httpTransport)
      .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
      .build()
    credential.setAccessToken(accessToken)

    //Create a new authorized API client
    new Drive.Builder(httpTransport, jsonFactory, credential).build()
  }

}
