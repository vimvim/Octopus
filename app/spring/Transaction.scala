package spring

import javax.persistence.EntityManager

/**
 *
 * Can be used as are wrapper in the Controllers action handlers if there is no another way
 * will be found ( annotation based intercept of the actions as JPA plugin doing. )
 *
 */
object Transaction {

  /*
  def apply [A] (action: () => A): A = {

    val em = SpringContextHolder.getContext.getBean(classOf[EntityManager])
    val transaction = em.getTransaction

    transaction.begin ()
    try {
      val result = action
      if (transaction.isActive) // transaction may already be explicitly rolled back
      transaction.commit ()
      action
    }
    catch {
      case exception: Throwable =>
        if (transaction! = null && transaction.isActive)
          Transaction.Rollback ()
        throw exception
    }
    finally
      em.close ()
  }
  */

}
