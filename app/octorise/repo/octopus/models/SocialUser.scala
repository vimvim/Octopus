package octorise.repo.octopus.models

import scala.beans.BeanProperty

import javax.persistence.{PrimaryKeyJoinColumn, Table, Entity}

/**
 *
 * Represent reference to the user from one ( or several ) of the social network.
 */
@Entity
@Table(name = "social_users")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class SocialUser extends Node {

  override def toString = s" $id SocialUser "

}
