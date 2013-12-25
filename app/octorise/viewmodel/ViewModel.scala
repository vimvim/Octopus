package octorise.viewmodel

/**
 *
 */
class ViewModel[T] {

  def value[VT](vo:Option[VT], default:VT) = {

    vo match {
      case Some(v) => v
      case None => default
    }
  }
}
