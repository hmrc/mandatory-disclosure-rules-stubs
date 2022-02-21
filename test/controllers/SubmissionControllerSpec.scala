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

import org.scalatest.OptionValues
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status._
import play.api.test.FakeRequest
import play.api.test.Helpers.{defaultAwaitTimeout, route, status, writeableOf_AnyContentAsXml, POST}
import uk.gov.hmrc.http.HeaderNames

class SubmissionControllerSpec extends AnyFreeSpec with Matchers with GuiceOneAppPerSuite with OptionValues {

  private val authHeader: (String, String) = HeaderNames.authorisation -> "token"

  private val nameAndErrorStatus: Seq[(String, Int)] = Seq(
    ("error", INTERNAL_SERVER_ERROR),
    ("invalid", BAD_REQUEST),
    ("server", SERVICE_UNAVAILABLE),
    ("notProcessed", SERVICE_UNAVAILABLE),
    ("notFound", NOT_FOUND)
  )

  "RegisterWithoutIdController" - {

    "must return FORBIDDEN response when 'Authorization' header is missing in the input request" in {
      val xmlPayload = <xml><submission></submission></xml>

      val request = FakeRequest(POST, routes.SubmissionController.submit().url).withXmlBody(xmlPayload)
      val result  = route(app, request).value

      status(result) shouldBe FORBIDDEN
    }

    "must return Ok response for xml submission" in {
      val xmlPayload = <xml><submission></submission></xml>

      val request = FakeRequest(POST, routes.SubmissionController.submit().url).withXmlBody(xmlPayload).withHeaders(authHeader)
      val result  = route(app, request).value

      status(result) shouldBe OK
    }

    "must return BadRequest for an invalid input" in {

      val xmlPayload = <eis:submission> <eis:subscriptionID>XE4001234567890</eis:subscriptionID></eis:submission>

      val request = FakeRequest(POST, routes.SubmissionController.submit().url).withXmlBody(xmlPayload).withHeaders(authHeader)
      val result  = route(app, request).value

      status(result) shouldBe BAD_REQUEST
    }
    "must return InternalServerError for an invalid input" in {

      val xmlPayload = <eis:submission> <eis:subscriptionID>XE5001234567890</eis:subscriptionID></eis:submission>

      val request = FakeRequest(POST, routes.SubmissionController.submit().url).withXmlBody(xmlPayload).withHeaders(authHeader)
      val result  = route(app, request).value

      status(result) shouldBe INTERNAL_SERVER_ERROR
    }
    "must return ServiceUnavailable for an invalid input" in {

      val xmlPayload = <eis:submission> <eis:subscriptionID>XE5031234567890</eis:subscriptionID></eis:submission>

      val request = FakeRequest(POST, routes.SubmissionController.submit().url).withXmlBody(xmlPayload).withHeaders(authHeader)
      val result  = route(app, request).value

      status(result) shouldBe SERVICE_UNAVAILABLE
    }
  }
}
