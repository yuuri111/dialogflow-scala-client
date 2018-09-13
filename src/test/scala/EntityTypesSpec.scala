import com.google.cloud.dialogflow.v2.EntityType
import com.typesafe.config.ConfigFactory
import org.scalatest.FunSpec
import scala.collection.JavaConverters._

class EntityTypesSpec extends FunSpec {

  describe("EntityTypes Test") {

    it("it should get correct response") {
      val config = ConfigFactory.load("reference.conf")
      val resultEither: Either[Throwable, Seq[EntityType]] = EntityTypes.getEntity(
        config.getString("dialogflow.project-id")
      )

      resultEither match {
        case Right(resultList) =>
          for (result <- resultList) {
            assert(result.getEntities(0).getValue == "good")
            assert(result.getEntities(0).getSynonymsList.asScala == List("good", "great", "excellent"))
            assert(result.getDisplayName == "judge")
          }
        case Left(e) =>
          fail(s"could not get correct response ${e.getMessage}")
      }
    }

  }

}