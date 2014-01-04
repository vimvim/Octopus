package octorise.repo

/**
 * Represent content location inside of the repository.
 *
 */
class Location

case class AbsoluteLocation(absolutePath:String) extends Location

case class RelativeLocation[T](parent:T, relativePath:String) extends Location


