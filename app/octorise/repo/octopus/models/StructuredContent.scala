package octorise.repo.octopus.models

import javax.persistence.{Column, PrimaryKeyJoinColumn, Table, Entity}
import scala.beans.BeanProperty

/**
 * Content node which is consist of the various content parts stored in or referred by in node attributes.
 *
 */
@Entity
@Table(name = "struct_content")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class StructuredContent extends Content {

  /**
   * Name of the content schema
   */
  @BeanProperty
  @Column(name="content_schema")
  var contentSchema: String = _


}
