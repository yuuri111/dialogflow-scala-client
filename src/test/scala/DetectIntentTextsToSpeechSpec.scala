import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse
import com.typesafe.config.ConfigFactory
import org.scalatest.FunSpec

class DetectIntentTextsToSpeechSpec extends FunSpec {

  describe("DetectIntentTextsToSpeech Test") {

    it("it should get correct response if phrase is correct") {
      val config = ConfigFactory.load("reference.conf")
      val responseList: Seq[Either[Throwable, DetectIntentResponse]] =
        DetectIntentTextsToSpeech.detectIntentTexttoSpeech(
          config.getString("dialogflow.project-id"),
          List("Thank you", "1500"),
          config.getString("dialogflow.detect-intent.session-id"),
          config.getString("dialogflow.detect-intent.language-code")
        )

      for (responseEither: Either[Throwable, DetectIntentResponse] <- responseList) {
        responseEither match {
          case Right(response) =>
            assert(!response.getOutputAudio.isEmpty)
            assert(response.getQueryResult.getFulfillmentText == "Correct Phrase!")
          case Left(e) =>
            fail(s"Include invalid phrase: ${e.getMessage}")
        }
      }
    }

    it("it should not get correct response if phrase is not correct") {
      val config = ConfigFactory.load("reference.conf")
      val responseList: Seq[Either[Throwable, DetectIntentResponse]] =
        DetectIntentTextsToSpeech.detectIntentTexttoSpeech(
          config.getString("dialogflow.project-id"),
          List("Invalid phrase", "1000"),
          config.getString("dialogflow.detect-intent.session-id"),
          config.getString("dialogflow.detect-intent.language-code")
        )

      for (responseEither: Either[Throwable, DetectIntentResponse] <- responseList) {
        responseEither match {
          case Right(response) =>
            assert(!response.getOutputAudio.isEmpty)
            assert(response.getQueryResult.getFulfillmentText != "Correct Phrase!")
          case Left(e) =>
            fail(s"unexpected exception: ${e.getMessage}")
        }
      }

    }
  }

}
