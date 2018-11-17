import com.google.cloud.dialogflow.v2beta1.TextInput.Builder
import com.google.cloud.dialogflow.v2beta1._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object DetectIntentTexts extends DialogSession {

  def detectIntentTexts(
                         projectId: String,
                         texts: List[String],
                         sessionId: String,
                         languageCode: String,
                         timeout: FiniteDuration = 5.seconds)
  : Seq[Either[Throwable, QueryResult]] = {
    for (text <- texts) yield {
      try {
        val textInput: Builder = TextInput.newBuilder().setText(text).setLanguageCode(languageCode)

        val queryInput: QueryInput = QueryInput.newBuilder().setText(textInput).build()

        val futureResponse: Future[DetectIntentResponse] = Future {
          detectIntent(projectId, sessionId, queryInput)
        }

        val response: DetectIntentResponse = Await.result(futureResponse, timeout)

        Right(response.getQueryResult)
      } catch {
        case e: Throwable => Left(e)
      }
    }
  }

}
