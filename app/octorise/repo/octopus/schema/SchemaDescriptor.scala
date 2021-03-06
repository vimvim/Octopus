package octorise.repo.octopus.schema

import scala.beans.BeanProperty
import octorise.repo.octopus.models.Attribute

/**
 *
 */
class SchemaDescriptor {

  @BeanProperty
  var name: String =_

  var descriptors: Map[String,AttributeDescriptor[Any]] = Map()

  def setDescriptors(descriptors: java.util.List[AttributeDescriptor[Any]]) = {

    val itr = descriptors.iterator()

    while (itr.hasNext) {

      val descriptor = itr.next()

      this.descriptors = this.descriptors + Pair(descriptor.name, descriptor)
    }
  }

  def getDescriptor[T :Manifest](attrName: String):AttributeDescriptor[T] = {

    descriptors.get(attrName) match {

      case Some(descriptor) => {

        if (!descriptor.isInstanceOf[AttributeDescriptor[T]]) {

          throw new Exception("Invalid attribute type "+name+":"+attrName+" "+classManifest[T].erasure.getName)
        }

        descriptor.asInstanceOf[AttributeDescriptor[T]]
      }

      case None => throw new Exception("Attribute descriptor is not found.")
    }
  }
}
