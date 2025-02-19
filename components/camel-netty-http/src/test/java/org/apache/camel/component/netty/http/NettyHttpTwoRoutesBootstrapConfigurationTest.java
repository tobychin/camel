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
package org.apache.camel.component.netty.http;

import org.apache.camel.BindToRegistry;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.netty.NettyServerBootstrapConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NettyHttpTwoRoutesBootstrapConfigurationTest extends BaseNettyTest {

    private NettyServerBootstrapConfiguration bootstrapConfiguration;

    @BindToRegistry("myBootstrapOptions")
    public NettyServerBootstrapConfiguration loadNettyBootstrapConf() {

        // create NettyServerBootstrapConfiguration instance where we can configure the bootstrap
        // option we want to use in our Camel routes. This allows us to configure this once,
        // and also explicit
        bootstrapConfiguration = new NettyServerBootstrapConfiguration();
        bootstrapConfiguration.setBacklog(200);
        bootstrapConfiguration.setConnectTimeout(5000);
        bootstrapConfiguration.setKeepAlive(true);
        bootstrapConfiguration.setWorkerCount(4);

        return bootstrapConfiguration;
    }

    @Test
    public void testTwoRoutes() throws Exception {
        getMockEndpoint("mock:foo").expectedBodiesReceived("Hello World");
        getMockEndpoint("mock:bar").expectedBodiesReceived("Hello Camel");

        String out = template.requestBody("netty-http:http://localhost:{{port}}/foo", "Hello World", String.class);
        assertEquals("Bye World", out);

        out = template.requestBody("netty-http:http://localhost:{{port}}/bar", "Hello Camel", String.class);
        assertEquals("Bye Camel", out);

        assertMockEndpointsSatisfied();

        // validate the options
        NettyHttpConsumer consumer = (NettyHttpConsumer) context.getRoute("foo").getConsumer();
        assertEquals(200, consumer.getConfiguration().getBacklog());
        assertEquals(4, consumer.getConfiguration().getWorkerCount());
        assertEquals(true, consumer.getConfiguration().isKeepAlive());
        assertEquals(5000, consumer.getConfiguration().getConnectTimeout());

        consumer = (NettyHttpConsumer) context.getRoute("bar").getConsumer();
        assertEquals(200, consumer.getConfiguration().getBacklog());
        assertEquals(4, consumer.getConfiguration().getWorkerCount());
        assertEquals(true, consumer.getConfiguration().isKeepAlive());
        assertEquals(5000, consumer.getConfiguration().getConnectTimeout());
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                // we want to use the same bootstrap options and want to configure this explicit, so we
                // have a NettyServerBootstrapConfiguration instance in the registry, with the key = myBootstrapOptions
                // which we then tell netty-http to lookup and use

                from("netty-http:http://0.0.0.0:{{port}}/foo?bootstrapConfiguration=#myBootstrapOptions").routeId("foo")
                        .to("mock:foo")
                        .transform().constant("Bye World");

                from("netty-http:http://0.0.0.0:{{port}}/bar?bootstrapConfiguration=#myBootstrapOptions").routeId("bar")
                        .to("mock:bar")
                        .transform().constant("Bye Camel");
            }
        };
    }

}
