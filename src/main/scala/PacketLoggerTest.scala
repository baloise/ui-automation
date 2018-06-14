/*
 * Copyright 2018 inpwtepydjuf@gmail.com
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

package mmarquee.automation.Scala

object PacketLoggerTest {

  var logger: PacketLoggerApplication = _

  def start(): Unit = {
    logger = new PacketLoggerApplication()

    logger.launch()

    // Wait for it ...
    Thread.sleep(10000)

    // Now kill it
    logger.exit

    logger.dump()
  }

  def main(args: Array[String]) {
    start()
  }
}