/*
 * Copyright 2022 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import controllers.actions.AuthActionFilter
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton()
class FileStatusController @Inject() (cc: ControllerComponents, authFilter: AuthActionFilter) extends BackendController(cc) with StubResource {

  def allFiles(mdrid: String): Action[AnyContent] = (Action andThen authFilter).async { implicit request =>
    mdrid match {
      case "XAMDR0009234568" => jsonAsyncResourceResponse(s"/resources/status/AllFiles.json")
      case _                 => Future.successful(NotFound)
    }
  }

  def file(conversationId: String): Action[AnyContent] = (Action andThen authFilter).async { implicit request =>
    conversationId match {
      case "conversationId3" => jsonAsyncResourceResponse(s"/resources/status/Accepted.json")
      case "conversationId2" => jsonAsyncResourceResponse(s"/resources/status/Rejected.json")
      case _                 => Future.successful(NotFound)
    }
  }
}
