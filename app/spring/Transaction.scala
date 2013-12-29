package spring

import org.springframework.transaction.support.DefaultTransactionDefinition
import org.springframework.transaction.{TransactionDefinition, PlatformTransactionManager}

/**
 *
 * Can be used as are wrapper for Spring JPA transaction
 *
 */
object Transaction {

  def apply[T] (action: => T): T = {

    val transactionManager = SpringContextHolder.getContext.getBean(classOf[PlatformTransactionManager])
    val transactionDef = new DefaultTransactionDefinition()
    transactionDef.setName("SomeTxName")
    transactionDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED)

    try {

      val retVal = action

      val transactionStatus = transactionManager.getTransaction(transactionDef)
      transactionManager.commit(transactionStatus)

      retVal

    } catch {

      case exception: Throwable =>
        val transactionStatus = transactionManager.getTransaction(transactionDef)
        transactionManager.rollback(transactionStatus)

        throw exception
    } finally {
    }
  }
}
