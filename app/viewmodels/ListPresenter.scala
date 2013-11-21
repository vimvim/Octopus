package viewmodels

import scala.collection.JavaConverters._

import repositories.NodesRepo
import models.Node

/**
 * Used for present list of the models.
 */
// class ListPresenter[T <:Node : Manifest , VT <: ViewModel[T]](_repo: NodesRepo[T], modelClass: Class[T])  {
class ListPresenter[T <:Node : Manifest , VT <: ViewModel[T]](_repo: NodesRepo[T])  {

  val repo: NodesRepo[T] = _repo

  def totalCount: Int = {
    repo.totalCount
  }

  def get(offset:Int, limit:Int): List[T] = {

    val constructors = manifest.erasure.getConstructors
    val constructor = constructors.apply(0)

    var retVal: List[T] = List()
    for(model <- repo.fetch(offset, limit)) {

      val viewModel = constructor.newInstance(model.asInstanceOf[T]).asInstanceOf[T]
      retVal = viewModel:: retVal
    }

    retVal
  }

}
