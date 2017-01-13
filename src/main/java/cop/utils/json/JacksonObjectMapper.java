/*
 * Copyright © 2016 Oleg Cherednik
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations
 * under the License.
 */
package cop.utils.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Oleg Cherednik
 * @since 19.11.2014
 */
final class JacksonObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 6353895542108257075L;

    private static final ThreadLocal<ObjectMapper> THREAD_LOCAL_MAPPER = new ThreadLocal<>();

    public static ObjectMapper getMapper() {
        ObjectMapper mapper = THREAD_LOCAL_MAPPER.get();

        if (mapper == null)
            THREAD_LOCAL_MAPPER.set(mapper = new JacksonObjectMapper());

        return mapper;
    }

    private JacksonObjectMapper() {
        registerModule(new SimpleModule(getClass().getSimpleName(), new Version(1, 0, 0, null, null, null)));
        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
