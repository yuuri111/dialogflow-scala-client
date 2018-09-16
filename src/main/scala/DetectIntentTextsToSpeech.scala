import com.google.cloud.dialogflow.v2beta1.TextInput.Builder
import com.google.cloud.dialogflow.v2beta1._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

case class DetectIntentTextsToSpeechException(message: String) extends Exception

object DetectIntentTextsToSpeech extends DialogSession {

  def detectIntentTexts(projectId: String, texts: List[String], sessionId: String, languageCode: String)
  : Seq[Either[Throwable, DetectIntentResponse]] = {
    for (text <- texts) yield {
      try {
        val textInput: Builder = TextInput.newBuilder().setText(text).setLanguageCode(languageCode)

        val queryInput: QueryInput = QueryInput.newBuilder().setText(textInput).build()

        val detectIntentRequest: DetectIntentRequest =
          DetectIntentRequest.newBuilder()
            .setQueryInput(queryInput)
            .setOutputAudioConfig(getOutputAudioConfig())
            .setSession(getSessionName(projectId, sessionId).toString())
            .build()

        val futureResponse: Future[DetectIntentResponse] = Future {
          detectIntent(detectIntentRequest)
        }

        val response: DetectIntentResponse = Await.result(futureResponse, 5 seconds)

        Option(response.getQueryResult) match {
          case Some(result) => Right(response)
          case None => throw new DetectIntentTextsToSpeechException("detect intent speech return null")
        }

      } catch {
        case e: Throwable => Left(e)
      }
    }
  }

  private def getOutputAudioConfig() = {
    val audioEncoding: OutputAudioEncoding = OutputAudioEncoding.OUTPUT_AUDIO_ENCODING_LINEAR_16
    val sampleRateHertz: Int = 16000
    OutputAudioConfig.newBuilder()
      .setAudioEncoding(audioEncoding)
      .setSampleRateHertz(sampleRateHertz)
      .build()

  }

}
