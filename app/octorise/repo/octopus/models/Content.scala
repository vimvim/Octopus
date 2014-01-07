package octorise.repo.octopus.models

import scala.beans.BeanProperty
import javax.persistence._

/**
 * Represents content
 *
 */
@Entity
@Table(name = "content")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
abstract class Content extends Node {

  // TODO: Add reference to template !!
  // TODO: Template can be Spring beans, In this case we can refer to the template by name.

  override def toString = s" $id Content $slug $name "

}
