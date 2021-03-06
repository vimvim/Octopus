package octorise.repo.octopus.repositories

import scala.reflect.ClassTag

import octorise.repo.octopus.models.Node

/**
 *
 */
trait NodesRepo[T <: Node] {

  def save(entity: T): Unit

  def delete(entity: T): Unit

  def find(id: Int): Option[T]

  def findBySlug(node:Node, slug: String): Option[T]

  def findByPath(parent:Node, path: String): Option[T]

  def findByPathComponents(parent:Node, pathComponents: List[String]): Option[T]

  def findOneBySchemaAttrValue[VT: ClassTag](schemaName: String, attrName: String, value: VT): Option[T]

  def findByParent(node:Node, offset:Int = 0, limit:Int = 50):List[T]

  def fetch(offset:Int, limit:Int):List[T]

  def totalCount: Long

  def findChildSlugs(node: T):List[String]

}
