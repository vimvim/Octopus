package octorise.repo.external.models

import javax.persistence._
import org.springframework.beans.factory.annotation.Configurable
import scala.beans.BeanProperty

/**
 * Represents external repository
 */
@Entity
@Table(name="external_repos")
@Inheritance(strategy=InheritanceType.JOINED)
@Configurable
class ExternalRepo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  @Column(name="id")
  var id: Int = _

  @BeanProperty
  @Column(name="name")
  var name: String = _
}
