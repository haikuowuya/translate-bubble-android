/*
 * Copyright (C) 2015 47 Degrees, LLC http://47deg.com hello@47deg.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortysevendeg.translatebubble.modules.translate

import com.fortysevendeg.macroid.extras.AppContextProvider
import com.fortysevendeg.translatebubble.modules.TestConfig
import com.fortysevendeg.translatebubble.modules.repository._
import com.fortysevendeg.translatebubble.modules.repository.impl.RepositoryServicesComponentImpl
import com.fortysevendeg.translatebubble.modules.translate.impl.TranslateServicesComponentImpl
import com.fortysevendeg.translatebubble.provider.TranslationHistoryEntityData
import com.fortysevendeg.translatebubble.utils.LanguageType
import macroid.AppContext
import org.specs2.mock.Mockito
import org.specs2.specification.Scope

import scala.concurrent.Future

trait TranslateMocks
    extends BaseTranslateMocks {

  val translateServiceUrl = "http://fakeUrl"
  val text = "Hello world!"
  val translatedText = "Hola mundo!"
  val from = LanguageType.ENGLISH
  val to = LanguageType.SPANISH

  val addTranslationHistoryRequest = AddTranslationHistoryRequest(TranslationHistoryEntityData(
    originalText = text,
    translatedText = translatedText,
    from = from,
    to = to))
  val fetchTranslationHistoryRequest = FetchTranslationHistoryRequest(originalText = text, from = from, to = to)
  val mockAddTranslationHistoryResponse = mock[AddTranslationHistoryResponse]
  val mockFetchTranslationHistoryResponse = mock[FetchTranslationHistoryResponse]

  repositoryServices.addTranslationHistory returns (addTranslationHistoryRequest => Future.successful(mockAddTranslationHistoryResponse))
  repositoryServices.fetchTranslationHistory returns (fetchTranslationHistoryRequest => Future.successful(mockFetchTranslationHistoryResponse))
  mockFetchTranslationHistoryResponse.result returns None
}

trait BaseTranslateMocks
    extends AppContextProvider
    with TranslateServicesComponentImpl
    with RepositoryServicesComponentImpl
    with TestConfig
    with Scope
    with Mockito {
  implicit val appContextProvider: AppContext = mock[AppContext]
  appContextProvider.get returns mockContext

  override lazy val repositoryServices: RepositoryServicesImpl = mock[RepositoryServicesImpl]
}