
import play.api._

import org.springframework.context.support.ClassPathXmlApplicationContext
import spring.SpringContextHolder

import org.vaadin.playintegration.VaadinSupport

object Global extends GlobalSettings with VaadinSupport {

  /**
  * Sync the context lifecycle with Play's.
  * @param app
  */
  override def onStart(app: Application) {

    super.onStart(app)

    SpringContextHolder.init()


    // val dao: UsersDao = ctx.getBean(classOf[UsersDao])

    // dao.save(new User("test1"))

  }

  /**
  * Sync the context lifecycle with Play's.
  * @param app
  */
  override def onStop(app: Application) {

    SpringContextHolder.shutdown()

    super.onStop(app)
  }

  /**
  * Controllers must be resolved through the application context. There is a special method of GlobalSettings
  * that we can override to resolve a given controller. This resolution is required by the Play router.
  * @param controllerClass
  * @param A
  * @return
  */

  override def getControllerInstance[A](controllerClass: Class[A]): A = {

    val instance = SpringContextHolder.getContext.getBean(controllerClass)
    // val instance = SpringContextHolder.getContext.getBean("controller.Tweets")
    instance.asInstanceOf[A]
  }



}
