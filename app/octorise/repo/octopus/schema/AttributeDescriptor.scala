package octorise.repo.octopus.schema

import scala.beans.BeanProperty
import octorise.repo.octopus.models.Attribute

/**
 *
 */
abstract class AttributeDescriptor[T] {

  @BeanProperty
  var name: String = _

  @BeanProperty
  var multiValue: Boolean = false

  def createAttribute(value: T): Attribute[T] = {

    val attribute = createAttributeEntity()
    attribute.applyValue(value)

    attribute
  }

  protected def createAttributeEntity(): Attribute[T]

}
