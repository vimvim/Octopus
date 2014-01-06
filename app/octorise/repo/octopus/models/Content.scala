package octorise.repo.octopus.models

import scala.beans.BeanProperty
import javax.persistence._

/**
 * Represents content
 */
@Entity
@Table(name = "content")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
abstract class Content extends Node {


  override def toString = s" $id Content $slug $name "

}
