package models

import javax.persistence._
import scala.beans.BeanProperty
import services.SchemaRefService
import schema.{AttributeDescriptor, SchemaDescriptor, SchemasRegistry, NodeType}
import java.util

/**
 *
 */
@Entity
@Table(name="nodes")
@Inheritance(strategy=InheritanceType.JOINED)
abstract class Node {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  @Column(name="id")
  var id: Int = _

  @BeanProperty
  @Column(name="name")
  var name: String = _

  @BeanProperty
  @OneToMany(mappedBy = "node")
  var attributes: java.util.List[Attribute] = new util.ArrayList[Attribute]()

  @ManyToMany
  @JoinTable(name="applied_schemas")
  var appliedSchemas: java.util.Set[SchemaRef] = new java.util.LinkedHashSet[SchemaRef]()

  @Transient
  var nodeType: NodeType =_

  @Transient
  var schemaRefService: SchemaRefService =_

  @Transient
  var schemasRegistry: SchemasRegistry =_

  
  def applySchema(schemaName: String) ={

    val schema = schemasRegistry.getSchema(schemaName)

    if (!isSchemaApplied(schema)) {
      appliedSchemas.add(schemaRefService.getRef(schema))
    }
  }
  
  def removeSchema(schemaName: String) ={

    val schema = schemasRegistry.getSchema(schemaName)

    if (isSchemaApplied(schema)) {
      appliedSchemas.remove(schemaRefService.getRef(schema))
    }
  }

  def isSchemaApplied(schema: SchemaDescriptor) = {
    appliedSchemas.contains(schemaRefService.getRef(schema))
  }

  /*
  def setAttribute(schemaName: String, name: String, value: Int) ={
    applyAttributeValue(schemaName, name, value)
  }

  def setAttribute(schemaName: String, name: String, value: String) ={
    applyAttributeValue(schemaName, name, value)
  }

  def setAttribute(schemaName: String, name: String, value: Boolean) ={
    applyAttributeValue(schemaName, name, value)
  }

  def setAttribute(schemaName: String, name: String, value: Node) ={
    applyAttributeValue(schemaName, name, value)
  }
  */

  def removeAttribute(schemaName: String, name: String) {

    val schema = schemasRegistry.getSchema(schemaName)
    val descriptor = schema.getDescriptor(name)
    val schemaRef = schemaRefService.getRef(schema)

    findAttribute(schemaRef, name) match {

      case Some(attribute) => attributes.remove(attribute)

      case None =>
    }
  }

  def setAttribute[T](schemaName: String, name: String, value: T) ={

    val schema = schemasRegistry.getSchema(schemaName)
    val descriptor = schema.getDescriptor(name)
    val schemaRef = schemaRefService.getRef(schema)

    if (!isSchemaApplied(schema)) appliedSchemas.add(schemaRef)

    findAttribute(schemaRef, name) match {

      case Some(attribute) => {

        if (descriptor.multiValue) {

          addNewAttribute[T](descriptor.asInstanceOf[AttributeDescriptor[T]], value)
        } else {

          attribute.asInstanceOf[Attribute[T]].applyValue(value)
        }
      }

      case None => {

        addNewAttribute[T](descriptor.asInstanceOf[AttributeDescriptor[T]], value)
      }
    }
  }

  protected def findAttribute(schemaRef: SchemaRef, name: String): Option ={

    val itr = attributes.iterator()

    while (itr.hasNext) {

      val attr = itr.next()

      if (attr.getSchemaRef.equals(schemaRef) && attr.getName.equals(name)) return Some(attr)
    }

    None
  }

  protected def addNewAttribute[T](descriptor: AttributeDescriptor[T], value: T) = {
    attributes.add(descriptor.createAttribute(value).asInstanceOf[Attribute])
  }

  def beginEdit(schemasRegistry: SchemasRegistry, schemaService: SchemaRefService) = {
    this.schemasRegistry = schemasRegistry
    this.schemaRefService = schemaService
  }

  def endEdit() = {

  }

  def getNodeType: NodeType = {
    nodeType
  }

  protected def setNodeType(nodeType: NodeType) = {
    this.nodeType = nodeType
  }

  @PostLoad
  protected def injectNodeType() = {

  }

}
