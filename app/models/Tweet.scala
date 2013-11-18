package models

import scala.beans.BeanProperty

import javax.persistence.{Column, PrimaryKeyJoinColumn, Table, Entity}
import scala.Array
import org.hibernate.annotations.Index

/**
 *
 */
@Entity
@Table(name = "tweets")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@org.hibernate.annotations.Table(
  appliesTo = "tweets",
  indexes = Array(
    new Index(name="tweet_id", columnNames=Array("tweet_id"))
  )
)
class Tweet extends Sentiment {

  @BeanProperty
  @Column(name="tweet_id")
  var tweetId: String = _


}
