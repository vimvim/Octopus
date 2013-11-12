package repositories

import scala.reflect.ClassTag

import models.Node

/**
 *
 */
trait NodesRepo[T <: Node] {

  def save(entity: T): Unit

  def find(id: Int): Option[T]

  def findOneBySchemaAttrValue[VT: ClassTag](schemaName: String, attrName: String, value: VT): Option[T]

}
