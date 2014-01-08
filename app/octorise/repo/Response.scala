package octorise.repo

/**
 *
 */
sealed class Response

case class RedirectResponse(repository:Repository[_], location:Location) extends Response

case class ContentResponse[T](kind:String, content:T) extends Response

case class NotFoundResponse() extends Response
