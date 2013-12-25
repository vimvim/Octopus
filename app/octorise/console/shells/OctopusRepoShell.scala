package octorise.console.shells

import groovy.lang.Closure
import org.springframework.beans.factory.annotation.{Autowired, Configurable}

import octorise.repo.octopus.schema.NodeTypesRegister
import octorise.repo.octopus.models.Node
import octorise.repo.octopus.NodeApiFacade
import octorise.repo.octopus.services.NodeService

/**
 * Shell allow to work with the nodes in the octopus repository.
 */
@Configurable
abstract class OctopusRepoShell extends BaseShell {

  @Autowired
  // @Qualifier("tweetService")
  var typesRegister:NodeTypesRegister = _

  @Autowired
  var nodeApiFacade:NodeApiFacade =_

  /**
   * Print shell info
   */
  def info(): Unit = {
    println("Octopus repository management shell")
  }

  /**
   * Open specified node
   *
   * @param slug    Node slug
   */
  def open(slug:String): Unit = {
    println("Will open node")
  }

  /**
   * Create node of the specified type.
   *
   * @param typeName        Node type
   * @param initClosure     Initialize function
   */
  def create(typeName:String, initClosure: Closure):Node = {

    typesRegister.getNodeType(typeName) match {

      case Some(nodeType) =>

        // nodeType.repo.findBySlug()
        val node = nodeType.service.create({node=>
          initClosure.setDelegate(node)
          initClosure.run()
        })

        println(s"Node created: $node")
        node.asInstanceOf[Node]

      case None =>
        println(s"Node type:$typeName is not found")
        null
    }
  }

  /**
   * Edit node
   *
   * @param slug          Node slug
   * @param editClosure   Closure
   */

  def edit(slug:String, editClosure: Closure):Node = {

    val currentNode = getProperty("node")

    nodeApiFacade.findBySlug(currentNode, slug, {(node, service, repo)=>

      editClosure.setDelegate(node)

      service.update({node=>
        editClosure.run()
      })
    }) match {
      case Some(node) => node
      case None =>
        println(s"Node :$slug is not found")
        null
    }
  }

  /**
   * Mount external repository to the specified point.
   *
   * @param slug        Slug specified where to mount repo
   * @param repoName    Name of the external repo
   */
  def mount(slug:String, repoName:String) = {

  }

}
