import com.google.cloud.dialogflow.v2._

trait DialogSession {

  def createSession() = {
    SessionsClient.create()
  }

  def detectIntent(projectId: String, sessionId: String, queryInput: QueryInput) =  {

    val session: SessionName = SessionName.of(projectId, sessionId)

    createSession().detectIntent(session, queryInput)
  }

}
