package octorise.viewmodel

/**
 * Used for present single model.
 *
 */
trait ModelPresenter[T] {

  def get(): T

}
