/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.main;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.VaultConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MainVaultTest {

    @Test
    public void testMain() throws Exception {
        Main main = new Main();

        main.addInitialProperty("camel.vault.awsAccessKey", "myKey");
        main.addInitialProperty("camel.vault.awsSecretKey", "mySecret");
        main.addInitialProperty("camel.vault.awsRegion", "myRegion");

        main.start();

        CamelContext context = main.getCamelContext();
        assertNotNull(context);

        VaultConfiguration cfg = context.getVaultConfiguration();
        assertNotNull(cfg);

        Assertions.assertEquals("myKey", cfg.getAwsAccessKey());
        Assertions.assertEquals("mySecret", cfg.getAwsSecretKey());
        Assertions.assertEquals("myRegion", cfg.getAwsRegion());

        main.stop();
    }

    @Test
    public void testMainFluent() throws Exception {
        Main main = new Main();
        main.configure().vault()
                .withAwsAccessKey("myKey")
                .withAwsSecretKey("mySecret")
                .withAwsRegion("myRegion")
                .end();

        main.start();

        CamelContext context = main.getCamelContext();
        assertNotNull(context);

        VaultConfiguration cfg = context.getVaultConfiguration();
        assertNotNull(cfg);

        Assertions.assertEquals("myKey", cfg.getAwsAccessKey());
        Assertions.assertEquals("mySecret", cfg.getAwsSecretKey());
        Assertions.assertEquals("myRegion", cfg.getAwsRegion());

        main.stop();
    }

}
