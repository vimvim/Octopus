package schema

import models.Attribute

/**
 *
 */
class SchemaDescriptor {

  var name: String =_

  var descriptors: Map[String,AttributeDescriptor[Any]] =_

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