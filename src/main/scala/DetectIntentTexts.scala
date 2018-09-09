import com.google.cloud.dialogflow.v2.TextInput.Builder
import com.google.cloud.dialogflow.v2._

object DetectIntentTexts extends App with DialogSession {

  def detectIntentTexts(projectId: String, texts: List[String], sessionId: String, languageCode: String) = {
    for (text <- texts) yield {
      try {
        val textInput: Builder = TextInput.newBuilder().setText(text).setLanguageCode(languageCode)

        val queryInput: QueryInput = QueryInput.newBuilder().setText(textInput).build()

        val response: DetectIntentResponse = detectIntent(projectId, sessionId, queryInput)

        Right(response.getQueryResult())
      } catch {
        case e: Throwable => Left(e)
      }
    }
  }

}
