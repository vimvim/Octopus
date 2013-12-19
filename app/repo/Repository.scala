package repo

import scala.concurrent.Future

/**
 * Represent abstract content repository
 */
trait Repository {

  def fetchNode(pathSegments:List[String]):Future[Cursor]
}


