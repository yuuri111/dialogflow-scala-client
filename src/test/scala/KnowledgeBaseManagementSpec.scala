import com.typesafe.config.ConfigFactory
import org.scalatest.{FunSpec, Matchers}

class KnowledgeBaseManagementSpec extends FunSpec with Matchers {

  describe("DetectIntentTexts Test") {

    it("it should get correct response") {

      val config            = ConfigFactory.load("reference.conf")
      val projectId         = config.getString("dialogflow.project-id")
      val knowledgebaseName = "test_knowledgebase"
      val documentbaseName  = "test_documentbase"

      KnowledgebaseManagement.listKnowledgeBases(projectId) match {
        case Right(knowledgeBaseList) =>
          for (knowledgeBase <- knowledgeBaseList) {
            knowledgeBase.getDisplayName shouldNot include(knowledgebaseName)
          }
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      KnowledgebaseManagement.createKnowledgeBase(projectId, knowledgebaseName) match {
        case Right(knowledgeBase) =>
          knowledgeBase.getDisplayName should include(knowledgebaseName)
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      val knowledgeBaseId = KnowledgebaseManagement.listKnowledgeBases(projectId) match {
        case Right(listKnowledgeBase) =>
          listKnowledgeBase.head.getName.split("knowledgeBases/")(1)
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      KnowledgebaseManagement.getKnowledgeBase(projectId, knowledgeBaseId) match {
        case Right(knowledgeBase) =>
          knowledgeBase.getDisplayName should include(knowledgebaseName)
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      val test =
        DocumentManagement.createDocument(projectId,
                                          knowledgeBaseId,
                                          documentbaseName,
                                          "text/html",
                                          "FAQ",
                                          "https://cloud.google.com/storage/docs/faq") match {
          case Right(future) =>
            future.get().getDisplayName should include(documentbaseName)
          case Left(e) =>
            fail(s"invalid ${e.getMessage}")
        }

      val documentId = DocumentManagement.listDocuments(projectId, knowledgeBaseId) match {
        case Right(documentList) =>
          documentList.head.getName.split("documents/")(1).split("- MIME Type").head
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      DocumentManagement.getDocument(projectId, knowledgeBaseId, documentId) match {
        case Right(document) =>
          document.getDisplayName should include(documentbaseName)
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      DocumentManagement.deleteDocument(projectId, knowledgeBaseId, documentId) match {
        case Right(_) =>
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }

      KnowledgebaseManagement.deleteKnowledgeBase(projectId, knowledgeBaseId) match {
        case Right(_) =>
        case Left(e) =>
          fail(s"invalid ${e.getMessage}")
      }
    }
  }

}
