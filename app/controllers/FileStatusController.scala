package controllers

import controllers.actions.AuthActionFilter
import play.api.libs.json.JsValue
import play.api.mvc.{Action, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton()
class FileStatusController @Inject() (cc: ControllerComponents, authFilter: AuthActionFilter) extends BackendController(cc) with StubResource {

  def allFiles(subscriptionId: String): Action[JsValue] = (Action(parse.json) andThen authFilter).async { implicit request =>

    subscriptionId match {
      case "XAMDR0009234568" => jsonAsyncResourceResponse(s"/resources/status/AllFiles.json")
      case _ => Future.successful(NotFound)
    }
  }
}
