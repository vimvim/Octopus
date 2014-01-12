package octorise.repo.gdrive.services

import java.util

import org.springframework.stereotype.Service

import com.google.api.client.googleapis.auth.oauth2.{GoogleCredential, GoogleBrowserClientRequestUrl}
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson.JacksonFactory
import com.google.api.services.drive.Drive

import octorise.repo.gdrive.models.GDriveRepo


/**
 * GDrive based repository
 */
@Service("gdrive")
class GDriveServiceImpl extends GDriveService {

  val CLIENT_ID = "XXXXXXXXXXX.apps.googleusercontent.com"
  val CLIENT_SECRET = "XXXXXXXXXXXXX"

  val redirectURI = "http://localhost:9000/gdrive/repoAuth/"
  val googleAuthUrl = "https://accounts.google.com/o/oauth2/token"

  val httpTransport = new NetHttpTransport
  val jsonFactory = new JacksonFactory

  /**
   * Create GDrive repository representation
   *
   * @param repoName
   *
   * @return Auth url
   */
  def create(repoName: String):GDriveRepo = {


  }

  /**
   * Authorize and connect GDrive repository
   *
   * @param repo
   */
  def auth(repo:GDriveRepo):String = {

    new GoogleBrowserClientRequestUrl(
      CLIENT_ID,
      redirectURI+repo.name,
      util.Arrays.asList(
        "https://www.googleapis.com/auth/userinfo.email",
        "https://www.googleapis.com/auth/userinfo.profile",
        "https://www.googleapis.com/auth/drive")
    ).set("access_type", "offline").set("response_type", "code").build()



  }

  /**
   * Confirm GDrive access.
   *
   * @param repo
   * @param authCode        Auth code passed to the redirect by Google
   */
  def authConfirm(repo:GDriveRepo, authCode:String) = {

    val url = "https://accounts.google.com/o/oauth2/token"
    val urlParameters = s"code=$authCode&client_id=************.apps.googleusercontent.com&client_secret=xxxxxxxxxx&redirect_uri="+redirectURI+repo.name+"&grant_type=authorization_code&Content-Type=application/x-www-form-urlencoded"


    // Expect to get: "access_token" : "ya29.AHES6ZSyNMy0btyYrYflOPkYGEF6ijCKjCASjYoxK5CuqHPOHLDG0og",  "expires_in" : 3600,  "refresh_token" : "1/U33p2VTgqIqtll2P43x6PVen156ys0EMGcrkYW4lYuMI"

  }

  /**
   * Disconnect external repository
   *
   * @param repoName
   */
  def disconnect(repoName: String): Unit = {


  }

  def createGDrive(accessToken: String): Drive = {

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
