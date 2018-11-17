import java.nio.file.{Files, Paths}

import com.google.cloud.dialogflow.v2beta1._
import com.google.protobuf.ByteString

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object DetectIntentAudio extends DialogSession {

  def detectIntentAudio(
                         projectId: String,
                         audioFilePath: String,
                         sessionId: String,
                         languageCode: String,
                         timeout: FiniteDuration = 5.seconds)
  : Either[Throwable, QueryResult] = {
    try {
      val queryInput: QueryInput =
        QueryInput.newBuilder().setAudioConfig(getInputAudioConfig(languageCode)).build()

      val inputAudio: Array[Byte] = Files.readAllBytes(Paths.get(audioFilePath))

      val request: DetectIntentRequest =
        DetectIntentRequest.newBuilder()
          .setSession(getSessionName(projectId, sessionId).toString)
          .setQueryInput(queryInput)
          .setInputAudio(ByteString.copyFrom(inputAudio))
          .build()

      val futureResponse: Future[DetectIntentResponse] = Future {
        detectIntent(request)
      }

      val response: DetectIntentResponse = Await.result(futureResponse, timeout)

      Right(response.getQueryResult)
    } catch {
      case e: Throwable => Left(e)
    }
  }

  private def getInputAudioConfig(languageCode: String) = {
    val audioEncoding: AudioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16
    val sampleRateHertz: Int = 16000
    InputAudioConfig.newBuilder()
      .setAudioEncoding(audioEncoding)
      .setLanguageCode(languageCode)
      .setSampleRateHertz(sampleRateHertz)
      .build()
  }

}
