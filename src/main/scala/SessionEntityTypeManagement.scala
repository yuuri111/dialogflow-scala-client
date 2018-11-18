import com.google.cloud.dialogflow.v2.EntityType.Entity
import com.google.cloud.dialogflow.v2.SessionEntityType.EntityOverrideMode
import com.google.cloud.dialogflow.v2.{
  SessionEntityType,
  SessionEntityTypeName,
  SessionEntityTypesClient,
  SessionName
}

import scala.collection.JavaConverters._

object SessionEntityTypeManagement extends DialogSession {

  def listSessionEntityTypes(projectId: String,
                             sessionId: String): Either[Throwable, Iterable[SessionEntityType]] = {
    try {

      val sessionEntityTypesClient = SessionEntityTypesClient.create()
      val session                  = SessionName.of(projectId, sessionId)

      Right(sessionEntityTypesClient.listSessionEntityTypes(session).iterateAll().asScala)
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def createSessionEntityType(projectId: String,
                              sessionId: String,
                              entityValues: List[String],
                              entityTypeDisplayName: String,
                              entityOverrideMode: Int): Either[Throwable, SessionEntityType] = {
    try {
      val sessionEntityTypesClient = SessionEntityTypesClient.create()
      val session                  = SessionName.of(projectId, sessionId)

      val name = SessionEntityTypeName.of(projectId, sessionId, entityTypeDisplayName)

      val entities = entityValues.map { entityValue =>
        Entity
          .newBuilder()
          .setValue(entityValue)
          .addSynonyms(entityValue)
          .build()
      }.asJava

      val sessionEntityType = SessionEntityType
        .newBuilder()
        .setName(name.toString)
        .addAllEntities(entities)
        .setEntityOverrideMode(EntityOverrideMode.forNumber(entityOverrideMode))
        .build()

      Right(sessionEntityTypesClient.createSessionEntityType(session, sessionEntityType))

    } catch {
      case e: Throwable => Left(e)
    }
  }

  def deleteSessionEntityType(projectId: String,
                              sessionId: String,
                              entityTypeDisplayName: String): Either[Throwable, Unit] = {
    try {
      val sessionEntityTypesClient = SessionEntityTypesClient.create()
      val name                     = SessionEntityTypeName.of(projectId, sessionId, entityTypeDisplayName)

      Right(sessionEntityTypesClient.deleteSessionEntityType(name))
    } catch {
      case e: Throwable => Left(e)
    }
  }

}
