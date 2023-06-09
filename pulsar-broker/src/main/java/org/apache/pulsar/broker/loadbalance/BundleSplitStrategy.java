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
package org.apache.pulsar.broker.loadbalance;

import java.util.Map;
import org.apache.pulsar.broker.PulsarService;

/**
 * Load Manager component which determines what bundles should be split into two bundles.
 */
public interface BundleSplitStrategy {
    /**
     * Determines which bundles, if any, should be split.
     *
     * @param loadData
     *            Load data to base decisions on (does not have benefit of preallocated data since this may not be the
     *            leader broker).
     * @param pulsar
     *            Service to use.
     * @return A map of the bundles that should be split and the brokers on which they reside.
     */
    Map<String, String> findBundlesToSplit(LoadData loadData, PulsarService pulsar);
}
