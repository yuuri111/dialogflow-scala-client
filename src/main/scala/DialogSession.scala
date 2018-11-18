import com.google.cloud.dialogflow.v2beta1._

trait DialogSession {

  lazy val sessionsClient = SessionsClient.create()

  def getSessionName(projectId: String, sessionId: String) = {

    SessionName.of(projectId, sessionId)

  }

  def detectIntent(projectId: String, sessionId: String, queryInput: QueryInput) = {

    val session: SessionName = SessionName.of(projectId, sessionId)

    sessionsClient.detectIntent(session, queryInput)
  }

  def detectIntent(detectIntentRequest: DetectIntentRequest) = {

    sessionsClient.detectIntent(detectIntentRequest)
  }

}
