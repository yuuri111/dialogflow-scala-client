import com.google.cloud.dialogflow.v2.EntityType.Kind
import com.google.cloud.dialogflow.v2._

import scala.collection.JavaConverters._

object EntityTypeManagement extends App {

  def listEntityTypes(projectId: String): Either[Throwable, Iterable[EntityType]] = {
    try {
      val entityTypesClient: EntityTypesClient = EntityTypesClient.create()
      val project: ProjectAgentName            = ProjectAgentName.of(projectId)

      Right(entityTypesClient.listEntityTypes(project).iterateAll().asScala)
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def createEntityType(projectId: String,
                       displayName: String,
                       kind: String): Either[Throwable, EntityType] = {
    try {
      val entityTypesClient: EntityTypesClient = EntityTypesClient.create()
      val parent                               = ProjectAgentName.of(projectId)

      val entityType = EntityType
        .newBuilder()
        .setDisplayName(displayName)
        .setKind(Kind.valueOf(kind))
        .build()

      Right(entityTypesClient.createEntityType(parent, entityType))
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def deleteEntityType(projectId: String, entityTypeId: String): Either[Throwable, Unit] = {
    try {
      val entityTypesClient: EntityTypesClient = EntityTypesClient.create()
      val name                                 = EntityTypeName.of(projectId, entityTypeId)

      Right(entityTypesClient.deleteEntityType(name))
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def getEntityTypeIds(projectId: String,
                       displayName: String): Either[Throwable, Iterable[String]] = {
    try {
      val entityTypesClient: EntityTypesClient = EntityTypesClient.create()
      val parent                               = ProjectAgentName.of(projectId)

      val entityTypeList: Iterable[EntityType] =
        entityTypesClient.listEntityTypes(parent).iterateAll().asScala

      val entityTypeIds = entityTypeList
        .withFilter(getSplitName(_).length > 0)
        .map(entityType => getSplitName(entityType)(getSplitName(entityType).length - 1))

      Right(entityTypeIds)
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def getSplitName(entytyType: EntityType): Array[String] = {
    entytyType.getName.split("/")
  }

}
