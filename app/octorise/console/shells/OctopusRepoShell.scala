package octorise.console.shells

import org.springframework.beans.factory.annotation.{Qualifier, Autowired, Configurable}
import org.springframework.transaction.{TransactionDefinition, PlatformTransactionManager}
import org.springframework.transaction.support.DefaultTransactionDefinition

import groovy.lang.{MissingPropertyException, Closure}

import spring.SpringContextHolder

import octorise.repo.octopus.schema.NodeTypesRegister
import octorise.repo.octopus.models.Node
import octorise.repo.octopus.NodeApiFacade
import octorise.repo.octopus.services.NodeService
import octorise.repo.octopus.repositories.NodesRepo

import octorise.console.OpenObjectShell


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

  @Autowired
  @Qualifier("nodesRepo")
  var nodesRepo:NodesRepo[Node] =_

  /**
   * Print shell info
   */
  def info(): Unit = {
    println("Octopus repository management shell")
  }

  /**
   * Will list nodes in the current scope.
   */
  def list(): Unit = {

    val nodes = nodesRepo.findByParent(getCurrentNode)
    nodes.foreach({
      node=>
        println(s"  $node")
    })
  }

  /**
   * List nodes of the specified type in the current scope.
   *
   * @param typeName      Node type name
   */
  def list(typeName:String): Unit = {

    typesRegister.getNodeType[Node](typeName) match {

      case Some(nodeType) =>

        val nodes = nodeType.repo.findByParent(getCurrentNode)
        nodes.foreach({
          node=>
            println(s"  $node")
        })

      case None =>
        println(s"Node type is not found: $typeName")

    }
  }

  /**
   * Delete by id
   *
   * @param id        Id of the target node
   */
  def delete(id:Int): Unit = {



    println(s"Delete node with id: $id")
  }

  /**
   * Delete by Slug
   *
   * @param slug      Slug of the target node
   */
  def delete(slug:String): Unit = {
    println(s"Delete node with slug: $slug")
  }

  /**
   * Open specified node
   *
   * @param slug    Node slug
   */
  def open(slug:String): Unit = {

    nodesRepo.findBySlug(getCurrentNode, slug) match {

      case Some(node) =>
        setProperty("node", node)
        delegateCommand(OpenObjectShell(node.slug, node.getClass))

      case None =>
        println(s"Node is not found: $slug ")
    }
  }

  /**
   * Create node of the specified type.
   *
   * @param typeName        Node type
   * @param initClosure     Initialize function
   */
  def create(typeName:String, initClosure: Closure[AnyRef]):Node = {

    typesRegister.getNodeType[Node](typeName) match {

      case Some(nodeType) =>

        val transactionManager = SpringContextHolder.getContext.getBean(classOf[PlatformTransactionManager])
        val transactionDef = new DefaultTransactionDefinition()
        transactionDef.setName("SomeTxName")
        transactionDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED)

        val transactionStatus = transactionManager.getTransaction(transactionDef)

        // nodeType.repo.findBySlug()
        val node = nodeType.service.create({
          node=>

            node.setParent(getCurrentNode)

            initClosure.setDelegate(node)
            initClosure.setResolveStrategy(Closure.DELEGATE_ONLY)
            initClosure.run()
        })

        transactionManager.commit(transactionStatus)

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

  def edit(slug:String, editClosure: Closure[AnyRef]):Node = {

    nodeApiFacade.findBySlug[Node](getCurrentNode, slug, {(node, service, repo)=>

      editClosure.setDelegate(node)
      editClosure.setResolveStrategy(Closure.DELEGATE_ONLY)

      service.update(node, {
        node=>
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

  private def getCurrentNode:Node = {

    try {
      getProperty("node").asInstanceOf[Node]
    } catch {
      case e: MissingPropertyException => null
    }
  }


}
