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


  def github(user: String) = Action.async {

    lazy val url = s"https://api.github.com/users/$user/repos"

    ws.url(url).get().map { response =>

//      val contributors = (response.json \\ "contributors_url").map(_.as[String])
//      val languages = (response.json \\ "languages_url").map(_.as[String])

      val resJson = response.json

      Ok(resJson)
//
//      val urls = contributors ++ languages
//      val innerJson = urls.map(ws.url)
//      val futureResponses = Future.sequence(innerJson.map(_.get()))
//
//      val details = futureResponses.map { responses =>
//        val k = responses.map { rs =>
//          rs.body
//        }
//        println(s"k: ${k}")
//      }


//        details onSuccess {
//          case des => println("success")
//        }
//
//        details onFailure {
//          case fail => Ok(s"fail to get data: ${fail}")
//        }



//      Ok(Json.prettyPrint(resJson/*.as[JsObject] + ("a" -> Json.toJson(details))*/))
    }
  }


}
