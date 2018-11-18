import com.typesafe.config.ConfigFactory

object Main extends App {

  val config = ConfigFactory.load()

  /*
  val result = DetectIntentTexts.detectIntentTexts(
    config.getString("dialogflow.project-id"),
    List(config.getString("dialogflow.detect-intent.text")),
    config.getString("dialogflow.detect-intent.session-id"),
    config.getString("dialogflow.detect-intent.language-code")
  )

  val result = EntityTypes.getEntity(
    config.getString("dialogflow.project-id")
  )

  println(result)
  */
}
