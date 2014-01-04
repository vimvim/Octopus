package octorise.repo.octopus.models

import scala.beans.BeanProperty
import javax.persistence._

/**
 * Represents content.
 * Content can be structured and contain references to the sub content.
 *
 * TODO: Make this abstract. Create TextContent, BinaryContent, CompoundContent
 */
@Entity
@Table(name = "content")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class Content extends Node {

  /**
   * Holds main content. In addition node can contain content in the applied schemas.
   */
  @BeanProperty
  @Column(name="content")
  var content: String = _

  // TODO: Think about storing schema name in the special attribute of the Content

  // TODO: Think about using special attributes schema for storing references to the subcontent.
  // TODO: How content presenter will be able to known schema name in this case ??
  // TODO: Solution : Allow to have schemas with the same name for different types of the node
//  @ManyToMany
//  @JoinTable(name="subcontent")
//  var subcontent: java.util.Set[SchemaRef] = new java.util.LinkedHashSet[SchemaRef]()

  override def toString = s" $id Content $slug $name "

}
