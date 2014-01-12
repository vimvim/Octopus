package octorise.repo.external.services

/**
 *
 */
trait ExternalRepository {

  /**
   * Disconnect external repository
   * @param repoName
   */
  def disconnect(repoName: String)
}

