import com.typesafe.config.ConfigFactory
import org.scalatest.{FunSpec, Matchers}

class SessionEntityTypeManagementSpec extends FunSpec with Matchers {

  describe("Session EntityTypes Management Test") {

    it("it should get correct response") {
      val config        = ConfigFactory.load("reference.conf")
      val projectId     = config.getString("dialogflow.project-id")
      val displayName   = "test_display_name"
      val sessionId     = config.getString("dialogflow.detect-intent.session-id")
      val entity_values = List("test_entity_value_1", "test_entity_value_2")

      EntityTypeManagement.createEntityType(projectId, displayName, "KIND_MAP") match {
        case Right(_) =>
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      SessionEntityTypeManagement.createSessionEntityType(projectId,
                                                          sessionId,
                                                          entity_values,
                                                          displayName,
                                                          1) match {
        case Right(_) =>
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      SessionEntityTypeManagement.listSessionEntityTypes(projectId, sessionId) match {
        case Right(sessionEntityTypeList) =>
          for (session <- sessionEntityTypeList) {
            session.getName should include(displayName)
            assert(entity_values.contains(session.getEntities(0).getValue))
          }
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      SessionEntityTypeManagement.deleteSessionEntityType(projectId, sessionId, displayName) match {
        case Right(_) =>
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      SessionEntityTypeManagement.listSessionEntityTypes(projectId, sessionId) match {
        case Right(sessionEntityTypeList) =>
          assert(sessionEntityTypeList.isEmpty)
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      EntityTypeManagement.getEntityTypeIds(projectId, displayName) match {
        case Right(entityTypeIds) =>
          entityTypeIds.map { entityTypeId =>
            EntityTypeManagement.deleteEntityType(projectId, entityTypeId) match {
              case Right(_) =>
              case Left(e) =>
                fail(s"invalid ${e.getMessage}")
            }
          }
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

    }
  }

}
