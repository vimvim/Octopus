package models

import javax.persistence.{Table, Entity, PrimaryKeyJoinColumn}

/**
 *
 */
@Entity
@Table(name = "sentiment")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class Sentiment extends Node {

}
