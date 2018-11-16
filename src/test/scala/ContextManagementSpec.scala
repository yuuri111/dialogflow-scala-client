import com.google.cloud.dialogflow.v2beta1.Context
import com.typesafe.config.ConfigFactory
import org.scalatest.{FunSpec, Matchers}

class ContextManagementSpec extends FunSpec with Matchers{

  describe("DetectIntentTexts Test") {

    it("it should get correct response if phrase is correct") {

      val config = ConfigFactory.load("reference.conf")
      val projectId = config.getString("dialogflow.project-id")
      val sessionId = config.getString("dialogflow.detect-intent.session-id")
      val contextId = "test_context"

      ContextManagement.createContext(contextId, sessionId, projectId, 1)
      ContextManagement.listContexts(sessionId, projectId) match {
        case Right(listContexts) =>
          for (context <- listContexts) {
            context.getName should include (contextId)
          }
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      ContextManagement.deleteContext(contextId, sessionId, projectId)
      ContextManagement.listContexts(sessionId, projectId) match {
        case Right(listContexts) =>
          for (context <- listContexts) {
            context.getName shouldNot include (contextId)
          }
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

    }
  }

}