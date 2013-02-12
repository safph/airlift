/*
 * Copyright 2010 Proofpoint, Inc.
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
package io.airlift.http.client;

import com.google.common.collect.ImmutableMap;
import io.airlift.configuration.testing.ConfigAssertions;
import io.airlift.units.DataSize;
import io.airlift.units.DataSize.Unit;
import org.testng.annotations.Test;

import java.util.Map;

public class TestAsyncHttpClientConfig
{
    @Test
    public void testDefaults()
    {
        ConfigAssertions.assertRecordedDefaults(ConfigAssertions.recordDefaults(AsyncHttpClientConfig.class)
                .setWorkerThreads(Runtime.getRuntime().availableProcessors() * 4)
                .setMaxContentLength(new DataSize(16, Unit.MEGABYTE))
                .setEnableConnectionPooling(false));
    }

    @Test
    public void testExplicitPropertyMappings()
    {
        Map<String, String> properties = new ImmutableMap.Builder<String, String>()
                .put("http-client.threads", "33")
                .put("http-client.max-content-length", "1GB")
                .put("http-client.pool-connections", "true")
                .build();

        AsyncHttpClientConfig expected = new AsyncHttpClientConfig()
                .setWorkerThreads(33)
                .setMaxContentLength(new DataSize(1, Unit.GIGABYTE))
                .setEnableConnectionPooling(true);

        ConfigAssertions.assertFullMapping(properties, expected);
    }
}
