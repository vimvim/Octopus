package octorise.repo.octopus.models

import scala.beans.BeanProperty
import javax.persistence._

/**
 * Represents content.
 * Content can be structured and contain references to the sub content.
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

  // TODO: Think about using special attributes schema for storing references to the subcontent.
  // TODO: How content presenter will be able to known schema name in this case ??
//  @ManyToMany
//  @JoinTable(name="subcontent")
//  var subcontent: java.util.Set[SchemaRef] = new java.util.LinkedHashSet[SchemaRef]()

}
