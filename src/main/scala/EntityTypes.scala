import com.google.cloud.dialogflow.v2._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.collection.JavaConverters._

object EntityTypes extends App {

  def getEntity(projectId: String, timeout: FiniteDuration = 5.seconds) = {
    try {
      val entityTypesClient: EntityTypesClient = EntityTypesClient.create()
      val project: ProjectAgentName = ProjectAgentName.of(projectId)

      val futureResponse: Future[EntityTypesClient.ListEntityTypesPagedResponse] = Future {
        entityTypesClient.listEntityTypes(project)
      }

      val response: EntityTypesClient.ListEntityTypesPagedResponse
      = Await.result(futureResponse, timeout)

      Right(response.iterateAll().asScala.to[Seq])
    } catch {
      case e: Throwable => Left(e)
    }
  }

}
