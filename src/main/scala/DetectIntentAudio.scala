import java.nio.file.{Files, Paths}

import com.google.cloud.dialogflow.v2._
import com.google.protobuf.ByteString

object DetectIntentAudio extends App with DialogSession {

  def detectIntentAudio(projectId: String, audioFilePath: String, sessionId: String, languageCode: String) = {

    val sessionsClient: SessionsClient = createSession()

    val session: SessionName = SessionName.of(projectId, sessionId)

    val audioEncoding: AudioEncoding = AudioEncoding.AUDIO_ENCODING_LINEAR_16
    val sampleRateHertz = 16000

    val inputAudioConfig: InputAudioConfig = InputAudioConfig.newBuilder()
      .setAudioEncoding(audioEncoding)
      .setLanguageCode(languageCode)
      .setSampleRateHertz(sampleRateHertz)
      .build()

    val queryInput: QueryInput = QueryInput.newBuilder().setAudioConfig(inputAudioConfig).build()

    val inputAudio: Array[Byte] = Files.readAllBytes(Paths.get(audioFilePath))

    val request: DetectIntentRequest = DetectIntentRequest.newBuilder()
      .setSession(session.toString())
      .setQueryInput(queryInput)
      .setInputAudio(ByteString.copyFrom(inputAudio))
      .build()

    val response: DetectIntentResponse = sessionsClient.detectIntent(request)

    response.getQueryResult()
  }

}
