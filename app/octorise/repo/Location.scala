package octorise.repo

/**
 * Represent content location inside of the repository.
 *
 */
class Location

case class AbsoluteLocation(path:String) extends Location

case class RelativeLocation[T](parent:T, path:String) extends Location


