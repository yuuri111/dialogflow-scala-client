import com.google.cloud.dialogflow.v2.TextInput.Builder
import com.google.cloud.dialogflow.v2._

object DetectIntentTexts extends App with DialogSession {

  def detectIntentTexts(projectId: String, texts: List[String], sessionId: String, languageCode: String)
  : Seq[Either[Throwable, QueryResult]] = {
    for (text <- texts) yield {
      try {
        val textInput: Builder = TextInput.newBuilder().setText(text).setLanguageCode(languageCode)

        val queryInput: QueryInput = QueryInput.newBuilder().setText(textInput).build()

        val response: DetectIntentResponse = detectIntent(projectId, sessionId, queryInput)

        Option(response.getQueryResult()) match {
          case Some(result) => Right(result)
          case None => throw new Exception("return null")
        }
      } catch {
        case e: Throwable => Left(e)
      }
    }
  }

}
