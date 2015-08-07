/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.reef.driver.restart;

import org.apache.reef.annotations.Unstable;
import org.apache.reef.annotations.audience.DriverSide;
import org.apache.reef.annotations.audience.Private;

/**
 * The manager that handles aspects of driver restart such as determining whether the driver is in
 * restart mode, what to do on restart, whether restart is completed, and others.
 */
@DriverSide
@Private
@Unstable
public interface DriverRestartManager {

  /**
   * @return Whether or not the driver instance is a restarted instance.
   */
  boolean isRestart();

  /**
   * Recovers the list of alive and failed evaluators and inform about evaluator failures
   * based on the specific runtime. Also sets the expected amount of evaluators to report back
   * as alive to the job driver.
   */
  void onRestart();

  /**
   * Indicate that the Driver restart is complete. It is meant to be called exactly once during a restart and never
   * during the ininital launch of a Driver.
   */
  void setRestartCompleted();

  /**
   * @return the number of Evaluators expected to check in from a previous run.
   */
  int getNumPreviousContainers();


  /**
   * Set the number of containers to expect still active from a previous execution of the Driver in a restart situation.
   * To be called exactly once during a driver restart.
   *
   * @param num
   */
  void setNumPreviousContainers(final int num);

  /**
   * @return the number of Evaluators from a previous Driver that have checked in with the Driver
   * in a restart situation.
   */
  int getNumRecoveredContainers();

  /**
   * Indicate that this Driver has re-established the connection with one more Evaluator of a previous run.
   */
  void oneContainerRecovered();

  /**
   * Records the evaluators when it is allocated. The implementation depends on the runtime.
   * @param id The evaluator ID of the allocated evaluator.
   */
  void recordAllocatedEvaluator(final String id);

  /**
   * Records a removed evaluator into the evaluator log. The implementation depends on the runtime.
   * @param id The evaluator ID of the removed evaluator.
   */
  void recordRemovedEvaluator(final String id);
}