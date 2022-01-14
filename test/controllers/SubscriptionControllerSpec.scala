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
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.HeaderNames

class SubscriptionControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite with OptionValues {

  private val authHeader: (String, String) = HeaderNames.authorisation -> "token"

  private val jsonBody = (id:String)  =>  Json.parse(s"""{
                   | "updateSubscriptionForMDRRequest": {
                   |  "requestCommon": {
                   |   "regime": "MDR",
                   |   "receiptDate": "2020-09-09T11:23:10Z",
                   |   "acknowledgementReference": "8493893huer3ruihuow",
                   |   "originatingSystem": "MDTP"
                   |  },
                   |  "requestDetail": {
                   |   "IDType": "subscriptionID",
                   |   "IDNumber": "$id",
                   |   "tradingName": "Test Tools",
                   |   "isGBUser": true,
                   |   "primaryContact": [
                   |    {
                   |     "email": "testprimary@test.com",
                   |     "phone": "08778763213789",
                   |     "mobile": "08778763213789",
                   |     "individual": {
                   |      "lastName": "TestTaylor",
                   |      "firstName": "TestTimothy",
                   |      "middleName": "TestTrent"
                   |} }
                   |   ],
                   |   "secondaryContact": [
                   |    {
                   |     "email": "testsecondary@test.com",
                   |     "organisation": {
                   |      "organisationName": "Tools for Trade"
                   |     }
                   |} ]
                   |} }
                   |}""".stripMargin)
  private val fakeRequestWithJsonBody = FakeRequest("POST", routes.SubscriptionController.updateSubscription().url)

  "POST to updateSubscription" should {
    "return 403" in {
      val result  = route(app, fakeRequestWithJsonBody.withBody(jsonBody("XAMDR0000123777"))).value
      status(result) shouldBe Status.FORBIDDEN
    }

    "return 200" in {
      val result  = route(app, fakeRequestWithJsonBody.withBody(jsonBody("XAMDR0000123777")).withHeaders(authHeader)).value
      status(result) shouldBe Status.OK
    }

    "return 404" in {

      val result  = route(app, fakeRequestWithJsonBody.withBody(jsonBody("XAMDR000NOTFOUND")).withHeaders(authHeader)).value
      status(result) shouldBe Status.NOT_FOUND
    }
  }

}
