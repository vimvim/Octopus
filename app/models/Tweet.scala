package models

import javax.persistence.{PrimaryKeyJoinColumn, Table, Entity}

/**
 *
 */
@Entity
@Table(name = "tweet")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class Tweet extends Sentiment {

}
