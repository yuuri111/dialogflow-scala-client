import com.google.cloud.dialogflow.v2beta1.QueryResult
import com.typesafe.config.ConfigFactory
import org.scalatest.FunSpec

class DetectIntentTextsSpec extends FunSpec {

  describe("DetectIntentTexts Test") {

    it("it should get correct response if phrase is correct") {
      val config = ConfigFactory.load("reference.conf")
      val resultList: Seq[Either[Throwable, QueryResult]] = DetectIntentTexts.detectIntentTexts(
        config.getString("dialogflow.project-id"),
        List("Thank you", "1500"),
        config.getString("dialogflow.detect-intent.session-id"),
        config.getString("dialogflow.detect-intent.language-code")
      )

      for (resultEither: Either[Throwable, QueryResult] <- resultList) {
        resultEither match {
          case Right(result) =>
            assert(result.getFulfillmentText == "Correct Phrase!")
          case Left(e) =>
            fail(s"Include invalid phrase: ${e.getMessage}")
        }
      }
    }

    it("it should not get correct response if phrase is not correct") {
      val config = ConfigFactory.load("reference.conf")
      val resultList: Seq[Either[Throwable, QueryResult]] = DetectIntentTexts.detectIntentTexts(
        config.getString("dialogflow.project-id"),
        List("Invalid phrase", "1000"),
        config.getString("dialogflow.detect-intent.session-id"),
        config.getString("dialogflow.detect-intent.language-code")
      )

      for (resultEither: Either[Throwable, QueryResult] <- resultList) {
        resultEither match {
          case Right(result) =>
            assert(result.getFulfillmentText !== "Correct Phrase!")
          case Left(e) =>
            fail(s"unexpected exception: ${e.getMessage}")
        }
      }

    }
  }

}
