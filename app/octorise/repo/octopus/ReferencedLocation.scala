package octorise.repo.octopus

import octorise.repo.Location
import octorise.repo.octopus.models.Node

/**
 * Special type of the location supported by Octopus repository.
 * Sub node referenced by specified attribute ( AttributeNode )
 */
case class ReferencedLocation[T <: Node](node:T, schemaName:String, attrName:String) extends Location
