import com.google.api.gax.longrunning.{OperationFuture, OperationSnapshot}
import com.google.cloud.dialogflow.v2beta1.Document.KnowledgeType
import com.google.cloud.dialogflow.v2beta1._

import scala.collection.JavaConverters._

object DocumentManagement extends DialogSession {

  def listDocuments(projectId: String, knowledgeBaseId: String): Either[Throwable, Iterable[Document]] = {
    try {
      val documentsClient = DocumentsClient.create()
      val knowledgeBaseName = KnowledgeBaseName.of(projectId, knowledgeBaseId)
      Right(documentsClient.listDocuments(knowledgeBaseName).iterateAll().asScala)
    } catch {
      case e: Throwable => {
        Left(e)
      }
    }
  }

  def createDocument(
                      projectId: String,
                      knowledgeBaseId: String,
                      displayName: String,
                      mimeType: String,
                      knowledgeType: String,
                      contentUri: String): Either[Throwable, OperationFuture[Document, KnowledgeOperationMetadata]] = {
    try {
      val documentsClient = DocumentsClient.create()
      val document =
        Document.newBuilder()
          .setDisplayName(displayName)
          .setContentUri(contentUri)
          .setMimeType(mimeType)
          .addKnowledgeTypes(KnowledgeType.valueOf(knowledgeType))
          .build()
      val parent = KnowledgeBaseName.of(projectId, knowledgeBaseId)
      val createDocumentRequest =
        CreateDocumentRequest.newBuilder()
          .setDocument(document)
          .setParent(parent.toString)
          .build()

      Right(documentsClient.createDocumentAsync(createDocumentRequest))
    } catch {
      case e: Throwable => {
        Left(e)
      }
    }
  }

  def getDocument(projectId: String, knowledgeBaseId: String, documentId: String): Either[Throwable, Document] = {
    try {
      val documentsClient = DocumentsClient.create()
      val documentName = DocumentName.of(projectId, knowledgeBaseId, documentId)
      Right(documentsClient.getDocument(documentName))
    } catch {
      case e: Throwable => {
        Left(e)
      }
    }
  }

  def deleteDocument(projectId: String, knowledgeBaseId: String, documentId: String): Either[Throwable, OperationSnapshot] = {
    try {
      val documentsClient = DocumentsClient.create()
      val documentName = DocumentName.of(projectId, knowledgeBaseId, documentId)
      Right(documentsClient.deleteDocumentAsync(documentName).getInitialFuture.get())
    } catch {
      case e: Throwable => {
        Left(e)
      }
    }
  }

}
