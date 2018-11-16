import com.google.cloud.dialogflow.v2beta1._
import collection.JavaConverters._

object ContextManagement extends DialogSession {


  def listContexts(sessionId: String, projectId: String): Either[Throwable, Iterable[Context]] = {
    try {
      val contextsClient: ContextsClient = ContextsClient.create()

      Right(contextsClient.listContexts(getSessionName(projectId, sessionId)).iterateAll().asScala)
    } catch {
      case e: Throwable => {
        Left(e)
      }
    }
  }

  def createContext(contextId: String, sessionId: String, projectId: String,
                    lifespanCount: Int): Either[Throwable, Context] = {
    try {
      val contextsClient: ContextsClient = ContextsClient.create()

      val contextName = ContextName.newBuilder()
        .setProject(projectId)
        .setSession(sessionId)
        .setContext(contextId)
        .build()

      val context = Context.newBuilder()
        .setName(contextName.toString())
        .setLifespanCount(lifespanCount)
        .build()

      Right(contextsClient.createContext(getSessionName(projectId, sessionId), context))

    } catch {
      case e: Throwable => {
        Left(e)
      }
    }
  }

  def deleteContext(contextId: String, sessionId: String, projectId: String): Either[Throwable, Unit] = {
    try {
      val contextsClient: ContextsClient = ContextsClient.create()
      val contextName = ContextName.of(projectId, sessionId, contextId)
      Right(contextsClient.deleteContext(contextName))
    } catch {
      case e: Throwable => {
        Left(e)
      }
    }
  }

}
