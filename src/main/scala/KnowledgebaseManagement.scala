import com.google.cloud.dialogflow.v2beta1._

import scala.collection.JavaConverters._

object KnowledgebaseManagement extends DialogSession {

  def listKnowledgeBases(projectId: String): Either[Throwable, Iterable[KnowledgeBase]] = {
    try {
      val knowledgeBasesClient = KnowledgeBasesClient.create()

      val projectName = ProjectName.of(projectId)
      Right(knowledgeBasesClient.listKnowledgeBases(projectName).iterateAll().asScala)
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def createKnowledgeBase(projectId: String,
                          displayName: String): Either[Throwable, KnowledgeBase] = {
    try {
      val knowledgeBasesClient = KnowledgeBasesClient.create()

      val knowledgeBase = KnowledgeBase.newBuilder().setDisplayName(displayName).build()
      val projectName   = ProjectName.of(projectId)
      Right(knowledgeBasesClient.createKnowledgeBase(projectName, knowledgeBase))
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def getKnowledgeBase(projectId: String,
                       knowledgeBaseId: String): Either[Throwable, KnowledgeBase] = {

    try {
      val knowledgeBasesClient = KnowledgeBasesClient.create()
      val knowledgeBaseName    = KnowledgeBaseName.of(projectId, knowledgeBaseId)
      Right(knowledgeBasesClient.getKnowledgeBase(knowledgeBaseName))
    } catch {
      case e: Throwable => Left(e)
    }
  }

  def deleteKnowledgeBase(projectId: String, knowledgeBaseId: String): Either[Throwable, Unit] = {
    try {
      val knowledgeBasesClient = KnowledgeBasesClient.create()
      val knowledgeBaseName    = KnowledgeBaseName.of(projectId, knowledgeBaseId)
      Right(knowledgeBasesClient.deleteKnowledgeBase(knowledgeBaseName))
    } catch {
      case e: Throwable => Left(e)
    }
  }
}
