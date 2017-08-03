package controllers

import javax.inject.Inject

import akka.actor.Status.Success
import play.api.Logger
import play.api.libs.ws
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.libs.ws._
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Success, Failure, Try}

/**
  * A very small controller that renders a home page.
  */




class HomeController @Inject()(cc: ControllerComponents, ws: WSClient) (implicit ec: ExecutionContext)  extends AbstractController(cc) {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  private lazy val url = "https://api.github.com/users/octocat/repos"

  def githubb = Action.async {
    ws.url("https://api.ipify.org/?format=json").get().map { response =>

      val a = (response.json \ "ip").as[String]
      println(s"a is $a")
      Ok(a)
    }
  }

  def githuba = Action {
    lazy val j = Json.parse("""[{"id": 123},{"id": 345}]""")
    lazy val k = (j \\ "id").map(_.as[Int])
    k.map(x => println(s"k is: ${x+1}"))
    Ok(Json.toJson(k))
  }

  def github = Action.async {

    ws.url(url).get().map { response =>

      val contributors = (response.json \\ "contributors_url").map(_.as[String])
      val languages = (response.json \\ "languages_url").map(_.as[String])

      val resJson = response.json

      val urls = contributors ++ languages
      val innerJson = urls.map(ws.url)
      val futureResponses = Future.sequence(innerJson.map(_.get()))

      val details = futureResponses.map { responses =>
        responses.map { rs =>
          rs.body
        }
      }

      details onComplete {
        case Success(des) => des
      }

      Ok(Json.prettyPrint(resJson.as[JsObject] + ("a" -> Json.toJson(details))))
    }
  }


}
