package models

import scala.beans.BeanProperty

import javax.persistence.{Column, PrimaryKeyJoinColumn, Table, Entity}

/**
 *
 */
@Entity
@Table(name = "tweets")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class Tweet extends Sentiment {

  @BeanProperty
  @Column(name="tweet_id")
  var tweetId: String = _


}
