package octorise.repo

/**
 *
 */
sealed class Response

case class RedirectResponse(repository:Repository, path:String) extends Response

case class ContentResponse(kind:String, content:AnyRef) extends Response

