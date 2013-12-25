package octorise.repo.octopus.models

import scala.beans.BeanProperty
import javax.persistence._

/**
 *
 */
@Entity
@Table(name = "sentiments")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class Sentiment extends Node {

  @BeanProperty
  @Column(name="text")
  var text: String =_

  @BeanProperty
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="social_user_id")
  var socialUser: SocialUser =_

}
