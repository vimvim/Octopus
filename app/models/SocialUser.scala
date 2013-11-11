package models

import javax.persistence.{PrimaryKeyJoinColumn, Table, Entity}
import scala.beans.BeanProperty

/**
 *
 * Represent reference to the user from one ( or several ) of the social network.
 */
@Entity
@Table(name = "social_users")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class SocialUser extends Node {



}
