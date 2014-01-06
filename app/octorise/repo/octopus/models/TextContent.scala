package octorise.repo.octopus.models

import javax.persistence.{Column, PrimaryKeyJoinColumn, Table, Entity}
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@Table(name = "text_content")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class TextContent extends Content {

  /**
   * Holds text content.
   */
  @BeanProperty
  @Column(name="content")
  var content: String = _

}
