import com.google.cloud.dialogflow.v2beta1.TextInput.Builder
import com.google.cloud.dialogflow.v2beta1._

object DetectIntentTexts extends DialogSession {

  def detectIntentTexts(
                         projectId: String,
                         texts: List[String],
                         sessionId: String,
                         languageCode: String)
  : Seq[Either[Throwable, QueryResult]] = {
    for (text <- texts) yield {
      try {
        val textInput: Builder = TextInput.newBuilder().setText(text).setLanguageCode(languageCode)

        val queryInput: QueryInput = QueryInput.newBuilder().setText(textInput).build()

        Right(detectIntent(projectId, sessionId, queryInput).getQueryResult)
      } catch {
        case e: Throwable => Left(e)
      }
    }
  }

}
