package controllers

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Functional tests start a Play application internally, available
  * as `app`.
  */
class FunctionalSpec extends PlaySpec with GuiceOneAppPerSuite {

  def req(url: String) = route(app, FakeRequest(GET, url).withHeaders(HOST -> "localhost:9000"))

  "Routes" should {

    "send 400 on a bad request" in  {
      req("/whatever").map(status(_)) mustBe Some(NOT_FOUND)
    }

    "send 200 on a good request" in  {
      req("/").map(status(_)) mustBe Some(OK)
      req("/github/octocat").map(status(_)) mustBe Some(OK)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = req("/").get

      status(home) mustBe Status.OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("<input name=\"repo\" id=\"input\">")
    }

    "get JSON from GitHub API" in {
      val github = req("/github/octocat").get

      status(github) mustBe Status.OK
      contentType(github) mustBe Some("application/json")
    }

  }

}
