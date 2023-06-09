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
package org.apache.pulsar.client.impl.conf;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.pulsar.common.util.ObjectMapperFactory;

/**
 * Utils for loading configuration data.
 */
public final class ConfigurationDataUtils {

    public static ObjectMapper create() {
        ObjectMapper mapper = ObjectMapperFactory.create();
        // forward compatibility for the properties may go away in the future
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
        return mapper;
    }

    private static final ObjectMapper MAPPER = create();

    private ConfigurationDataUtils() {}

    public static <T> T loadData(Map<String, Object> config,
                                 T existingData,
                                 Class<T> dataCls) {
        try {
            String existingConfigJson = MAPPER.writeValueAsString(existingData);
            Map<String, Object> existingConfig = MAPPER.readValue(existingConfigJson, Map.class);
            Map<String, Object> newConfig = new HashMap<>();
            newConfig.putAll(existingConfig);
            newConfig.putAll(config);
            String configJson = MAPPER.writeValueAsString(newConfig);
            return MAPPER.readValue(configJson, dataCls);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config into existing configuration data", e);
        }

    }

}

