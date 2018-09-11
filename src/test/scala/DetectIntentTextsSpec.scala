import com.google.cloud.dialogflow.v2.QueryResult
import com.typesafe.config.ConfigFactory
import org.scalatest.FunSpec

class DetectIntentTextsSpec extends FunSpec {

  describe("DetectIntentTextsA Test") {

    it("should get response") {
      val config = ConfigFactory.load("reference.conf")
      val result: Seq[Either[Throwable, QueryResult]] = DetectIntentTexts.detectIntentTexts(
        config.getString("dialogflow.project-id"),
        List("Thank you"),
        config.getString("dialogflow.detect-intent.session-id"),
        config.getString("dialogflow.detect-intent.language-code")
      )

      assert(result.length == 1)
    }
  }

}