
import models.User
import org.springframework.context.support.ClassPathXmlApplicationContext

import org.vaadin.playintegration.VaadinSupport
import play.api._
import repositories.UsersRepo
import spring.SpringContextHolder

object Global extends GlobalSettings with VaadinSupport {

  /**
  * Declare the application context to be used.
  */
  val ctx = new ClassPathXmlApplicationContext("applicationContext.xml")

  /**
  * Sync the context lifecycle with Play's.
  * @param app
  */
  override def onStart(app: Application) {

    super.onStart(app)

    SpringContextHolder.getContext.start()


    // val dao: UsersDao = ctx.getBean(classOf[UsersDao])

    // dao.save(new User("test1"))

  }

  /**
  * Sync the context lifecycle with Play's.
  * @param app
  */
  override def onStop(app: Application) {

    SpringContextHolder.getContext.stop()

    super.onStop(app)
  }

  /**
  * Controllers must be resolved through the application context. There is a special method of GlobalSettings
  * that we can override to resolve a given controller. This resolution is required by the Play router.
  * @param controllerClass
  * @param A
  * @return
  */
  override def getControllerInstance[A](controllerClass: Class[A]): A = ctx.getBean(controllerClass)

}
