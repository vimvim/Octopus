package octorise.repo.octopus.models

import java.util

import scala.beans.BeanProperty
import javax.persistence._

import org.springframework.beans.factory.annotation.{Autowired, Configurable}

import octorise.repo.octopus.schema.{AttributeDescriptor, SchemaDescriptor, SchemasRegistry, NodeType}
import octorise.repo.octopus.services.SchemaRefService


/**
 *
 */
@Entity
@Table(name="nodes")
@Inheritance(strategy=InheritanceType.JOINED)
@Configurable
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
  @Column(name="slug")
  var slug: String = _

  @BeanProperty
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="parent_id")
  var parent: Node =_

  @BeanProperty
  @OneToMany(mappedBy = "node", cascade = Array(CascadeType.ALL))
  var attributes: java.util.List[Attribute[_]] = new util.ArrayList[Attribute[_]]()

  @ManyToMany
  @JoinTable(name="applied_schemas")
  var appliedSchemas: java.util.Set[SchemaRef] = new java.util.LinkedHashSet[SchemaRef]()

  @Transient
  var nodeType: NodeType[Node] =_

  @Transient
  @Autowired
  var schemaRefService: SchemaRefService =_

  @Transient
  @Autowired
  var schemasRegistry: SchemasRegistry =_

  def beginEdit(schemasRegistry: SchemasRegistry, schemaService: SchemaRefService) = {
    this.schemasRegistry = schemasRegistry
    this.schemaRefService = schemaService
  }

  def endEdit() = {

  }

  def getNodeType: NodeType[Node] = {
    nodeType
  }

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

    findAttribute[T](schemaRef, name) match {

      case Some(attribute) => {

        if (descriptor.multiValue) {

          addNewAttribute[T](schemaRef, descriptor.asInstanceOf[AttributeDescriptor[T]], value)
        } else {

          attribute.asInstanceOf[Attribute[T]].applyValue(value)
        }
      }

      case None => {

        addNewAttribute[T](schemaRef, descriptor.asInstanceOf[AttributeDescriptor[T]], value)
      }
    }
  }

  def attr[T](schemaName: String, name: String):Option[T] = {

    val schema = schemasRegistry.getSchema(schemaName)
    val descriptor = schema.getDescriptor(name)
    val schemaRef = schemaRefService.getRef(schema)

    if (!isSchemaApplied(schema)) {

      None
    } else {

      findAttribute[T](schemaRef, name) match {

        case Some(attribute) => {

          if (descriptor.multiValue) {

            // TODO: Implement return multi-value
            None
            // Some(attribute.getValues.get(0))
          } else {

            Some(attribute.getValue)
          }
        }

        case None => {

          None
        }
      }
    }
  }

  def attrs(schemaName: String):Map[String, Attribute] = {

  }

  protected def findAttribute[T](schemaRef: SchemaRef, name: String): Option[Attribute[T]] ={

    val itr = attributes.iterator()

    while (itr.hasNext) {

      val attr = itr.next()

      if (attr.getSchemaRef.equals(schemaRef) && attr.getName.equals(name)) return Some(attr.asInstanceOf[Attribute[T]])
    }

    None
  }

  protected def addNewAttribute[T](schemaRef:SchemaRef, descriptor: AttributeDescriptor[T], value: T) = {

    val attribute = descriptor.createAttribute(value)
    attribute.setName(descriptor.name)
    attribute.setSchemaRef(schemaRef)
    attribute.setNode(this)

    attributes.add(attribute.asInstanceOf[Attribute[_]])
  }

  protected def setNodeType(nodeType: NodeType[Node]) = {
    this.nodeType = nodeType
  }

  @PostLoad
  protected def injectNodeType() = {

  }

}
